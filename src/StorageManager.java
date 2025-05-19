import org.json.JSONArray;
import org.json.JSONObject;

import PasswordManager.PasswordEntry;

import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class StorageManager {

    private static final String FILE_NAME = "passwords.json";

    /**
     * Get list of Password entries and put them inside the JSON array 
     */
    public static void savePasswordList(List<PasswordEntry> list) {
    JSONArray data = new JSONArray();

    for (PasswordEntry entry : list) {
        JSONObject obj = new JSONObject();
        obj.put("service", entry.getService());
        obj.put("username", entry.getUsr());
        obj.put("password", entry.getPass());
        data.put(obj);
    }

    savePasswords(data); 
    }   
    /**
     * Load the password list from the JSON file.
     * If the file doesn't exist, create an empty one.
     */
    public static JSONArray loadPasswords() {
        try {
            File file = new File(FILE_NAME);

            // If file doesn't exist, create a new empty one
            if (!file.exists()) {
                file.createNewFile();
                FileWriter writer = new FileWriter(file);
                writer.write("[]");
                writer.close();
            }

            // Read the file contents and parse as JSONArray
            String content = new String(Files.readAllBytes(file.toPath()));
            return new JSONArray(content);

        } catch (Exception e) {
            System.out.println("Error loading passwords: " + e.getMessage());
            return new JSONArray(); // return empty list if failed
        }
    }

    /**
     * receives the loaded passwords inside a JSONArray Format and turns it back into the list 
     */
    public static List<PasswordEntry> loadIntoList() {
        JSONArray data = loadPasswords(); //load existing passwords into JSONArray data
        List<PasswordEntry> list = new ArrayList<>(); //create the list we should return

        //load UserName, PassWord, Service for each jason obj inside JSONArray 

        for (int i = 0; i < data.length(); i++) {
        JSONObject obj = data.getJSONObject(i);
        String service = obj.getString("service");
        String username = obj.getString("username");
        String password = obj.getString("password");

        list.add(new PasswordEntry(service, username, password));
    }

        return list;
    }
    /**
     * Save the given password list into the JSON file.
     */
    public static void savePasswords(JSONArray data) {
        try {
            FileWriter writer = new FileWriter(FILE_NAME);
            writer.write(data.toString(4)); // Pretty print with indentation
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving passwords: " + e.getMessage());
        }
    }
    /**
    * Add a new password entry to the JSON file if it doesn't already exist.
    */
    public static boolean addPassword(JSONObject newEntry) {
        JSONArray data = loadPasswords();

        // Check for duplicates
        for (int i = 0; i < data.length(); i++) {
            JSONObject existing = data.getJSONObject(i);
        if (existing.similar(newEntry)) {
            System.out.println("Password entry already exists.");
            return false;
        }
    }

    // Add and save
        data.put(newEntry);
        savePasswords(data);
        System.out.println("Password added successfully.");
        return true;
    }

}
