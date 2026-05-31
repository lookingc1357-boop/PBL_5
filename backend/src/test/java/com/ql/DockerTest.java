package com.ql;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import org.junit.jupiter.api.Test;

class DockerTest {
    @Test
    void killsRiskySnippet() {
        DockerService service = new DockerService();
        Map<String, Object> result = service.runSnippet("demo", "c", "int main(){ while(1){} }");
        assertEquals(137, result.get("exitCode"));
    }

    @Test
    void acceptsNormalSnippet() {
        DockerService service = new DockerService();
        Map<String, Object> result = service.runSnippet("demo", "c", "int main(){ return 0; }");
        assertEquals(0, result.get("exitCode"));
    }
}
