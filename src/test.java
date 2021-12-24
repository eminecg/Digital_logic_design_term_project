public class test {

    public static void main(String[] args) {
        String b ="1110";
        String a=String.format("%8s", b).replaceAll(" ", "0");
        System.out.println(a);
    }
}
