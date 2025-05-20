
public class PasswordTester {
    
    private static final String lower = "abcdefghijklmnopqrstuvwxyz";
    private static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String numbers = "0123456789";
    private static final String special = "!@#$%^&*()-_=+[]{};:,.<>?";

    private static final int lowLength = 6, mediumLength = 10, highLength = 16;
    private static final int weakPoints = 3, mediumPoints = 6, strongPoints = 9;


    public static String testPassword(String password) {
        int points = 0;

        // Password strength using length
        int length = password.length();
        if (length > lowLength) points++;
        if (length > mediumLength) points++;
        if (length > highLength) points += 2;

        // Password strength using chars
        if (hasCommonChar(password, lower)) points++;
        if (hasCommonChar(password, upper)) points++;
        if (hasCommonChar(password, numbers)) points++;
        if (hasCommonChar(password, special)) points += 2;
        
        if (points <= weakPoints) return "weak";
        else if (points <= mediumPoints) return "medium";
        else if (points <= strongPoints) return "strong";
        else return "error";
    }

    private static boolean hasCommonChar(String a, String b) {
        for (int i = 0; i < a.length(); i++) {
            if (b.indexOf(a.charAt(i)) != -1) {
                return true;
            }
        }
        return false;
    }
}
