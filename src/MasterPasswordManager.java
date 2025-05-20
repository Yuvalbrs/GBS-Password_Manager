import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import org.json.JSONArray;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MasterPasswordManager {

    private static final String FILE_NAME = "masterPassword.hash";


    public static String sha256(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    public static boolean checkPassword(String password) {
        try {
            File file = new File(FILE_NAME);

            // If file doesn't exist, create a new empty one
            if (!file.exists()) {
                file.createNewFile();
                FileWriter writer = new FileWriter(file);
                writer.write(sha256(password));
                writer.close();
                return true;
            }

            String content = new String(Files.readAllBytes(file.toPath())).trim();
            if (sha256(password).equals(content)) {
                return true;
            }
            else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error loading passwords: " + e.getMessage());
            return false;
        }
    }    
}