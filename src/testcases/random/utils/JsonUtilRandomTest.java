package testcases.random.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.FileUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class JsonUtilRandomTest {

    private static final String TEMP_FILE_PATH = "test.json";

    @BeforeEach
    public void setUp() throws IOException {
        // Ensure the test file is clean before each test
        File file = new File(TEMP_FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
    }

    @Test
    public void testReadFromFile_EmptyFile() {
        List<TestModel> result = FileUtil.readFromFile(TEMP_FILE_PATH, TestModel.class);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testReadFromFile_ValidJson() throws IOException {
        String jsonContent = "[{\"name\":\"John\",\"age\":30},{\"name\":\"Jane\",\"age\":25}]";
        Files.writeString(new File(TEMP_FILE_PATH).toPath(), jsonContent);

        List<TestModel> result = FileUtil.readFromFile(TEMP_FILE_PATH, TestModel.class);
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getName());
        assertEquals(30, result.get(0).getAge());
        assertEquals("Jane", result.get(1).getName());
        assertEquals(25, result.get(1).getAge());
    }

    @Test
    public void testReadFromFile_InvalidJson() throws IOException {
        String invalidJsonContent = "[{\"name\":\"John\",\"age\":30,}"; // Invalid JSON
        Files.writeString(new File(TEMP_FILE_PATH).toPath(), invalidJsonContent);

        assertThrows(RuntimeException.class, () -> {
            FileUtil.readFromFile(TEMP_FILE_PATH, TestModel.class);
        });
    }

    @Test
    public void testWriteToFile_ValidObjects() {
        List<TestModel> testModels = new ArrayList<>();
        testModels.add(new TestModel("John", 30));
        testModels.add(new TestModel("Jane", 25));

        FileUtil.writeToFile(TEMP_FILE_PATH, testModels);

        File file = new File(TEMP_FILE_PATH);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);

        List<TestModel> result = FileUtil.readFromFile(TEMP_FILE_PATH, TestModel.class);
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getName());
        assertEquals(30, result.get(0).getAge());
    }

    @Test
    public void testWriteToFile_ReadOnlyFile() throws IOException {
        File file = new File(TEMP_FILE_PATH);
        file.setReadOnly();

        List<TestModel> testModels = new ArrayList<>();
        testModels.add(new TestModel("John", 30));

        assertThrows(IOException.class, () -> {
            FileUtil.writeToFile(TEMP_FILE_PATH, testModels);
        });

        file.setWritable(true); // Clean up
    }

    @Test
    public void testWriteToFile_EmptyList() {
        List<TestModel> testModels = new ArrayList<>();

        FileUtil.writeToFile(TEMP_FILE_PATH, testModels);

        File file = new File(TEMP_FILE_PATH);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }

    // Test model class
    public static class TestModel {
        private String name;
        private int age;

        public TestModel() {}

        public TestModel(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TestModel testModel = (TestModel) o;
            return age == testModel.age && name.equals(testModel.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age);
        }
    }
}
