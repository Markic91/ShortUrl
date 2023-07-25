package com.D2D.ShortUrl.repository;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
@Component
public class SaveFile {

    public FileWriter createFile(File folder, String name) throws IOException {
        File file = new File(folder, name + ".json");
        if(!file.exists()){
            Files.createFile(file.toPath());
        }
        FileWriter fileToWrite = new FileWriter(file);
        return fileToWrite;
    }
}
