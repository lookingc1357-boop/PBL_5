package com.pbl.IDE.config;
 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
 
import java.util.concurrent.Executor;
 
@Configuration
@EnableAsync
public class AsyncConfig {
 
    /**
     * Thread pool riêng cho việc gọi AI model.
     * Giúp STOMP I/O thread không bị block khi AI xử lý lâu.
     *
     * Tuning:
     *  - corePoolSize  : số thread luôn sẵn sàng
     *  - maxPoolSize   : giới hạn tối đa khi queue đầy
     *  - queueCapacity : backlog trước khi tạo thêm thread
     */
    @Bean(name = "scanTaskExecutor")
    public Executor scanTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(16);
        executor.setQueueCapacity(200);
        executor.setThreadNamePrefix("vuln-scan-");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(30);
        executor.initialize();
        return executor;
    }
}
 