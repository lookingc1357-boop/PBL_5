package com.ql;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*")
public class ProjectController {
    private final FileService fileService;
    private final DockerService dockerService;

    public ProjectController(FileService fileService, DockerService dockerService) {
        this.fileService = fileService;
        this.dockerService = dockerService;
    }

    @GetMapping
    public List<Map<String, Object>> listProjects() {
        return List.of(
            Map.of("id", "pbl-devsecops", "name", "DevSecOps IDE", "updatedAt", Instant.now().toString()),
            Map.of("id", "demo-cwe", "name", "CWE Scanner Demo", "updatedAt", Instant.now().toString())
        );
    }

    @PostMapping
    public Map<String, Object> createProject(@RequestBody Map<String, String> body) {
        String name = body.getOrDefault("name", "Untitled Project");
        return Map.of("id", UUID.randomUUID().toString(), "name", name, "status", "created");
    }

    @GetMapping("/{projectId}/tree")
    public Map<String, Object> tree(@PathVariable String projectId) {
        return fileService.sampleTree(projectId);
    }

    @PostMapping("/{projectId}/files")
    public ResponseEntity<Map<String, Object>> saveFile(
        @PathVariable String projectId,
        @RequestBody Map<String, String> body
    ) {
        return ResponseEntity.ok(fileService.save(projectId, body.get("path"), body.get("content")));
    }

    @PostMapping("/{projectId}/run")
    public Map<String, Object> run(@PathVariable String projectId, @RequestBody Map<String, String> body) {
        return dockerService.runSnippet(projectId, body.getOrDefault("language", "java"), body.getOrDefault("code", ""));
    }
}
