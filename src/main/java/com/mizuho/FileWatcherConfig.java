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
    VendorFileListener vendorFileListener;

    @Value("${vendor.publish.directory}")
    private String vendorPublishDirectory;

    @PostConstruct
    public void createFolders() {
        log.info("vendorPublishDirectory="+vendorPublishDirectory);
        this.createFolderIfDoesntExist(vendorPublishDirectory);
        this.createFolderIfDoesntExist(vendorPublishDirectory + "/input");
        this.createFolderIfDoesntExist(vendorPublishDirectory + "/error");
        this.createFolderIfDoesntExist(vendorPublishDirectory + "/processed");
    }

    private void createFolderIfDoesntExist(String folder) {
        File dir = new File(folder);
        log.info(dir.getAbsolutePath() + " exists? " + dir.exists());
        if (!dir.exists()) {
            dir.mkdir();
            log.info("mkdir " + dir.getAbsolutePath());
        }
    }

    @Bean
    public FileSystemWatcher fileSystemWatcher() {
        log.info("vendorPublishDirectory="+vendorPublishDirectory);

        FileSystemWatcher fileSystemWatcher = new FileSystemWatcher(true,
                Duration.ofMillis(1500L), Duration.ofMillis(500L));
        File directory = new File(vendorPublishDirectory + "/input");
        log.info("directory.getAbsolutePath()="+directory.getAbsolutePath());
        fileSystemWatcher.addSourceDirectory(directory);
        fileSystemWatcher.addListener(vendorFileListener);
        fileSystemWatcher.start();
        log.info("started fileSystemWatcher");
        return fileSystemWatcher;
    }

    @PreDestroy
    public void onDestroy() throws Exception {
        fileSystemWatcher().stop();
    }
}
