package testcases.blackbox.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonUtilBlackBoxTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testSerializeToJson() throws Exception {
        // Arrange
        Map<String, Object> input = new HashMap<>();
        input.put("email", "test@example.com");
        input.put("username", "TestUser");
        input.put("isActive", true);

        String expectedJson = "{\"email\":\"test@example.com\",\"username\":\"TestUser\",\"isActive\":true}";

        // Act
        String actualJson = objectMapper.writeValueAsString(input);

        // Assert
        assertEquals(objectMapper.readTree(expectedJson), objectMapper.readTree(actualJson), 
                "Serialized JSON should match the expected JSON structure.");
    }

    @Test
    void testDeserializeFromJson() throws Exception {
        // Arrange
        String json = "{\"email\":\"test@example.com\",\"username\":\"TestUser\",\"isActive\":true}";
        Map<String, Object> expectedOutput = new HashMap<>();
        expectedOutput.put("email", "test@example.com");
        expectedOutput.put("username", "TestUser");
        expectedOutput.put("isActive", true);

        // Act
        @SuppressWarnings("unchecked")
        Map<String, Object> actualOutput = objectMapper.readValue(json, Map.class);

        // Assert
        assertEquals(expectedOutput, actualOutput, 
                "Deserialized map should match the expected map.");
    }

    @Test
    void testSerializeEmptyMap() throws Exception {
        // Arrange
        Map<String, Object> input = new HashMap<>();
        String expectedJson = "{}";

        // Act
        String actualJson = objectMapper.writeValueAsString(input);

        // Assert
        assertEquals(objectMapper.readTree(expectedJson), objectMapper.readTree(actualJson), 
                "Serialized JSON for an empty map should match the expected JSON structure.");
    }

    @Test
    void testDeserializeEmptyJson() throws Exception {
        // Arrange
        String json = "{}";
        Map<String, Object> expectedOutput = new HashMap<>();

        // Act
        @SuppressWarnings("unchecked")
        Map<String, Object> actualOutput = objectMapper.readValue(json, Map.class);

        // Assert
        assertEquals(expectedOutput, actualOutput, 
                "Deserialized map for empty JSON should match the expected empty map.");
    }

    @Test
    void testDeserializeInvalidJson() {
        // Arrange
        String invalidJson = "{invalidJson}";

        // Act & Assert
        assertThrows(Exception.class, () -> {
            objectMapper.readValue(invalidJson, Map.class);
        }, "Invalid JSON should throw an exception.");
    }
}
