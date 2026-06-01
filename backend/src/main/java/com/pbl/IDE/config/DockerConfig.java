// import com.github.dockerjava.core.DockerClientConfig;
// import com.github.dockerjava.core.DefaultDockerClientConfig;

// DockerClientConfig standard = DefaultDockerClientConfig.createDefaultConfigBuilder().build();
// Cấu hình tùy chỉnh:
// JavaDockerClientConfig custom = DefaultDockerClientConfig.createDefaultConfigBuilder()
//     .withDockerHost("tcp://docker.somewhere.tld:2376")
//     .withDockerTlsVerify(true)
//     .withDockerCertPath("/home/user/.docker")
//     .withRegistryUsername(registryUser)
//     .withRegistryPassword(registryPass)
//     .withRegistryEmail(registryMail)
//     .withRegistryUrl(registryUrl)
//     .build();

package com.pbl.IDE.config;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DockerConfig {
    @Bean
    public DockerClient dockerClientConfig() {
        DockerClientConfig dockerClientConfig = DefaultDockerClientConfig.createDefaultConfigBuilder().build();
        DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
                .dockerHost(dockerClientConfig.getDockerHost())
                .sslConfig(dockerClientConfig.getSSLConfig())
                .build();
        return  DockerClientImpl.getInstance(dockerClientConfig, httpClient);
    }
    
}