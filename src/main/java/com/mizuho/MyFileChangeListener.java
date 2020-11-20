package com.mizuho;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.devtools.filewatch.ChangedFile;
import org.springframework.boot.devtools.filewatch.ChangedFiles;
import org.springframework.boot.devtools.filewatch.FileChangeListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Set;

@Slf4j
@Component
public class MyFileChangeListener implements FileChangeListener {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void onChange(Set<ChangedFiles> changeSet) {
        for(ChangedFiles cfiles : changeSet) {
            for(ChangedFile cfile: cfiles.getFiles()) {
                if( /* (cfile.getType().equals(Type.MODIFY)
                     || cfile.getType().equals(Type.ADD)
                     || cfile.getType().equals(Type.DELETE) ) && */ !isLocked(cfile.getFile().toPath())) {
                    log.info("Operation: " + cfile.getType()
                            + " On file: "+ cfile.getFile().getName() + " is done");
                    try {
                        String newFilePath = cfile.getFile().getAbsolutePath().replace("input", "processed");

                        // Send a message with a POJO - the template reuse the message converter
                        log.info("Sending a JMS message. newFilePath=" + newFilePath);

                        log.info("jmsTemplate" + jmsTemplate);
                        jmsTemplate.convertAndSend("vendor-file-published-queue", newFilePath);
                        FileUtils.copyFile(cfile.getFile(), new File(newFilePath));
                        cfile.getFile().delete();
                    } catch (Exception e) {
                        String newFilePath = cfile.getFile().getAbsolutePath().replace("input", "error");
                        try {
                            FileUtils.copyFile(cfile.getFile(), new File(newFilePath));
                        } catch (IOException ex) {
                            log.error("failed to copy file to the error folder", ex);
                        }
                        cfile.getFile().delete();
                        log.error("failed processing file " + cfile.getRelativeName(), e);
                    }
                }
            }
        }
    }

    private boolean isLocked(Path path) {
        try (FileChannel ch = FileChannel.open(path, StandardOpenOption.WRITE); FileLock lock = ch.tryLock()) {
            return lock == null;
        } catch (IOException e) {
            return true;
        }
    }

}