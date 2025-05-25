import org.json.JSONArray;
import org.json.JSONObject;
import PasswordManager.PasswordEntry;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class StorageManager {

    private static final String FILE_NAME = "passwords.json";
    private static EncryptionManager encryptionManager;

    /**
     * Initialize the EncryptionManager before using this class.
     */
    public static void init(EncryptionManager manager) {
        encryptionManager = manager;
    }

    /**
     * Get list of Password entries and put them inside the JSON array, encrypting passwords.
     */
    public static void savePasswordList(List<PasswordEntry> list) {
        JSONArray data = new JSONArray();

        for (PasswordEntry entry : list) {
            JSONObject obj = new JSONObject();
            obj.put("service", entry.getService());
            obj.put("username", entry.getUsr());
            try {
                obj.put("password", encryptionManager.encrypt(entry.getPass()));
            } catch (Exception e) {
                System.out.println("Encryption failed for entry: " + entry.getService());
                continue;
            }
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

            if (!file.exists()) {
                file.createNewFile();
                FileWriter writer = new FileWriter(file);
                writer.write("[]");
                writer.close();
            }

            String content = new String(Files.readAllBytes(file.toPath()));
            return new JSONArray(content);

        } catch (Exception e) {
            System.out.println("Error loading passwords: " + e.getMessage());
            return new JSONArray();
        }
    }

    /**
     * Load passwords from JSON file into List<PasswordEntry> with decryption.
     */
    public static List<PasswordEntry> loadIntoList() {
        JSONArray data = loadPasswords();
        List<PasswordEntry> list = new ArrayList<>();

        for (int i = 0; i < data.length(); i++) {
            JSONObject obj = data.getJSONObject(i);
            String service = obj.getString("service");
            String username = obj.getString("username");
            String password;
            try {
                password = encryptionManager.decrypt(obj.getString("password"));
            } catch (Exception e) {
                System.out.println("Decryption failed for service: " + service);
                continue;
            }

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
            writer.write(data.toString(4));
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving passwords: " + e.getMessage());
        }
    }

    /**
     * Add a new password entry to the JSON file if it doesn't already exist.
     * Encrypts the password before saving.
     */
    public static boolean addPassword(JSONObject newEntry) {
        JSONArray data = loadPasswords();

        for (int i = 0; i < data.length(); i++) {
            JSONObject existing = data.getJSONObject(i);
            if (existing.similar(newEntry)) {
                System.out.println("Password entry already exists.");
                return false;
            }
        }

        try {
            String encryptedPass = encryptionManager.encrypt(newEntry.getString("password"));
            newEntry.put("password", encryptedPass);
        } catch (Exception e) {
            System.out.println("Encryption failed.");
            return false;
        }

        data.put(newEntry);
        savePasswords(data);
        System.out.println("Password added successfully.");
        return true;
    }
}
