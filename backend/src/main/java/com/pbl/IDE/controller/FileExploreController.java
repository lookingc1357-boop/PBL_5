package com.pbl.IDE.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import com.pbl.IDE.service.IOService;

@Controller
public class FileExploreController {

    private final IOService ioService;

    public FileExploreController(IOService ioService) {
        this.ioService = ioService;
    }

    @MessageMapping("/explorer/create")
    @SendToUser("/queue/explorer-response")
    public Map<String, Object> createPath(Principal principal, CreateRequest request) throws Exception {
        String username = principal.getName();
        ioService.createPath(username, request.getPath(), request.isDirectory());
        return Map.of("status", "ok", "action", "created", "path", request.getPath());
    }

    @MessageMapping("/explorer/delete")
    @SendToUser("/queue/explorer-response")
    public Map<String, Object> deletePath(Principal principal, PathRequest request) throws Exception {
        String username = principal.getName();
        ioService.deletePath(username, request.getPath());
        return Map.of("status", "ok", "action", "deleted", "path", request.getPath());
    }

    @MessageMapping("/explorer/copy")
    @SendToUser("/queue/explorer-response")
    public Map<String, Object> copyPath(Principal principal, CopyMoveRequest request) throws Exception {
        String username = principal.getName();
        ioService.copyPath(username, request.getSource(), request.getTarget());
        return Map.of("status", "ok", "action", "copied", "source", request.getSource(), "target", request.getTarget());
    }

    @MessageMapping("/explorer/move")
    @SendToUser("/queue/explorer-response")
    public Map<String, Object> movePath(Principal principal, CopyMoveRequest request) throws Exception {
        String username = principal.getName();
        ioService.movePath(username, request.getSource(), request.getTarget());
        return Map.of("status", "ok", "action", "moved", "source", request.getSource(), "target", request.getTarget());
    }

    @MessageMapping("/explorer/update")
    @SendToUser("/queue/explorer-response")
    public Map<String, Object> updateFile(Principal principal, UpdateRequest request) throws Exception {
        String username = principal.getName();
        ioService.updateFile(username, request.getPath(), request.getOperations());
        return Map.of("status", "ok", "action", "updated", "path", request.getPath());
    }

    @MessageMapping("/explorer/list")
    @SendToUser("/queue/explorer-response")
    public Map<String, Object> listTree(Principal principal, PathRequest request) throws Exception {
        String username = principal.getName();
        var tree = ioService.listTree(username, request.getPath());
        return Map.of("status", "ok", "action", "list", "tree", tree);
    }

    @MessageMapping("/explorer/read")
    @SendToUser("/queue/explorer-response")
    public Map<String, Object> readFile(Principal principal, PathRequest request) throws Exception {
        String username = principal.getName();
        String content = ioService.readFile(username, request.getPath());
        return Map.of("status", "ok", "action", "read", "path", request.getPath(), "content", content);
    }

    @MessageMapping("/explorer/subscribe")
    public void subscribeExplorer(Principal principal) throws Exception {
        ioService.ensureWatcher(principal.getName());
    }

    public static class CreateRequest {
        private String path;
        private boolean directory;

        public CreateRequest() {
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
    }

    public static class PathRequest {
        private String path;

        public PathRequest() {
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }

    public static class CopyMoveRequest {
        private String source;
        private String target;

        public CopyMoveRequest() {
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getTarget() {
            return target;
        }

        public void setTarget(String target) {
            this.target = target;
        }
    }

    public static class UpdateRequest {
        private String path;
        private List<IOService.Operation> operations;

        public UpdateRequest() {
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public List<IOService.Operation> getOperations() {
            return operations;
        }

        public void setOperations(List<IOService.Operation> operations) {
            this.operations = operations;
        }
    }
}
