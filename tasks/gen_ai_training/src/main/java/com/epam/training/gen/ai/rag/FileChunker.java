package com.epam.training.gen.ai.rag;

;
import com.microsoft.semantickernel.implementation.EmbeddedResourceLoader;
import org.springframework.stereotype.Component;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileChunker {
    public List<String> readFromFileAndCreateChunks(String fileName, int size) throws FileNotFoundException {
        String fileContent = EmbeddedResourceLoader.readFile("Darshana_RAG.txt", FileChunker.class);
        List<String> chunks = new ArrayList<>();
        for (int i = 0; i < fileContent.length(); i++) {
            chunks.add(fileContent.substring(i, Math.min(fileContent.length(), i += size)));
        }
        return chunks;
    }

}
