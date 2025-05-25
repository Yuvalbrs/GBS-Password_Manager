import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionManager {
    // Configuration constants
    private static final int KEY_SIZE = 256; // Key size in bits
    private static final int IV_SIZE = 12; // 96-bit IV (Nonce) for AES-GCM
    private static final int TAG_LENGTH = 128; // GCM authentication tag length in bits
    private static final int ITERATIONS = 65536; // PBKDF2 iterations for key strengthening
    private static final String ALGORITHM = "AES"; // Encryption algorithm
    private static final String TRANSFORMATION = "AES/GCM/NoPadding"; // Cipher transformation
    private static final String SECRET_KEY_FACTORY_ALGORITHM = "PBKDF2WithHmacSHA256"; // Key derivation function

    private final SecretKey secretKey;

    /**
     * Constructor that derives a secret key from a password and salt using PBKDF2.
     */
    public EncryptionManager(String password, byte[] salt) throws Exception {
        // Use PBKDF2 to generate a strong key from password and salt
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_SIZE);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(SECRET_KEY_FACTORY_ALGORITHM);
        byte[] keyBytes = factory.generateSecret(spec).getEncoded();
        this.secretKey = new SecretKeySpec(keyBytes, ALGORITHM); // Wrap as AES key
    }

    /**
     * Encrypt a plaintext string using AES-GCM.
     */
    public String encrypt(String plaintext) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        byte[] iv = generateIV(); // Generate a random IV for this encryption
        GCMParameterSpec parameterSpec = new GCMParameterSpec(TAG_LENGTH, iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);

        // Encrypt the plaintext
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());

        // Combine IV and ciphertext into one byte array
        byte[] ivAndCiphertext = new byte[IV_SIZE + encryptedBytes.length];
        System.arraycopy(iv, 0, ivAndCiphertext, 0, IV_SIZE);
        System.arraycopy(encryptedBytes, 0, ivAndCiphertext, IV_SIZE, encryptedBytes.length);

        // Return the result as a Base64-encoded string
        return Base64.getEncoder().encodeToString(ivAndCiphertext);
    }

    /**
     * Decrypt a Base64-encoded string that was encrypted with AES-GCM.
     */
    public String decrypt(String encryptedData) throws Exception {
        byte[] decoded = Base64.getDecoder().decode(encryptedData);

        // Extract IV and ciphertext from the combined byte array
        byte[] iv = new byte[IV_SIZE];
        byte[] ciphertext = new byte[decoded.length - IV_SIZE];
        System.arraycopy(decoded, 0, iv, 0, IV_SIZE);
        System.arraycopy(decoded, IV_SIZE, ciphertext, 0, ciphertext.length);

        // Initialize cipher for decryption using the same IV
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        GCMParameterSpec parameterSpec = new GCMParameterSpec(TAG_LENGTH, iv);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);

        // Decrypt and return the original plaintext
        byte[] decryptedBytes = cipher.doFinal(ciphertext);
        return new String(decryptedBytes);
    }

    /**
     * Generate a secure random IV (nonce) for AES-GCM.
     */
    private byte[] generateIV() {
        byte[] iv = new byte[IV_SIZE];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        return iv;
    }
}
