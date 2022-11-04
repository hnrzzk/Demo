import java.util.concurrent.ThreadLocalRandom;

public class Utils {
    public static int randomInt(final int a, final int b) {
        if (a == b) {
            return a;
        }
        int min = Math.min(a, b);
        int max = Math.max(a, b);
        int n = max - min + 1;
        ThreadLocalRandom random = ThreadLocalRandom.current();
        n = random.nextInt(n);
        n = n + min;
        return n;
    }
}
