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

    private static final String IP_FILE = "C:\\Users\\Ilia_Stavrov\\Downloads\\ip_addresses\\ip_addresses";

    private static final Map<String, Integer> cache = getCache();

    public static void main(String[] args) throws IOException {
        long begin = System.currentTimeMillis();
        Path path = Paths.get(IP_FILE);
        BufferedReader reader = Files.newBufferedReader(path);
        String line = reader.readLine();
        BitSet setPositive = new BitSet();
        BitSet setNegative = new BitSet();
        int hasZero = 0;
        long i = 0L;
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
            i++;
            if (i % 100_000_000 == 0) {
                System.out.println(i + " " + getResult(setPositive, setNegative, hasZero));
            }
        }
        System.out.println("Result: " + getResult(setPositive, setNegative, hasZero));
        long milliseconds = System.currentTimeMillis() - begin;
        long minutes = (milliseconds / 1000) / 60;
        long seconds = (milliseconds / 1000) % 60;
        System.out.println("Time: " + minutes + "m " + seconds + "s");
    }

    private static int getResult(BitSet setPositive, BitSet setNegative, int hasZero) {
        return setPositive.cardinality() + setNegative.cardinality() + hasZero;
    }

    private static Map<String, Integer> getCache() {
        Map<String, Integer> cache = new HashMap<>();
        for (int i = 0; i < 256; i++) {
            cache.put(String.valueOf(i), i);
        }
        return cache;
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
