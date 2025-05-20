import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

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

    public static boolean isFirstTime() {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return true;
        }
        return false;
    }

    public static boolean recoverPassword(String answer, String newPass) {
        try {
            Path path = Paths.get("masterPassword.hash");
            List<String> lines = Files.readAllLines(path);
            String thirdLine = lines.get(2).trim();
            if (sha256(answer).equals(thirdLine)) {
                lines.set(0, sha256(newPass));
                Files.write(path, lines);
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            System.err.println();
            return false;
        }


    }

    public static void createMasterPassword(String password, String safeQuestion, String answer) {
        try {
            File file = new File(FILE_NAME);

            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(sha256(password) + "\n");
            writer.write(safeQuestion + "\n");
            writer.write(sha256(answer) + "\n");
            writer.close();

        } catch (Exception e) {
            System.err.println();
        }
    }

    public static boolean checkPassword(String password) {
        try {
            Path path = Paths.get("masterPassword.hash");
            List<String> lines = Files.readAllLines(path);
            String firstLine = lines.get(0).trim();
            if (sha256(password).equals(firstLine)) {
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