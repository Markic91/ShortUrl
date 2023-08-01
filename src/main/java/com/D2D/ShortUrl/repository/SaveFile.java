package com.D2D.ShortUrl.repository;

import com.D2D.ShortUrl.entity.ShortUrlObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class SaveFile {

    private static ObjectMapper objectMapper;
    //sera utilisée pour sérialiser et désérialiser des objets Java en Json et vice versa
    private final File filePath;

    public SaveFile(ObjectMapper objectMapper, @Value("${file.path}") File filePath) {
        this.objectMapper = objectMapper;
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.filePath = filePath;
    }

    //Le constructor de la classe savefile initialise 'objectMapper' en créant une nelle instance
    // de ObjectMapper pour l'utiliser pour les opérations de sérialisation et désérilisation
    // INDENT_OUTPUT permet l'indentation du fichier JSON

    public File getFilePath() {
        return filePath;
    }

    @PostConstruct
    public void createFile() {
        File filePath = getFilePath();
        try {
            if (filePath.createNewFile()) {
                try (FileWriter fileWriter = new FileWriter(filePath)) {
                    objectMapper.writeValue(fileWriter, new ArrayList<>());
                }
            }
        } catch (IOException e) {
            System.out.println("Une erreur s'est produite lors de la création du fichier : " + e.getMessage());
        }
    }

    public List<ShortUrlObject> addContent(ShortUrlObject content) throws IOException {
        File filePath = getFilePath();
        List<ShortUrlObject> existingContent = readExistingData(filePath).orElseGet(ArrayList::new);
        existingContent.add(content);
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            objectMapper.writeValue(fileWriter, existingContent);
        }
        return existingContent;
    }

    public static Optional<List<ShortUrlObject>> readExistingData(File file) throws IOException {
        return file.exists()
                ? Optional.of(objectMapper.readValue(file, new TypeReference<>() {
        }))
                : Optional.empty();
    }
}
