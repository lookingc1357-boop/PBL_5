package com.pbl.IDE.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pbl.IDE.service.ProjectService;
import com.pbl.IDE.service.ProjectService.ProjectInfo;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/create")
    public Map<String, Object> createProject(Principal principal, @RequestBody CreateProjectRequest request)
            throws Exception {
        String username = principal.getName();
        projectService.createProject(username, request.getProjectName());
        return Map.of("status", "ok", "action", "created", "projectName", request.getProjectName());
    }

    @PostMapping("/delete")
    public Map<String, Object> deleteProject(Principal principal, @RequestBody DeleteProjectRequest request)
            throws Exception {
        String username = principal.getName();
        projectService.deleteProject(username, request.getProjectName());
        return Map.of("status", "ok", "action", "deleted", "projectName", request.getProjectName());
    }

    @GetMapping("/list")
    public Map<String, Object> listAllProjects(Principal principal) throws Exception {
        String username = principal.getName();
        List<ProjectInfo> projects = projectService.listAllProjects(username);
        return Map.of("status", "ok", "action", "list", "projects", projects);
    }

    public static class CreateProjectRequest {
        private String projectName;

        public CreateProjectRequest() {
        }

        public String getProjectName() {
            return projectName;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }
    }

    public static class DeleteProjectRequest {
        private String projectName;

        public DeleteProjectRequest() {
        }

        public String getProjectName() {
            return projectName;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }
    }
}
