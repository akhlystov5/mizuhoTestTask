package com.mizuho.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class FileReader {

    public List<String> readFileContent(String message) throws IOException {
        return FileUtils.readLines(new File(message), "UTF-8");
    }
}
