package com.mizuho;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.devtools.filewatch.FileSystemWatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.io.File;
import java.time.Duration;

@Configuration
@Slf4j
public class FileWatcherConfig {
    @Bean
    public FileSystemWatcher fileSystemWatcher() {
        FileSystemWatcher fileSystemWatcher = new FileSystemWatcher(true, Duration.ofMillis(5000L), Duration.ofMillis(3000L));
        File directory = new File("./mizuhoTestTask/src/test/app/input");
        log.info("directory.getAbsolutePath()="+directory.getAbsolutePath());
        fileSystemWatcher.addSourceDirectory(directory);
        fileSystemWatcher.addListener(new MyFileChangeListener());
        fileSystemWatcher.start();
        log.info("started fileSystemWatcher");
        return fileSystemWatcher;
    }

    @PreDestroy
    public void onDestroy() throws Exception {
        fileSystemWatcher().stop();
    }
}
