import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

// Основной класс приложения
public class Main {

    // Точка входа в программу
    public static void main(String[] args) throws IOException {

       //task1

        // Определение схемы столбцов CSV файла
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        // Путь к CSV файлу
        String fileName = "data.csv";

        // Парсинг CSV и получение списка сотрудников
       List<Employee> list = parseCSV(columnMapping, fileName);
        // Преобразование списка сотрудников в JSON
        String json = listToJson(list);

        // Запись JSON в файл
        try {
            writeString(json, "data3.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

       //task2

       /* String fileName = "data.xml";
        List<Employee> list = parseXML(fileName);
        String json = listToJson(list); // метод уже реализован
        writeString(json, "data2.json"); // метод уже реализован
*/
    }

    // Метод для парсинга CSV файла
    static List<Employee> parseCSV(String[] columnMapping, String fileName) {
        try (CSVReader csvReader = new CSVReader(new FileReader(fileName))) { // Автоматическое закрытие ресурса CSVReader
            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class); // Установка типа класса для маппинга
            strategy.setColumnMapping(columnMapping); // Установка сопоставления столбцов

            CsvToBean<Employee> csvToBean = new CsvToBeanBuilder<Employee>(csvReader)
                    .withMappingStrategy(strategy)
                    .build(); // Создание экземпляра CsvToBean

            return csvToBean.parse(); // Возвращение списка сотрудников
        } catch (IOException e) {
            e.printStackTrace();
            return null; // В случае ошибки возвращается null
        }
    }

    // Метод для преобразования списка сотрудников в JSON
    static String listToJson(List<Employee> list) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create(); // Создание экземпляра Gson
        Type listType = new TypeToken<List<Employee>>() {}.getType(); // Определение типа списка
        return gson.toJson(list, listType); // Преобразование списка в JSON
    }

    // Метод для записи строки в файл
    static void writeString(String json, String fileName) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName); // Создание FileWriter
        fileWriter.write(json); // Запись JSON в файл
        fileWriter.close(); // Закрытие файла
    }

    //task2 Метод для чтения и анализа XML файла
   /* public static List<Employee> parseXML(String fileName) {
        List<Employee> employees = new ArrayList<>();
        try {
            // Создаем фабрику построителей документов
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // Создаем построителя документов
            DocumentBuilder builder = factory.newDocumentBuilder();
            // Разбираем XML-документ
            Document document = builder.parse(new File(fileName));
            // Получаем корневой элемент
            Element root = document.getDocumentElement();
            // Получаем коллекцию всех элементов employee внутри корня
            NodeList nodeList = root.getElementsByTagName("employee");
            // Перебираем все элементы employee
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    // Создаем объект Employee, заполняем его данными из XML и добавляем в список
                    Employee employee = new Employee(
                            Long.parseLong(element.getElementsByTagName("id").item(0).getTextContent()),
                            element.getElementsByTagName("firstName").item(0).getTextContent(),
                            element.getElementsByTagName("lastName").item(0).getTextContent(),
                            element.getElementsByTagName("country").item(0).getTextContent(),
                            Integer.parseInt(element.getElementsByTagName("age").item(0).getTextContent())
                    );
                    employees.add(employee);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employees;
    }*/


}