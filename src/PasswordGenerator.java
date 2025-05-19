import java.security.SecureRandom;


public class PasswordGenerator {

    // Secure random to randomize charcters for passwords
    private static final SecureRandom random = new SecureRandom();

    // Charcters used for creation of passwords
    private static final String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%&*_";

    // Length of strong passwords
    private static final int length = 16;

    public static String generatePassword() {
        StringBuilder password = new StringBuilder(length);

        // For each char in the new password randomize from premited chars
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            password.append(chars.charAt(index));
        }

        return password.toString();
    }
}
