import org.apache.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileToJson {

    public static void main(String[] args) {
        List<User> users = new ArrayList<>();


        try (BufferedReader reader = new BufferedReader(new FileReader("file.txt"))) {
            String headerLine = reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(" ");
                User user = new User(values[0], Integer.parseInt(values[1]));
                users.add(user);
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            return;
        }


        ObjectMapper objectMapper = null;
        try {
            objectMapper = new ObjectMapper();
        } catch (Exception e) {
            System.err.println("Error initializing ObjectMapper: " + e.getMessage());
        }

        if (objectMapper == null) {
            System.err.println("Failed to initialize ObjectMapper, cannot proceed.");
            return;
        }

        try {
            String jsonContent = objectMapper.writerWithDefaultPrettyPrinter().toString();


            FileWriter fileWriter = new FileWriter("user.json");
            fileWriter.write('[');
            fileWriter.write(jsonContent.substring(1, jsonContent.length() - 1));
            fileWriter.write(']');
            fileWriter.close();
        } catch (IOException e) {
            System.err.println("Error writing JSON to the file: " + e.getMessage());
        }}}