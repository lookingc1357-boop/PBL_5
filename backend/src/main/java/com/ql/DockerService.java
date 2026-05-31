package com.ql;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DockerService {
    public Map<String, Object> runSnippet(String projectId, String language, String code) {
        boolean risky = code.contains("while(1)") || code.contains("fork(") || code.contains("Runtime.getRuntime");
        return Map.of(
            "projectId", projectId,
            "language", language,
            "sandbox", "mock-docker-runner",
            "exitCode", risky ? 137 : 0,
            "stdout", risky ? "" : "Program executed in isolated sandbox",
            "stderr", risky ? "Process killed by resource policy" : "",
            "limits", List.of("cpu=1", "memory=256m", "timeout=5s")
        );
    }
}
