package com.mizuho;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.devtools.filewatch.ChangedFile;
import org.springframework.boot.devtools.filewatch.ChangedFiles;
import org.springframework.boot.devtools.filewatch.FileChangeListener;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Set;

@Slf4j
@Component
public class MyFileChangeListener implements FileChangeListener {

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
                        FileUtils.copyFile(cfile.getFile(), new File(cfile.getFile().getAbsolutePath().replace("input","processed")));
                        cfile.getFile().delete();
                    } catch (IOException e) {
                        log.error("failed to copy a file " + cfile.getRelativeName(), e);
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