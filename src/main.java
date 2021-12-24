import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

public class main {
    // OPCODES
    static String opcodeList[][]={
            { "AND", "0000"},
            { "ANDI", "0001"},
            { "ADD", "0010"},
            { "ADDI", "0011"},
            { "OR", "0100"},
            { "ORI", "0101"},
            { "XOR", "0110"},
            { "XORI", "0111"},
            { "LD", "1000"},
            { "ST", "1001"},
            { "JUMP", "1010"},
            { "BEQ", "1011"},
            { "BGT", "1100"},
            { "BLT", "1101"},
            { "BGE", "1110"},
            { "BLE", "1111"},

    };

    public static void main(String[] args) throws FileNotFoundException {

        String inputFilePath = "input.txt";

        ArrayList<ArrayList<String>> myOpcodeList = new ArrayList<ArrayList<String>>();

        //File file =new File(inputFilePath);
        FileInputStream fis = new FileInputStream(inputFilePath);
        Scanner sc = new Scanner(fis);    //file to be scanned

        int instIndex = 0;
        while (sc.hasNextLine()) {
            String instructionSet[] = sc.nextLine().split("[ ,]+");

            ArrayList<String> temp = new ArrayList<String>();
            for ( int i = 0; i < instructionSet.length; i++ ) {

                temp.add(instructionSet[i]);
            }
            myOpcodeList.add(temp);
            instIndex++;
        }
        sc.close();     //closes the scanner



        String dest;
        String src1;
        String src2;
        String imm;
        String opp1;
        String opp2;
        String addr;
        String n = "0";
        String p = "0";
        String z= "0";
        String PC;



        for ( int i = 0; i < myOpcodeList.size(); i++ ) {
            //                                              ------------------ and
            if (myOpcodeList.get(i).get(0).equals(opcodeList[0][0])) {// AND



              //  andFuncForm(opcodeList[0][1],dest,src1,src2);

            }//                                              ------------------ andI
            else if (myOpcodeList.get(i).get(0).equals(opcodeList[1][0])) {   // ANDI
                dest = convertRegistersToBinary(myOpcodeList.get(i).get(1));
                src1 = convertRegistersToBinary(myOpcodeList.get(i).get(2));
                // Since immediate can be negative or positive, determine it's sign

                if(Integer.parseInt(myOpcodeList.get(i).get(3))<0){
                    imm=Negative_signed_numb(myOpcodeList.get(i).get(3));
                }
                else
                    imm=Positive_signed_numb(myOpcodeList.get(i).get(3));

                String binaryResult= "";
                binaryResult=opcodeList[1][1]+dest+src1+imm;
                System.out.println(binaryResult);



                //                andIFuncForm(opcodeList[1][1],dest,src1,imm);


//                                              ------------------ add
            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[2][0])) { // ADD

                dest = myOpcodeList.get(i).get(1);
                src1 = myOpcodeList.get(i).get(2);
                src2 = myOpcodeList.get(i).get(3);

              //  andFuncForm(opcodeList[2][1], dest, src1, src2);

            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[3][0])) {// ADDI
                dest = myOpcodeList.get(i).get(1);
                src1 = myOpcodeList.get(i).get(2);
                imm = myOpcodeList.get(i).get(3);

                andIFuncForm(opcodeList[3][1],dest,src1,imm);

            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[4][0])) {//  OR

                dest = myOpcodeList.get(i).get(1);
                src1 = myOpcodeList.get(i).get(2);
                src2 = myOpcodeList.get(i).get(3);

              //  andFuncForm(opcodeList[4][1], dest, src1, src2);

            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[5][0])) {//  ORI
                System.out.println(opcodeList[5][1]);
            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[6][0])) {  //  XOR
                System.out.println(opcodeList[6][1]);

                dest = myOpcodeList.get(i).get(1);
                src1 = myOpcodeList.get(i).get(2);
                src2 = myOpcodeList.get(i).get(3);

              //  andFuncForm(opcodeList[6][1], dest, src1, src2);

            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[7][0])) {   // XORI
                dest = myOpcodeList.get(i).get(1);
                src1 = myOpcodeList.get(i).get(2);
                imm = myOpcodeList.get(i).get(3);

                andIFuncForm(opcodeList[7][1],dest,src1,imm);
            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[8][0])) { // LD


                dest = myOpcodeList.get(i).get(1);
                addr = myOpcodeList.get(i).get(2);


                if (Integer.parseInt(addr) < 0) {
                    n="1";
                    p="0";
                    z="0";

                }
                else if (Integer.parseInt(addr) == 0){
                    z="1";
                    n="0";
                    p="0";
                }
                else if( Integer.parseInt(addr) > 0 && Integer.parseInt(addr) < 128){
                    z="0";
                    n="0";
                    p="1";
                }
              else{
                    System.out.println("Address can\'t be greater than 512 for LD !");
                    break;
                }

               LD(opcodeList[8][1],dest,addr);

            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[9][0])) {// ST
                src1 = myOpcodeList.get(i).get(1);
                addr = myOpcodeList.get(i).get(2);


                if (Integer.parseInt(addr) < 0) {
                    n="1";
                    p="0";
                    z="0";

                }
                else if (Integer.parseInt(addr) == 0){
                    z="1";
                    n="0";
                    p="0";
                }
                else if( Integer.parseInt(addr) > 0 && Integer.parseInt(addr) < 128){
                    z="0";
                    n="0";
                    p="1";
                }
                else{
                    System.out.println("Address can\'t be greater than 512 for st !");
                    break;
                }

                ST(opcodeList[9][1],src1,addr);

            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[10][0])) {  // JUMP
                addr= myOpcodeList.get(i).get(1);

                if( Integer.parseInt(addr) > -8192 &&  Integer.parseInt(addr) > 8191){
                    System.out.println("PC-Relative address must be between -2048 and 2047 !");
                   break;
                }
                String sign;
                if(Integer.parseInt(addr) < 0) {
                    sign = "1";
                }
                else{
                    sign = "0";
                }

                JmpFunction(opcodeList[10][1],addr);


            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[11][0])) {  //  BEQ

                opp1 =myOpcodeList.get(i).get(1);
                opp2= myOpcodeList.get(i).get(2);
                addr= myOpcodeList.get(i).get(3);

                if(Integer.parseInt(addr)>32){
                    System.out.println("Address can\'t be greater than 32 for st !"); //2^5
                    break;
                }
                if(opp1.equals(opp2)){
                    z="1";
                    n="0";
                    p="0";
                }
                PC= n + z + p + addr;
                 BranchFunction(opcodeList[11][1],opp1,opp2,addr);


            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[12][0])) {  //  BGT
                opp1 =myOpcodeList.get(i).get(1);
                opp2= myOpcodeList.get(i).get(2);
                addr= myOpcodeList.get(i).get(3);

                if(Integer.parseInt(addr)>32){
                    System.out.println("Address can\'t be greater than 32 for st !"); //2^5
                    break;
                }
                if(Integer.parseInt(opp1)>Integer.parseInt(opp2)){
                    z="0";
                    n="0";
                    p="1";
                }
                PC= n + z + p + addr;
                BranchFunction(opcodeList[12][1],opp1,opp2,addr);

            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[13][0])) {   // BLT
                opp1 =myOpcodeList.get(i).get(1);
                opp2= myOpcodeList.get(i).get(2);
                addr= myOpcodeList.get(i).get(3);

                if(Integer.parseInt(addr)>32){
                    System.out.println("Address can\'t be greater than 32 for st !"); //2^5
                    break;
                }
                if(Integer.parseInt(opp1)<Integer.parseInt(opp2)){
                    z="0";
                    n="1";
                    p="0";
                }
                PC= n + z + p + addr;
                BranchFunction(opcodeList[13][1],opp1,opp2,addr);

            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[14][0])) {    // BGE
                opp1 =myOpcodeList.get(i).get(1);
                opp2= myOpcodeList.get(i).get(2);
                addr= myOpcodeList.get(i).get(3);

                if(Integer.parseInt(addr)>32){
                    System.out.println("Address can\'t be greater than 32 for st !"); //2^5
                    break;
                }
                if(Integer.parseInt(opp1)>= Integer.parseInt(opp2)){
                    z="1";
                    n="0";
                    p="1";
                }
                PC= n + z + p + addr;
                BranchFunction(opcodeList[13][1],opp1,opp2,addr);

            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[15][0])) {// BLE
                opp1 =myOpcodeList.get(i).get(1);
                opp2= myOpcodeList.get(i).get(2);
                addr= myOpcodeList.get(i).get(3);

                if(Integer.parseInt(addr)>32){
                    System.out.println("Address can\'t be greater than 32 for st !"); //2^5
                    break;
                }
                if(Integer.parseInt(opp1)<=Integer.parseInt(opp2)){
                    z="1";
                    n="1";
                    p="0";
                }
                PC= n + z + p + addr;
                BranchFunction(opcodeList[14][1],opp1,opp2,addr);

            } else {
                System.out.println("Please enter correct input.");
            }
        }


    }



    static String convertBinary(int number) {
        int reminder;
        String bin_number = "";

        while (number > 0) {
            reminder = number % 2;
            bin_number = reminder + "" + bin_number;
            number = number / 2;
        }

        return bin_number;
    }
    static String Positive_signed_numb(String number) {

        String Real_number = convertBinary(Integer.parseInt(number));
        Real_number = String.format("%0" + (8 - Real_number.length()) + "d%s", 0, Real_number); //complements 2 byte

        return Real_number;

    }
    //convert negative decimal to binary by using 2's complement representation
    static String Negative_signed_numb(String number) {

        String Real_number = convertBinary(Integer.parseInt(number.substring(1)));
        Real_number = String.format("%0" + (8 - Real_number.length()) + "d%s", 0, Real_number); //complement 2 byte

        char[] chars = Real_number.toCharArray(); //to access each character

        //it progresses from the right until it reaches 1, when it reaches 1, it makes the 1s 0, the 0s 1, starting from the left of 1.

        int size = Real_number.length();
        int j;
        int i = size - 1;
        while (i >= 0) {
            if (chars[i] == '1') {
                for (j = i - 1; j >= 0; j--) {
                    if (chars[j] == '1') {
                        chars[j] = '0';
                    } else if (chars[j] == '0') {
                        chars[j] = '1';
                    }
                }
                Real_number = String.valueOf(chars);
                break;
            }
            i = i - 1;
        }
        return Real_number;
    }


     static String BranchFunction(String operation, String opp1, String opp2, String addr) {
        return "";
    }

    static String JmpFunction(String operation, String addr) {
        return "";
    }


    static String  andFuncForm(String operation,int dest,int src1,int src2){
        String binaryResult= "";
        if (operation.equals("ADD") || operation.equals( "AND")){
//            dest = convertRegistersToBinary(myOpcodeList.get(i).get(1));
//            src1 = convertRegistersToBinary(myOpcodeList.get(i).get(2));
//            src2 = String.format("%8s",  convertRegistersToBinary(myOpcodeList.get(i).get(3))    ).replaceAll(" ", "0");
//
//            binaryResult=opcodeList[0][1]+dest+src1+src2;
//
//
//            //binary_code += ''.join(binary_register_values)
        }
        return  binaryResult;
    }

    static String  andIFuncForm(String operation,String dest,String src1,String imm){
        if (operation.equals("ADD") || operation.equals( "AND")){

            // binary_register_values = self.__convert_registers_to_binary(dest = dest, src_1 = src_1, src_2 = src_2)

            //binary_code += ''.join(binary_register_values)
        }
        return " ";
    }

        static String  convertRegistersToBinary(String src1 ){

        int registerNo= Integer.parseInt(src1.split("R")[1]);
           String binaryResult;
            if(registerNo>15 || registerNo<0 ){
                System.out.println("There are 16 register in processor.");
                binaryResult=null;
            }
            else{

                binaryResult= Integer.toBinaryString(registerNo);

                binaryResult  =String.format("%4s",binaryResult).replaceAll(" ","0");
                    System.out.println("Binary result "+binaryResult);
                }
        return binaryResult;
    }

    static String  convertImmidateToBinary(String src1 ){ // immidate value 8 bit = 1 bit sign 7 bit value

        int registerNo= Integer.parseInt(src1.split("R")[1]);
        String binaryResult;
        if(registerNo>15 || registerNo<0 ){
            System.out.println("There are 16 register in processor.");
            binaryResult=null;
        }
        else{

            binaryResult= Integer.toBinaryString(registerNo);

            binaryResult  =String.format("%4s",binaryResult).replaceAll(" ","0");
            System.out.println("Binary result "+binaryResult);
        }
        return binaryResult;
    }


    static String  LD(String op,String dest,String address){


        return "";
    }

    static String  ST(String op,String dest,String address){


        return "";
    }
}

