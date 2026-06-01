package com.pbl.IDE.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class IOService {

    private static final Path STORAGE_ROOT = Paths.get("D:/docker_store");
    private static final String USER_QUEUE = "/queue/file-events";

    private final SimpMessagingTemplate messagingTemplate;
    private final Map<String, WatchService> userWatchers = new ConcurrentHashMap<>();
    private final ExecutorService executor = Executors.newCachedThreadPool();

    public IOService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @PostConstruct
    public void init() throws IOException {
        Files.createDirectories(STORAGE_ROOT);
    }

    public void createPath(String username, String relativePath, boolean directory) throws IOException {
        Path target = resolveUserPath(username, relativePath);
        if (directory) {
            Files.createDirectories(target);
        } else {
            Files.createDirectories(target.getParent());
            if (!Files.exists(target)) {
                Files.createFile(target);
            }
        }
        notifyUser(username, "created", target);
        ensureWatcher(username);
    }

    public void deletePath(String username, String relativePath) throws IOException {
        Path target = resolveUserPath(username, relativePath);
        if (Files.notExists(target)) {
            throw new NoSuchFileException(target.toString());
        }
        deleteRecursively(target);
        notifyUser(username, "deleted", target);
    }

    public void copyPath(String username, String sourceRelativePath, String targetRelativePath) throws IOException {
        Path source = resolveUserPath(username, sourceRelativePath);
        Path target = resolveUserPath(username, targetRelativePath);
        if (Files.notExists(source)) {
            throw new NoSuchFileException(source.toString());
        }
        Files.createDirectories(target.getParent());
        if (Files.isDirectory(source)) {
            copyDirectoryRecursively(source, target);
        } else {
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
        }
        notifyUser(username, "copied", target);
        ensureWatcher(username);
    }

    public void movePath(String username, String sourceRelativePath, String targetRelativePath) throws IOException {
        Path source = resolveUserPath(username, sourceRelativePath);
        Path target = resolveUserPath(username, targetRelativePath);
        if (Files.notExists(source)) {
            throw new NoSuchFileException(source.toString());
        }
        Files.createDirectories(target.getParent());
        Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
        notifyUser(username, "moved", target);
    }

    public void updateFile(String username, String relativePath, List<Operation> operations) throws IOException {
        Path target = resolveUserPath(username, relativePath);
        if (Files.notExists(target) || Files.isDirectory(target)) {
            throw new NoSuchFileException(target.toString());
        }

        List<String> lines = new ArrayList<>(Files.readAllLines(target, StandardCharsets.UTF_8));

        operations.stream()
                .filter(op -> "removed".equals(op.getType()))
                .sorted((a, b) -> Integer.compare(b.getIndex(), a.getIndex()))
                .forEach(op -> {
                    int index = op.getIndex();
                    if (index >= 0 && index < lines.size()) {
                        lines.remove(index);
                    }
                });

        operations.stream()
                .filter(op -> "added".equals(op.getType()))
                .forEach(op -> {
                    int index = op.getIndex();
                    if (index >= lines.size()) {
                        lines.add(op.getValue());
                    } else if (index >= 0) {
                        lines.add(index, op.getValue());
                    }
                });

        Path tempFile = target.resolveSibling(target.getFileName().toString() + ".temp");
        Files.write(tempFile, lines, StandardCharsets.UTF_8, StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);
        Files.move(tempFile, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);

        notifyUser(username, "updated", target);
    }

    public FileEntry listTree(String username, String relativePath) throws IOException {
        Path target = resolveUserPathOrRoot(username, relativePath);
        if (Files.notExists(target)) {
            throw new NoSuchFileException(target.toString());
        }
        return buildEntry(target, getUserRoot(username));
    }

    public String readFile(String username, String relativePath) throws IOException {
        Path target = resolveUserPath(username, relativePath);
        if (Files.notExists(target) || Files.isDirectory(target)) {
            throw new NoSuchFileException(target.toString());
        }
        return Files.readString(target, StandardCharsets.UTF_8);
    }

    private Path resolveUserPathOrRoot(String username, String relativePath) {
        if (relativePath == null || relativePath.isBlank() || "/".equals(relativePath) || ".".equals(relativePath)) {
            return getUserRoot(username);
        }
        return resolveUserPath(username, relativePath);
    }

    private FileEntry buildEntry(Path path, Path userRoot) throws IOException {
        boolean directory = Files.isDirectory(path);
        String relative = userRoot.relativize(path).toString().replace('\\', '/');
        List<FileEntry> children = null;
        if (directory) {
            children = new ArrayList<>();
            try (var stream = Files.list(path)) {
                var sorted = stream.sorted((a, b) -> {
                    boolean da = Files.isDirectory(a);
                    boolean db = Files.isDirectory(b);
                    if (da != db) {
                        return da ? -1 : 1;
                    }
                    return a.getFileName().toString().compareToIgnoreCase(b.getFileName().toString());
                }).toList();
                for (Path child : sorted) {
                    children.add(buildEntry(child, userRoot));
                }
            }
        }
        return new FileEntry(path.getFileName().toString(), relative, directory, children);
    }

    public static class FileEntry {
        private String name;
        private String path;
        private boolean directory;
        private List<FileEntry> children;

        public FileEntry() {
        }

        public FileEntry(String name, String path, boolean directory, List<FileEntry> children) {
            this.name = name;
            this.path = path;
            this.directory = directory;
            this.children = children;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public boolean isDirectory() {
            return directory;
        }

        public void setDirectory(boolean directory) {
            this.directory = directory;
        }

        public List<FileEntry> getChildren() {
            return children;
        }

        public void setChildren(List<FileEntry> children) {
            this.children = children;
        }
    }

    public void ensureWatcher(String username) throws IOException {
        userWatchers.computeIfAbsent(username, key -> {
            try {
                return createUserWatcher(key);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private WatchService createUserWatcher(String username) throws IOException {
        Path userRoot = getUserRoot(username);
        Files.createDirectories(userRoot);
        WatchService watchService = FileSystems.getDefault().newWatchService();
        registerAllDirectories(userRoot, watchService);

        executor.submit(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                WatchKey key;
                try {
                    key = watchService.take();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    break;
                }

                Path dir = (Path) key.watchable();
                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();
                    if (kind == StandardWatchEventKinds.OVERFLOW) {
                        continue;
                    }

                    Path name = ((WatchEvent<Path>) event).context();
                    Path changed = dir.resolve(name);
                    String normalized = userRoot.relativize(changed).toString().replace('\\', '/');
                    if (Files.isDirectory(changed, LinkOption.NOFOLLOW_LINKS)
                            && kind == StandardWatchEventKinds.ENTRY_CREATE) {
                        try {
                            registerAllDirectories(changed, watchService);
                        } catch (IOException ignore) {
                        }
                    }

                    notifyUser(username, kind == StandardWatchEventKinds.ENTRY_DELETE ? "deleted"
                            : kind == StandardWatchEventKinds.ENTRY_CREATE ? "created" : "modified",
                            changed, normalized);
                }

                boolean valid = key.reset();
                if (!valid) {
                    break;
                }
            }
        });

        return watchService;
    }

    private void registerAllDirectories(Path root, WatchService watchService) throws IOException {
        Files.walkFileTree(root, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                dir.register(watchService,
                        StandardWatchEventKinds.ENTRY_CREATE,
                        StandardWatchEventKinds.ENTRY_DELETE,
                        StandardWatchEventKinds.ENTRY_MODIFY);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private void deleteRecursively(Path path) throws IOException {
        if (Files.isDirectory(path)) {
            try (var stream = Files.list(path)) {
                for (Path child : stream.toList()) {
                    deleteRecursively(child);
                }
            }
        }
        Files.deleteIfExists(path);
    }

    private void copyDirectoryRecursively(Path source, Path target) throws IOException {
        Files.walkFileTree(source, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path relative = source.relativize(dir);
                Files.createDirectories(target.resolve(relative));
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Path relative = source.relativize(file);
                Files.copy(file, target.resolve(relative), StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private Path resolveUserPath(String username, String relativePath) {
        if (relativePath == null || relativePath.isBlank()) {
            throw new IllegalArgumentException("Path is required");
        }
        Path normalized = STORAGE_ROOT.resolve(username).resolve(relativePath).normalize();
        Path userRoot = getUserRoot(username).normalize();
        if (!normalized.startsWith(userRoot)) {
            throw new SecurityException("Invalid path");
        }
        return normalized;
    }

    private Path getUserRoot(String username) {
        return STORAGE_ROOT.resolve(username);
    }

    private void notifyUser(String username, String action, Path target) {
        String relative = getUserRoot(username).relativize(target).toString().replace('\\', '/');
        notifyUser(username, action, target, relative);
    }

    private void notifyUser(String username, String action, Path target, String relativePath) {
        messagingTemplate.convertAndSendToUser(username, USER_QUEUE,
                Map.of(
                        "action", action,
                        "path", relativePath,
                        "isDirectory", Files.isDirectory(target),
                        "timestamp", System.currentTimeMillis()));
    }

    public static class Operation {
        private int index;
        private String type;
        private String value;

        public Operation() {
        }

        public Operation(int index, String type, String value) {
            this.index = index;
            this.type = type;
            this.value = value;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
