package com.D2D.ShortUrl.repository;

import com.D2D.ShortUrl.entity.ShortUrl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;


/*public class SaveFile {

   public ObjectMapper createFile(File folder, String name, ShortUrl content) throws IOException {
        File file = new File(folder, name + ".json");
        if(!file.exists()){
            Files.createFile(file.toPath());
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(file, content);
        return objectMapper;
    }
} */

@Component
public class SaveFile {

    private ObjectMapper objectMapper;
    //sera utilisée pour sérialiser et désérialiser des objets Java en Json et vice versa

    public SaveFile() {
        this.objectMapper = new ObjectMapper();
    }
    //Le constructor de la classe savefile initialise 'objectMapper' en créant une nelle instance
    // de ObjectMapper pour l'utiliser pour les opérations de sérialisation et déblabla

    public void createFile(File folder, String name, ShortUrl content) throws IOException {
        File file = new File(folder, name + ".json");

        if (!file.exists()) {
            // Créer un nouveau fichier avec un tableau JSON vide
            try (FileWriter fileWriter = new FileWriter(file)) {
                objectMapper.writeValue(fileWriter, new ArrayList<>());
            }
        }

        List<ShortUrl> existingContent = readExistingData(file);
        //Appelle la méthode readExistingData pour lire le contenu existant
        // du fichier en une liste d'objets ShortUrl.
        existingContent.add(content);

        // Écrire la liste mise à jour dans le fichier
        try (FileWriter fileWriter = new FileWriter(file)) {
            objectMapper.writeValue(fileWriter, existingContent);
        }
    }

    private List<ShortUrl> readExistingData(File file) throws IOException {
        if (file.exists()) {
            return objectMapper.readValue(file, new TypeReference<List<ShortUrl>>() {});
        }
        return new ArrayList<>();
    }
}
