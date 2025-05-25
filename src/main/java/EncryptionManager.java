import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionManager {

    /**
     * Encrypts the contents of a JSON file using AES and returns a Base64-encoded string.
     * 
     * @param jsonFile the input JSON file to encrypt
     * @param key the AES encryption key
     * @return a Base64-encoded string representing the encrypted data
     */
    public static String encrypt(File jsonFile, SecretKeySpec key) throws InvalidKeyException { 
        try {
            // Read the JSON file content as a String
            String data = readJsonFileAsString(jsonFile);

            // Initialize AES cipher with ECB mode and PKCS5 padding
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            // Convert the string to bytes and encrypt it
            byte[] inputBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] encryptedBytes = cipher.doFinal(inputBytes);

            // Encode the encrypted bytes to Base64 string
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Encryption failed", e);
        }
    }

    /**
     * Decrypts a Base64-encoded encrypted string and writes the result to a JSON file.
     * 
     * @param data the encrypted Base64 string
     * @param key the AES decryption key
     */
    public static void decrypt(String data, SecretKeySpec key) {
        try {
            String passWordFile = "passwordsDecrypt.json";

            // Initialize AES cipher for decryption
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);

            // Decode the Base64-encoded string to get encrypted bytes
            byte[] encryptedBytes = Base64.getDecoder().decode(data);

            // Decrypt the bytes
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            // Convert decrypted bytes to JSON string
            String decryptedJson = new String(decryptedBytes, StandardCharsets.UTF_8);

            // Write the JSON string to file
            createJsonFromString(decryptedJson, passWordFile);
        } catch (Exception e) {
            throw new RuntimeException("Decryption failed", e);
        }
    }

    /**
     * Generates an AES SecretKeySpec from a hexadecimal string (must be 16, 24, or 32 bytes long).
     * 
     * @param masterPassword the hex-encoded key string
     * @return the generated SecretKeySpec
     * @throws Exception if key length is invalid or conversion fails
     */
    public static SecretKeySpec generateKeyFromPassword(String masterPassword) throws Exception {
        byte[] keyBytes = hexToBytes(masterPassword);
        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");

        // Validate key length for AES (128, 192, or 256 bits)
        if (keyBytes.length != 16 && keyBytes.length != 24 && keyBytes.length != 32) {
            throw new IllegalArgumentException("Invalid key length for AES");
        }
        return key;
    }

    /**
     * Converts a hexadecimal string into a byte array.
     * 
     * @param hex the hex string to convert
     * @return the resulting byte array
     */
    public static byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] result = new byte[len / 2];

        for (int i = 0; i < len; i += 2) {
            result[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                                 + Character.digit(hex.charAt(i+1), 16));
        }
        return result;
    }

    /**
     * Reads the content of a JSON file and returns it as a UTF-8 encoded string.
     * 
     * @param jsonFile the JSON file to read
     * @return the file content as a string
     */
    public static String readJsonFileAsString(File jsonFile) {
        try {
            return new String(Files.readAllBytes(jsonFile.toPath()), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read JSON file", e);
        }
    }

    /**
     * Writes a JSON string into a file.
     * 
     * @param data the JSON string to write
     * @param outputPath the destination file path
     * @return the File object pointing to the written file
     */
    public static File createJsonFromString(String data, String outputPath) {
        try {
            File file = new File(outputPath);
            Files.write(file.toPath(), data.getBytes(StandardCharsets.UTF_8));
            return file;
        } catch (Exception e) {
            throw new RuntimeException("Failed to write JSON to file", e);
        }
    }
}
