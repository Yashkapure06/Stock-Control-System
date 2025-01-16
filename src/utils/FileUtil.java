package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> List<T> readFromFile(String filePath, Class<T> clazz) {
        try {
            File file = new File(filePath);
            if (!file.exists() || file.length() == 0) {
                // Return an empty list if the file doesn't exist or is empty
                return new ArrayList<>();
            }
            CollectionType listType = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
            return mapper.readValue(file, listType);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + filePath, e);
        }
    }

   
    public static <T> void writeToFile(String filePath, List<T> data) {
        try {
            File file = new File(filePath);

            // Create parent directories if they don't exist
            if (file.getParentFile() != null && !file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            mapper.writerWithDefaultPrettyPrinter().writeValue(file, data);
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + filePath, e);
        }
    }
}
