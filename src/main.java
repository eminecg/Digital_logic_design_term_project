import java.io.*;
import java.util.*;

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


    static ArrayList<ArrayList<String>> myOpcodeList = new ArrayList<ArrayList<String>>();

    static String dest;
    static String src1;
    static String src2;
    static String imm;
    static  String opp1;
    static  String opp2;
    static  String addr;
    static String PC="";
    static String PCOffset="";

    public static void main(String[] args) throws FileNotFoundException {
        String n = "0";
        String p = "0";
        String z= "0";
       //
        String PCOffsetExtended="";

        String inputFilePath = "input.txt";



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


        ArrayList<String> outputHex = new ArrayList<String>();         // don't forget the convert binary to hex for each if statement !!!
        for ( int i = 0; i < myOpcodeList.size(); i++ ) {
            String currentInstHex = "null";
            if (myOpcodeList.get(i).get(0).equals(opcodeList[0][0])) {// AND
                currentInstHex=andFuncForm(opcodeList[0][1],i);
            }
            else if (myOpcodeList.get(i).get(0).equals(opcodeList[1][0])) {                    // ANDI
                currentInstHex=andIFuncForm(opcodeList[1][1],i);
            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[2][0])) {                  // ADD
                currentInstHex=andFuncForm(opcodeList[2][1], i);
            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[3][0])) {                  // ADDI
                currentInstHex=andIFuncForm(opcodeList[3][1],i);
            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[4][0])) {                  //  OR
                currentInstHex=andFuncForm(opcodeList[4][1], i);
            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[5][0])) {                 //  ORI
                currentInstHex= andIFuncForm(opcodeList[5][1], i);
            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[6][0])) {                 //  XOR
                currentInstHex=andFuncForm(opcodeList[6][1], i);
            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[7][0])) {                 // XORI
                currentInstHex=andIFuncForm(opcodeList[7][1],i);
            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[8][0])) {                 // LD

                dest = convertRegistersToBinary(myOpcodeList.get(i).get(1));
                addr = convertBinary(Integer.parseInt(myOpcodeList.get(i).get(2))); // CAN ADDRESS BE NEGATIVE ?
                PCOffsetExtended= String.format("%10s",addr).replaceAll(" ", "0");

                String binaryResult= "";
                binaryResult=opcodeList[8][1]+dest+PCOffsetExtended; //ld instruction binary  result
                System.out.println(binaryResult);

                currentInstHex=binaryResult;
            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[9][0])) {               // ST

                src1 = convertRegistersToBinary(myOpcodeList.get(i).get(1));
                addr = convertBinary(Integer.parseInt(myOpcodeList.get(i).get(2))); // CAN ADDRESS BE NEGATIVE ?
                PCOffsetExtended= String.format("%10s",addr).replaceAll(" ", "0");

                String binaryResult= "";
                binaryResult=opcodeList[9][1]+src1+PCOffsetExtended; //ld instruction binary  result
                System.out.println(binaryResult);

                currentInstHex=binaryResult;
            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[10][0])) {  // JUMP
                addr= myOpcodeList.get(i).get(1);

//                if( Integer.parseInt(addr) > -8192 &&  Integer.parseInt(addr) > 8191){
//                    System.out.println("PC-Relative address must be between -2048 and 2047 !");
//                   break;
//                }
                String sign;
                if(Integer.parseInt(addr) < 0) {
                    sign = "1";
                }
                else{
                    sign = "0";
                }

                currentInstHex=JmpFunction(opcodeList[10][1],Integer.parseInt(addr));


            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[11][0])) {  //  BEQ

                opp1 =myOpcodeList.get(i).get(1);
                opp2= myOpcodeList.get(i).get(2);

                if(Integer.parseInt(myOpcodeList.get(i).get(3))>32){
                    System.out.println("Address can\'t be greater than 32 for beq !"); //2^5
                    break;
                }

                addr= convertBinary(Integer.parseInt(myOpcodeList.get(i).get(3)));

                BranchInstruction(11,i,opp1,opp2,addr);


            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[12][0])) {  //  BGT
                opp1 =myOpcodeList.get(i).get(1);
                opp2= myOpcodeList.get(i).get(2);

                if(Integer.parseInt(myOpcodeList.get(i).get(3))>32){
                    System.out.println("Address can\'t be greater than 32 for bgt !"); //2^5
                    break;
                }

                addr= convertBinary(Integer.parseInt(myOpcodeList.get(i).get(3)));

                BranchInstruction(12,i,opp1,opp2,addr);

            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[13][0])) {   // BLT
                opp1 =myOpcodeList.get(i).get(1);
                opp2= myOpcodeList.get(i).get(2);

                if(Integer.parseInt(myOpcodeList.get(i).get(3))>32){
                    System.out.println("Address can\'t be greater than 32 for blt !"); //2^5
                    break;
                }

                addr= convertBinary(Integer.parseInt(myOpcodeList.get(i).get(3)));

                BranchInstruction(13,i,opp1,opp2,addr);

            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[14][0])) {    // BGE
                opp1 =myOpcodeList.get(i).get(1);
                opp2= myOpcodeList.get(i).get(2);

                if(Integer.parseInt(myOpcodeList.get(i).get(3))>32){
                    System.out.println("Address can\'t be greater than 32 for bge !"); //2^5
                    break;
                }

                addr= convertBinary(Integer.parseInt(myOpcodeList.get(i).get(3)));

                BranchInstruction(14,i,opp1,opp2,addr);

            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[15][0])) {// BLE
                opp1 =myOpcodeList.get(i).get(1);
                opp2= myOpcodeList.get(i).get(2);

                if(Integer.parseInt(myOpcodeList.get(i).get(3))>32){
                    System.out.println("Address can\'t be greater than 32 for ble !"); //2^5
                    break;
                }

                addr= convertBinary(Integer.parseInt(myOpcodeList.get(i).get(3)));

                BranchInstruction(15,i,opp1,opp2,addr);
            }
        }


    }
    static String binaryToHex(String binary){
        String sumHex = "0x";
        Map<String, String> hexMaps = new HashMap<>();

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

        for (int i = 0; i < binary.length(); i++) {
            sumHex =   sumHex.concat(hexMaps.get(binary.substring(i*4,(i+1)*4)));
        }
        return sumHex;
    }

    // BRANCH INSTS
    static String BranchInstruction(int indexOfOpcode,int indexOfInst,String operation1,String operation2, String address){

        String n,p,z;
        String PCOffsetExtended;
        String binaryResult ="";
        String opp1_Converted;
        String opp2_Converted;

        PCOffsetExtended = String.format("%5s", address).replaceAll(" ", "0"); //PcOffset allocate 5 bit of adress if needed, it can be extended

        if(myOpcodeList.get(indexOfInst).get(0).equals("BQE")) {
            n = "0";   //n p z allocate 3 bit of adress
            p = "0";


            if (Integer.parseInt(operation1) == Integer.parseInt(operation2)) { //BQE
                z = "1";



                opp1_Converted = convertBinary(Integer.parseInt(operation1));
                opp2_Converted = convertBinary(Integer.parseInt(operation2));
                binaryResult = opcodeList[indexOfOpcode][1] + opp1_Converted + opp2_Converted + n + p + z + PCOffsetExtended; //ld instruction binary  result
                System.out.println(binaryResult);

            }
            else{
                z = "0";



                opp1_Converted = convertBinary(Integer.parseInt(operation1));
                opp2_Converted = convertBinary(Integer.parseInt(operation2));
                binaryResult = opcodeList[indexOfOpcode][1] + opp1_Converted + opp2_Converted + n + p + z + PCOffsetExtended; //ld instruction binary  result
               // System.out.println(binaryResult);
            }
        }
        else if(myOpcodeList.get(indexOfInst).get(0).equals("BLE")) {
            z = "0";
            p = "0";

            if (Integer.parseInt(operation1) < Integer.parseInt(operation2)) { // BLE
                n = "1";




                opp1_Converted = convertBinary(Integer.parseInt(operation1));
                opp2_Converted = convertBinary(Integer.parseInt(operation2));
                binaryResult = opcodeList[indexOfOpcode][1] + opp1_Converted + opp2_Converted + n + p + z + PCOffsetExtended; //ld instruction binary  result
                //System.out.println(binaryResult);
            }
            else{
                n = "0";



                opp1_Converted = convertBinary(Integer.parseInt(operation1));
                opp2_Converted = convertBinary(Integer.parseInt(operation2));
                binaryResult = opcodeList[indexOfOpcode][1] + opp1_Converted + opp2_Converted + n + p + z + PCOffsetExtended; //ld instruction binary  result
               // System.out.println(binaryResult);
            }
        }
        else if(myOpcodeList.get(indexOfInst).get(0).equals("BGT")) {


            z = "0";
            n = "0";
            if (Integer.parseInt(operation1) < Integer.parseInt(operation2)) { //BGT
                p = "1";

                opp1_Converted = convertBinary(Integer.parseInt(operation1));
                opp2_Converted = convertBinary(Integer.parseInt(operation2));
                binaryResult = opcodeList[indexOfOpcode][1] + opp1_Converted + opp2_Converted + n + p + z + PCOffsetExtended; //ld instruction binary  result
                //System.out.println(binaryResult);
            }
            else{
                p = "0";



                opp1_Converted = convertBinary(Integer.parseInt(operation1));
                opp2_Converted = convertBinary(Integer.parseInt(operation2));
                binaryResult = opcodeList[indexOfOpcode][1] + opp1_Converted + opp2_Converted + n + p + z + PCOffsetExtended; //ld instruction binary  result
              //  System.out.println(binaryResult);
            }
        }
        else if(myOpcodeList.get(indexOfInst).get(0).equals("BGT")) {
            p = "0";

            if (Integer.parseInt(operation1) <= Integer.parseInt(operation2)) {  //BLE
                z = "1";
                n = "1";
                p = "0";

                opp1_Converted = convertBinary(Integer.parseInt(operation1));
                opp2_Converted = convertBinary(Integer.parseInt(operation2));
                binaryResult = opcodeList[indexOfOpcode][1] + opp1_Converted + opp2_Converted + n + p + z + PCOffsetExtended; //ld instruction binary  result
               // System.out.println(binaryResult);
            }
            else{
                z = "0";
                n = "0";
                opp1_Converted = convertBinary(Integer.parseInt(operation1));
                opp2_Converted = convertBinary(Integer.parseInt(operation2));
                binaryResult = opcodeList[indexOfOpcode][1] + opp1_Converted + opp2_Converted + n + p + z + PCOffsetExtended; //ld instruction binary  result
             //   System.out.println(binaryResult);
            }
        }
        else if(myOpcodeList.get(indexOfInst).get(0).equals("BGT")) {
            n = "0";

            if (Integer.parseInt(operation1) < Integer.parseInt(operation2)) {  //BGE
                z = "1";
                n = "0";
                p = "1";



                opp1_Converted = convertBinary(Integer.parseInt(operation1));
                opp2_Converted = convertBinary(Integer.parseInt(operation2));
                binaryResult = opcodeList[indexOfOpcode][1] + opp1_Converted + opp2_Converted + n + p + z + PCOffsetExtended; //ld instruction binary  result
               // System.out.println(binaryResult);
            }
            else{
                z = "0";
                p = "0";
                opp1_Converted = convertBinary(Integer.parseInt(operation1));
                opp2_Converted = convertBinary(Integer.parseInt(operation2));
                binaryResult = opcodeList[indexOfOpcode][1] + opp1_Converted + opp2_Converted + n + p + z + PCOffsetExtended; //ld instruction binary  result
              //  System.out.println(binaryResult);
            }
        }
        return binaryResult ;
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

    static String JmpFunction(String operation, int addr) {
        return "";
    }


    static String  andFuncForm(String operation,int opIndex){
        String binaryResult= "";
        if (operation.equals("ADD") || operation.equals( "AND")){

            dest = convertRegistersToBinary(myOpcodeList.get(opIndex).get(1));
            src1 = convertRegistersToBinary(myOpcodeList.get(opIndex).get(2));
            src2 = String.format("%8s",  convertRegistersToBinary(myOpcodeList.get(opIndex).get(3))    ).replaceAll(" ", "0");

            binaryResult=opcodeList[0][1]+dest+src1+src2;

        }
        return  binaryResult;
    }

    static String  andIFuncForm(String operation,int opIndex){
        String binaryResult= "";
        if (operation.equals("ADDI") || operation.equals( "ANDI")){
            dest = convertRegistersToBinary(myOpcodeList.get(opIndex).get(1));
            src1 = convertRegistersToBinary(myOpcodeList.get(opIndex).get(2));
            // Since immediate can be negative or positive, determine it's sign

            if(Integer.parseInt(myOpcodeList.get(opIndex).get(3))<0){
                imm=Negative_signed_numb(myOpcodeList.get(opIndex).get(3));
            }
            else
                imm=Positive_signed_numb(myOpcodeList.get(opIndex).get(3));


            binaryResult=opcodeList[1][1]+dest+src1+imm;
        }
        return  binaryResult;
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
                  //  System.out.println("Binary result "+binaryResult);
                }
        return binaryResult;
    }

/*    static String  convertImmidateToBinary(String src1 ){ // immidate value 8 bit = 1 bit sign 7 bit value

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
    }*/


    static String  LD(String op,String dest,String address){


        return "";
    }

    static String  ST(String op,String dest,String address){


        return "";
    }
}

