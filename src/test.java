import java.util.HashMap;
import java.util.Map;

public class test {

    public static void main(String[] args) {

        String x=binaryToHex("00 1100 0000 0011 0100");
        System.out.println(x);
    }

    static String binaryToHex(String binary){
        String sumHex = "";
        Map<String, String> hexMaps = new HashMap<>();
        hexMaps.put("0","0");
        hexMaps.put("1","1");

        hexMaps.put("00","0");
        hexMaps.put("01","1");
        hexMaps.put("10","2");
        hexMaps.put("11","3");

        hexMaps.put("0000","0");
        hexMaps.put("0001","1");
        hexMaps.put("0010","2");
        hexMaps.put("0011","3");
        hexMaps.put("0100","4");
        hexMaps.put("0101","5");
        hexMaps.put("0110","6");
        hexMaps.put("0111","7");
        hexMaps.put("1000","8");
        hexMaps.put("1001","9");
        hexMaps.put("1010","A");
        hexMaps.put("1011","B");
        hexMaps.put("1100","C");
        hexMaps.put("1101","D");
        hexMaps.put("1110","E");
        hexMaps.put("1111","F");

        // binary size 18 bit  son iki
        for (int i = 0; i < binary.length(); ) {

            if (i < 2){
                sumHex = sumHex.concat(hexMaps.get(binary.substring(0, 2)));
                i = i + 2;
            }
            else{
                sumHex = sumHex.concat(hexMaps.get(binary.substring(i, i + 4)));
                i=i+4;
            }
        }
        return sumHex;
    }
}
