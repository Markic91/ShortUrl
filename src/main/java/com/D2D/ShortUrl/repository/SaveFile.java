package com.D2D.ShortUrl.repository;

import com.D2D.ShortUrl.dto.ShortUrlTokenDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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

    private final ObjectMapper objectMapper;
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

    public void createFile(ShortUrlTokenDto content) throws IOException {
        File file = filePath;

        if (!file.exists()) {
            // Créer un nouveau fichier avec un tableau JSON vide
            try (FileWriter fileWriter = new FileWriter(file)) {
                objectMapper.writeValue(fileWriter, new ArrayList<>());
            }
        }


        List<ShortUrlTokenDto> existingContent = readExistingData(file).orElseGet(ArrayList::new); //affichage court pour ()-> new ArrayList
        //Appelle la méthode readExistingData pour lire le contenu existant
        // du fichier en une liste d'objets ShortUrl.
        existingContent.add(content);

        // Écrire la liste mise à jour dans le fichier
        try (FileWriter fileWriter = new FileWriter(file)) {
            objectMapper.writeValue(fileWriter, existingContent);
        }
    }


    public Optional<List<ShortUrlTokenDto>> readExistingData(File file) throws IOException {

        return file.exists()
                ? Optional.of(objectMapper.readValue(file, new TypeReference<>() {
        }))
                : Optional.empty();
    }
}
