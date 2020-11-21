package com.mizuho;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.devtools.filewatch.ChangedFile;
import org.springframework.boot.devtools.filewatch.ChangedFiles;
import org.springframework.jms.core.JmsTemplate;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class VendorFileChangeListenerTest {

    @InjectMocks
    VendorFileChangeListener vendorFileChangeListener;

    @Mock
    private JmsTemplate jmsTemplate;

    @Before
    public void setUp() throws IOException {
        new File("src/test/resources/csv/input", "Test 1.csv").createNewFile();

        new File("src/test/resources/csv/processed", "Test 2.csv").createNewFile();
    }

    @After
    public void tearDown() {
        new File("src/test/resources/csv/input", "Test 1.csv").deleteOnExit();
        new File("src/test/resources/csv/error", "Test 1.csv").deleteOnExit();
        new File("src/test/resources/csv/processed", "Test 1.csv").deleteOnExit();
    }

    @Test
    public void onChange() {
//        Mockito.when(vendorFileChangeListener.isLocked(Mockito.anyObject())).thenReturn(false);

        Set<ChangedFiles> changeSet = new HashSet<>();
        Set<ChangedFile> files = new HashSet<>();
        String pathname = "src/test/resources/csv/input";
        File sourceDirectory = new File(pathname);
        files.add(new ChangedFile(sourceDirectory, new File(pathname, "Test 1.csv"), ChangedFile.Type.ADD) );
        changeSet.add(new ChangedFiles(sourceDirectory, files));

        vendorFileChangeListener.onChange(changeSet);

        Mockito.verify(jmsTemplate).convertAndSend("vendor-file-published-queue",
                new File("src/test/resources/csv/processed", "Test 1.csv").getAbsolutePath());
    }

    @Test
    public void onChangeFail() {
//        Mockito.when(vendorFileChangeListener.isLocked(Mockito.anyObject())).thenReturn(false);

        Set<ChangedFiles> changeSet = new HashSet<>();
        Set<ChangedFile> files = new HashSet<>();
        String pathname = "src/test/resources/csv/processed";
        File sourceDirectory = new File(pathname);
        files.add(new ChangedFile(sourceDirectory, new File(pathname, "Test 2.csv"), ChangedFile.Type.ADD) );
        changeSet.add(new ChangedFiles(sourceDirectory, files));

        vendorFileChangeListener.onChange(changeSet);

        Mockito.verify(jmsTemplate, Mockito.times(0)).convertAndSend(Mockito.anyString(), Mockito.anyString());
    }

}