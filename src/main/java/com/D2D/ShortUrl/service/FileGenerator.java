package com.D2D.ShortUrl.service;

import com.D2D.ShortUrl.dto.ShortUrlTokenDto;
import com.D2D.ShortUrl.repository.SaveFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileGenerator {


    private final SaveFile saveFile;
    private final ObjectMapper objectMapper;

    public FileGenerator(SaveFile saveFile, ObjectMapper objectMapper) {
        this.saveFile = saveFile;
        this.objectMapper = objectMapper;
    }

    @PostConstruct

    public void createFile() throws IOException {
        String filePath = "C:\\Users\\9101015H\\www\\fileTest.json";
        File file = new File(filePath);
        try {
            if (file.createNewFile()) {
                try (FileWriter fileWriter = new FileWriter(file)) {
                    objectMapper.writeValue(fileWriter, new ArrayList<>());
                }
                List<ShortUrlTokenDto> existingContent = saveFile.readExistingData(file).orElseGet(ArrayList::new);
            }
        } catch (IOException e) {
            System.out.println("Une erreur s'est produite lors de la création du fichier : " + e.getMessage());
        }
    }
}
