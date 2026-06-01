package com.pbl.IDE.service;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    private static final Path STORAGE_ROOT = Paths.get("D:/docker_store");

    public ProjectService() {
        try {
            Files.createDirectories(STORAGE_ROOT);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create storage root", e);
        }
    }

    public void createProject(String username, String projectName) throws IOException {
        Path userRoot = getUserRoot(username);
        Path projectPath = userRoot.resolve(projectName);

        if (Files.exists(projectPath)) {
            throw new IOException("Project already exists: " + projectName);
        }

        Files.createDirectories(projectPath);
    }

    public void deleteProject(String username, String projectName) throws IOException {
        Path projectPath = resolveProject(username, projectName);
        if (Files.notExists(projectPath)) {
            throw new NoSuchFileException(projectPath.toString());
        }
        deleteRecursively(projectPath);
    }

    public List<ProjectInfo> listAllProjects(String username) throws IOException {
        Path userRoot = getUserRoot(username);
        if (Files.notExists(userRoot)) {
            return new ArrayList<>();
        }

        List<ProjectInfo> projects = new ArrayList<>();
        try (var stream = Files.list(userRoot)) {
            stream.filter(path -> Files.isDirectory(path))
                    .forEach(path -> {
                        try {
                            projects.add(buildProjectInfo(path));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }

        projects.sort((a, b) -> b.getLastModified().compareTo(a.getLastModified()));
        return projects;
    }

    private ProjectInfo buildProjectInfo(Path projectPath) throws IOException {
        String projectName = projectPath.getFileName().toString();

        BasicFileAttributes attrs = Files.readAttributes(projectPath, BasicFileAttributes.class);
        LocalDateTime createdAt = Instant.ofEpochMilli(attrs.creationTime().toMillis())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        LocalDateTime lastModified = Instant.ofEpochMilli(attrs.lastModifiedTime().toMillis())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        String description = readProjectDescription(projectPath);

        return new ProjectInfo(projectName, createdAt, lastModified, description);
    }

    private String readProjectDescription(Path projectPath) throws IOException {
        Path readmePath = projectPath.resolve("README.md");
        if (Files.notExists(readmePath)) {
            return "";
        }

        byte[] bytes = Files.readAllBytes(readmePath);
        CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder()
                .onMalformedInput(CodingErrorAction.REPLACE)
                .onUnmappableCharacter(CodingErrorAction.REPLACE);
        String content = decoder.decode(ByteBuffer.wrap(bytes)).toString();

        int maxLength = Math.min(200, content.length());
        return content.substring(0, maxLength).trim();
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

    private Path resolveProject(String username, String projectName) {
        if (projectName == null || projectName.isBlank()) {
            throw new IllegalArgumentException("Project name is required");
        }
        Path normalized = getUserRoot(username).resolve(projectName).normalize();
        Path userRoot = getUserRoot(username).normalize();
        if (!normalized.startsWith(userRoot)) {
            throw new SecurityException("Invalid project path");
        }
        return normalized;
    }

    private Path getUserRoot(String username) {
        return STORAGE_ROOT.resolve(username);
    }

    public static class ProjectInfo {
        private String name;
        private LocalDateTime createdAt;
        private LocalDateTime lastModified;
        private String description;

        public ProjectInfo() {
        }

        public ProjectInfo(String name, LocalDateTime createdAt, LocalDateTime lastModified, String description) {
            this.name = name;
            this.createdAt = createdAt;
            this.lastModified = lastModified;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
        }

        public LocalDateTime getLastModified() {
            return lastModified;
        }

        public void setLastModified(LocalDateTime lastModified) {
            this.lastModified = lastModified;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
