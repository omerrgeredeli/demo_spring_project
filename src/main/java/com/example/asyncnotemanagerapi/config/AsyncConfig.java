package com.example.asyncnotemanagerapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "noteExecutor")
    public Executor noteExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(6);           // Maksimum thread sayısı
        executor.setQueueCapacity(20);        // Bekleyen görev kapasitesi
        executor.setThreadNamePrefix("NoteAsync-"); // Thread isim prefix
        executor.initialize();
        return executor;
    }
}
