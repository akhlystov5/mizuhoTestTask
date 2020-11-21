package com.mizuho;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.devtools.filewatch.FileSystemWatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.time.Duration;

@Configuration
@Slf4j
public class FileWatcherConfig {

    @Autowired
    VendorFileChangeListener vendorFileChangeListener;

    @Value("${vendor.publish.directory}")
    private String vendorPublishDirectory;

    @PostConstruct
    public void createFolders() {
        log.info("vendorPublishDirectory="+vendorPublishDirectory);
        //TODO create 3 folders if don't exist
    }

    @Bean
    public FileSystemWatcher fileSystemWatcher() {
        log.info("vendorPublishDirectory="+vendorPublishDirectory);

        FileSystemWatcher fileSystemWatcher = new FileSystemWatcher(true,
                Duration.ofMillis(1500L), Duration.ofMillis(500L));
        File directory = new File(vendorPublishDirectory + "/input");
        log.info("directory.getAbsolutePath()="+directory.getAbsolutePath());
        fileSystemWatcher.addSourceDirectory(directory);
        fileSystemWatcher.addListener(vendorFileChangeListener);
        fileSystemWatcher.start();
        log.info("started fileSystemWatcher");
        return fileSystemWatcher;
    }

    @PreDestroy
    public void onDestroy() throws Exception {
        fileSystemWatcher().stop();
    }
}
