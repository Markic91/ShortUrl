package com.D2D.ShortUrl.repository;

import com.D2D.ShortUrl.Entity.ShortUrl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
@Component
public class SaveFile {

    public ObjectMapper createFile(File folder, String name, ShortUrl content) throws IOException {
        File file = new File(folder, name + ".json");
        if(!file.exists()){
            Files.createFile(file.toPath());
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(file, content);
        return objectMapper;
    }
}
