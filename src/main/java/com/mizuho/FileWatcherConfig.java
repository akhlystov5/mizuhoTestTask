package com.mizuho;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.devtools.filewatch.FileSystemWatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

import javax.annotation.PreDestroy;
import java.io.File;
import java.time.Duration;

@Configuration
@Slf4j
public class FileWatcherConfig {

    @Autowired
    MyFileChangeListener myFileChangeListener;

    @Bean
    public FileSystemWatcher fileSystemWatcher() {
        FileSystemWatcher fileSystemWatcher = new FileSystemWatcher(true,
                Duration.ofMillis(1500L), Duration.ofMillis(500L));
        File directory = new File("./src/test/app/input");
        log.info("directory.getAbsolutePath()="+directory.getAbsolutePath());
        fileSystemWatcher.addSourceDirectory(directory);
        fileSystemWatcher.addListener(myFileChangeListener);
        fileSystemWatcher.start();
        log.info("started fileSystemWatcher");
        return fileSystemWatcher;
    }

    @PreDestroy
    public void onDestroy() throws Exception {
        fileSystemWatcher().stop();
    }
}
