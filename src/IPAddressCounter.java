import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class IPAddressCounter {

    public static final String IP_FILE = "src/resources/test.txt";
    private static final Map<String, Integer> cache = new HashMap<>();

    public static void main(String[] args) throws IOException {
        long begin = System.currentTimeMillis();
        fillCache();
        Path path = Paths.get(IP_FILE);
        BufferedReader reader = Files.newBufferedReader(path);
        String line = reader.readLine();
        BitSet setPositive = new BitSet();
        BitSet setNegative = new BitSet();
        int hasZero = 0;
        while (Objects.nonNull(line)) {
            int number = getNumberFromLine(line);
            if (number > 0) {
                setPositive.set(number);
            } else if (number < 0) {
                setNegative.set(-number);
            } else {
                hasZero = 1;
            }
            line = reader.readLine();
        }
        System.out.println(setPositive.cardinality() + setNegative.cardinality() + hasZero);
        System.out.println("Time: " + (System.currentTimeMillis() - begin)/1000 + " s");
    }

    private static void fillCache() {
        for (int i = 0; i < 256; i++) {
            cache.put(String.valueOf(i), i);
        }
    }

    private static int getNumberFromLine(String line) {
//        String[] split = line.split("\\.");
//        return Integer.parseInt(split[3])
//               + Integer.parseInt(split[2]) * 256
//               + Integer.parseInt(split[1]) * 256 * 256
//               + Integer.parseInt(split[0]) * 256 * 256 * 256;
        String[] split = line.split("\\.");
        return cache.get(split[3]) + cache.get(split[2]) * 256
               + cache.get(split[1]) * 256 * 256 + cache.get(split[0]) * 256 * 256 * 256;
    }
}
