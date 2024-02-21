import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

class MainTest {


    @Test
    void parseCSVTest() {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.csv";
        List<Employee> result = Main.parseCSV(columnMapping, fileName);
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("John", result.get(0).firstName);
    }



    @Test
    void listToJsonTest() { // Объявление метода для тестирования функциональности listToJson
        List<Employee> employees = List.of(new Employee(1, "John", "Doe", "USA", 25)); // Создание тестового списка сотрудников
        String json = Main.listToJson(employees); // Вызов метода listToJson и получение результата
        Assertions.assertNotNull(json); // Проверка, что результат не равен null
        Assertions.assertFalse(json.isEmpty()); // Проверка, что результат не пустой
        Assertions.assertTrue(json.contains("\"firstName\":\"John\"")); // Проверка, что JSON содержит имя "John"
    }

    @Test
    void writeStringTest() throws IOException { // Объявление метода для тестирования функциональности writeString
        String testContent = "Test Content"; // Тестовое содержимое для записи в файл
        String fileName = "temp_test_file.json"; // Путь к временному тестовому файлу
        Main.writeString(testContent, fileName); // Вызов метода writeString для записи в файл
        File file = new File(fileName); // Создание объекта файла
        Assertions.assertTrue(file.exists()); // Проверка, что файл был создан
        String content = new String(Files.readAllBytes(Paths.get(fileName))); // Чтение содержимого из файла
        Assertions.assertEquals(testContent, content); // Проверка, что содержимое файла соответствует ожидаемому
        // Удаление тестового файла после проверки
        Files.deleteIfExists(file.toPath());
    }
}