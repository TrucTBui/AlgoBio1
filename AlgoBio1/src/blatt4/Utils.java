package blatt4;
import java.util.Random;

public class Utils {
    protected static final char[] ALPHABET1 = {'0','1'};
    protected static final char[] ALPHABET2 = {'A', 'T', 'G', 'C'};
    protected static final char[] ALPHABET3 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
    protected static Random rand = new Random();

    public static String generateRandomString(int length, char[] alphabet) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(alphabet[rand.nextInt(alphabet.length)]);
        }
        return sb.toString();
    }
}
