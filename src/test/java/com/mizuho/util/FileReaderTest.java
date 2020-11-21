package com.mizuho.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

@Slf4j
public class FileReaderTest {

    @Test
    public void readFileContent() throws IOException {
        List<String> strings = new FileReader().readFileContent("src/test/resources/csv/processed/ICE 1.csv");
        log.info(strings.toString());
        assertEquals("[Instrument Name, ISIN, Price, Nike Inc Stock,US6541061031,105.00, Adidas AG Stock,DE000A1EWWW0,125.00]",
                strings.toString());
    }
}