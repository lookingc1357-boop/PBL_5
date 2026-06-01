/*
lớp ContainerService nơi khởi động container trong docker 
+ gồm các phương thức để tạo container, khởi động, dừng và xóa container
+ kiểm tra trạng thái container 
*/
package com.pbl.IDE.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Bind;
import com.pbl.IDE.status.ContainerStatus;

@Service
public class ContainerService {
    @Autowired
    private final DockerClient dockerClient;
    @Autowired
    private Environment env;

    public ContainerService(DockerClient dockerClient) {
        this.dockerClient = dockerClient;
    }

    public void start(String username) {
        if (checkContainerExists(username)) {
            dockerClient.startContainerCmd(username).exec();
        }
    }

    public void stop(String username) {
        if (checkContainerExists(username)) {
            dockerClient.stopContainerCmd(username).exec();
        }
    }

    public void remove(String username) {
        if (checkContainerExists(username)) {
            this.stop(username);
            dockerClient.removeContainerCmd(username).exec();
        }
    }

    public ContainerStatus getStatus(String username) {
        Boolean isRunning = dockerClient.inspectContainerCmd(username).exec().getState().getRunning();
        return isRunning ? ContainerStatus.RUNNING : ContainerStatus.STOPPED;
    }
    public boolean isStopped(String username) {
        return getStatus(username) == ContainerStatus.STOPPED;
    }

    public boolean checkContainerExists(String containerId) {
        try {
            dockerClient.inspectContainerCmd(containerId).exec();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String createContainer(String username) {
        dockerClient.createContainerCmd(env.getProperty("docker.image.name"))
                .withName(username)
                .withHostName(username) // add hostname 
                .withCmd("sh")
                .withTty(true)
                .withStdinOpen(true)
                .withStdInOnce(true)
                .withBinds(Bind.parse("D:\\docker_store\\" + username + ":/workspace"))
                .exec();
        return username;
    }
}
