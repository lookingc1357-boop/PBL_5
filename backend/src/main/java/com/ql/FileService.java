package com.ql;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class FileService {
    public Map<String, Object> sampleTree(String projectId) {
        return Map.of(
            "projectId", projectId,
            "nodes", List.of(
                Map.of("type", "folder", "name", "src", "children", List.of(
                    Map.of("type", "file", "name", "main.c", "path", "src/main.c"),
                    Map.of("type", "file", "name", "auth.c", "path", "src/auth.c")
                )),
                Map.of("type", "file", "name", "README.md", "path", "README.md")
            )
        );
    }

    public Map<String, Object> save(String projectId, String path, String content) {
        if (path == null || path.isBlank()) {
            throw new IllegalArgumentException("File path is required");
        }
        return Map.of(
            "projectId", projectId,
            "path", path,
            "bytes", content == null ? 0 : content.getBytes().length,
            "savedAt", Instant.now().toString()
        );
    }
}
