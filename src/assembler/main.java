package assembler;/*
Emine Çığ          150118012
Sena Altıntaş      150118007
Mehmet Akif Akkaya 150118041
Süleyman Keleş     150118039

 */

import java.io.*;
import java.util.*;

public class main {

    // Instruction name and Opcodes
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

    // 2D ArrayList to hold instructions from input file as separated each value
    static ArrayList<ArrayList<String>> myOpcodeList = new ArrayList<ArrayList<String>>();

    // register variables
    static String dest;
    static String src1;
    static String src2;
    static String imm;
    static  String opp1;
    static  String opp2;
    static  String addr;


    public static void main(String[] args) throws FileNotFoundException {
        //branch variables
        String n = "0";
        String p = "0";
        String z = "0";

        String PCOffsetExtended="";

        // inout and output files
        String inputFilePath  = "input.txt";
        String outputFilePath = "output.hex";

        // read instructions from input file
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

        // ArrayList to hold output hex values
        ArrayList<String> outputHex = new ArrayList<String>();


        ArrayList<String> outputBinary = new ArrayList<String>();

        // determine instruction type for each instruction then does the operation of converting binary bit sequence
        for ( int i = 0; i < myOpcodeList.size(); i++ ) {
            String currentInstHex = "";
            String currentInstBinary = "";

            if (myOpcodeList.get(i).get(0).toString().equals(opcodeList[0][0].trim())) {// AND
                currentInstBinary=andFuncForm(opcodeList[0][1],i);
                currentInstHex=binaryToHex(currentInstBinary);

            }
            else if (myOpcodeList.get(i).get(0).equals(opcodeList[1][0])) {// ANDI
                currentInstBinary=andIFuncForm(opcodeList[1][1],i);
                currentInstHex=binaryToHex(currentInstBinary);
            }
            else if (myOpcodeList.get(i).get(0).equals(opcodeList[2][0])) { //                   ADD
                currentInstBinary=andFuncForm(opcodeList[2][1], i);
                currentInstHex=binaryToHex(currentInstBinary);

            }
            else if (myOpcodeList.get(i).get(0).equals(opcodeList[3][0])) {  //                 ADDI
                currentInstBinary=andIFuncForm(opcodeList[3][1],i);
                currentInstHex=binaryToHex(currentInstBinary);
            }

            else if (myOpcodeList.get(i).get(0).equals(opcodeList[4][0])) { //                   OR
                currentInstBinary=andFuncForm(opcodeList[4][1], i);
                currentInstHex=binaryToHex(currentInstBinary);


            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[5][0])) {//                  ORI
                currentInstBinary=andIFuncForm(opcodeList[5][1], i);
                currentInstHex= binaryToHex(currentInstBinary);
            }

            else if (myOpcodeList.get(i).get(0).equals(opcodeList[6][0])) {  //                  XOR
                currentInstBinary=andFuncForm(opcodeList[6][1], i);
                currentInstHex=binaryToHex(currentInstBinary);
            }
            else if (myOpcodeList.get(i).get(0).equals(opcodeList[7][0])) { //                   XORI
                currentInstBinary=andIFuncForm(opcodeList[7][1],i);
                currentInstHex=binaryToHex(currentInstBinary);
            }
            else if (myOpcodeList.get(i).get(0).equals(opcodeList[8][0])) {//                     LD

                dest = convertRegistersToBinary(myOpcodeList.get(i).get(1));
                addr = convertBinary(Integer.parseInt(myOpcodeList.get(i).get(2))); // CAN ADDRESS BE NEGATIVE ?
                PCOffsetExtended= String.format("%10s",addr).replaceAll(" ", "0");
                currentInstBinary=opcodeList[8][1]+dest+PCOffsetExtended;
                currentInstHex=binaryToHex(currentInstBinary);

            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[9][0])) {               // ST

                src1 = convertRegistersToBinary(myOpcodeList.get(i).get(1));
                addr = convertBinary(Integer.parseInt(myOpcodeList.get(i).get(2)));

                PCOffsetExtended= String.format("%10s",addr).replaceAll(" ", "0");
                currentInstBinary=opcodeList[9][1]+src1+PCOffsetExtended; //ld instruction binary  result
                currentInstHex=binaryToHex(currentInstBinary);

            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[10][0])) {  // JUMP
                addr= myOpcodeList.get(i).get(1);

                if( Integer.parseInt(addr) > -8192 &&  Integer.parseInt(addr) > 8191){ // checks range of the addr value
                    System.out.println("PC-Relative address must be between -2048 and 2047 !");
                   break;
                }
                if(Integer.parseInt(addr)<0){
                    addr= Negative_signed_numb(myOpcodeList.get(i).get(1) ,14);
                }
                else{
                    addr=Positive_signed_numb(myOpcodeList.get(i).get(1) ,14);
                }

                currentInstBinary=opcodeList[10][1]+addr;
                currentInstHex=binaryToHex(currentInstBinary);


            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[11][0])) {  //  BEQ
                opp1 =myOpcodeList.get(i).get(1);
                opp2= myOpcodeList.get(i).get(2);

                if(Integer.parseInt(myOpcodeList.get(i).get(3))>32){
                    System.out.println("Address can\'t be greater than 32 for beq !"); //2^5
                    break;
                }

                if(Integer.parseInt(myOpcodeList.get(i).get(3))<0){
                    addr= Negative_signed_numb(myOpcodeList.get(i).get(3),3 );
                }
                else{
                    addr=Positive_signed_numb(myOpcodeList.get(i).get(3),3 );
                }

                currentInstBinary=BranchInstruction(11,i,opp1,opp2,addr);
                currentInstHex=binaryToHex(currentInstBinary);


            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[12][0])) {  //  BGT
                opp1 =myOpcodeList.get(i).get(1);
                opp2= myOpcodeList.get(i).get(2);

                if(Integer.parseInt(myOpcodeList.get(i).get(3))>32){
                    System.out.println("Address can\'t be greater than 32 for bgt !"); //2^5
                    break;
                }

                if(Integer.parseInt(myOpcodeList.get(i).get(3))<0){
                    addr= Negative_signed_numb(myOpcodeList.get(i).get(3) ,3);
                }
                else{
                    addr=Positive_signed_numb(myOpcodeList.get(i).get(3),3 );
                }

                currentInstBinary=BranchInstruction(12,i,opp1,opp2,addr);
                currentInstHex=binaryToHex(currentInstBinary);

            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[13][0])) {   // BLT
                opp1 =myOpcodeList.get(i).get(1);
                opp2= myOpcodeList.get(i).get(2);

                if(Integer.parseInt(myOpcodeList.get(i).get(3))>32){
                    System.out.println("Address can\'t be greater than 32 for blt !"); //2^5
                    break;
                }

                if(Integer.parseInt(myOpcodeList.get(i).get(3))<0){
                    addr= Negative_signed_numb(myOpcodeList.get(i).get(3) ,3);
                }
                else{
                    addr=Positive_signed_numb(myOpcodeList.get(i).get(3),3 );
                }

                currentInstBinary=BranchInstruction(13,i,opp1,opp2,addr);
                currentInstHex=binaryToHex(currentInstBinary);

            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[14][0])) {    // BGE
                opp1 =myOpcodeList.get(i).get(1);
                opp2= myOpcodeList.get(i).get(2);

                if(Integer.parseInt(myOpcodeList.get(i).get(3))>32){
                    System.out.println("Address can\'t be greater than 32 for bge !"); //2^5
                    break;
                }

                if(Integer.parseInt(myOpcodeList.get(i).get(3))<0){
                    addr= Negative_signed_numb(myOpcodeList.get(i).get(3),3 );
                }
                else{
                    addr=Positive_signed_numb(myOpcodeList.get(i).get(3),3 );
                }

                currentInstBinary=BranchInstruction(14,i,opp1,opp2,addr);
                currentInstHex=binaryToHex(currentInstBinary);

            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[15][0])) {// BLE
                opp1 =(myOpcodeList.get(i).get(1));
                opp2= (myOpcodeList.get(i).get(2));

                if(Integer.parseInt(myOpcodeList.get(i).get(3))>32){
                    System.out.println("Address can\'t be greater than 32 for ble !"); //2^5
                    break;
                }

                if(Integer.parseInt(myOpcodeList.get(i).get(3))<0){
                    addr= Negative_signed_numb(myOpcodeList.get(i).get(3) ,3);
                }
                else{
                    addr=Positive_signed_numb(myOpcodeList.get(i).get(3),3 );
                }

                currentInstBinary=BranchInstruction(15,i,opp1,opp2,addr);
                currentInstHex=binaryToHex(currentInstBinary);
            }

            // add to the binary list
            outputBinary.add(currentInstBinary);
            // add to the hex list
            outputHex.add(currentInstHex);

        }


        // write output hex values to the output.txt file
        try {
            FileWriter myWriter = new FileWriter(outputFilePath);
            myWriter.write("v2.raw\n");
            int j = 0;
            for ( String hex : outputHex ){
                if( j<5){
                    myWriter.write(hex + " ");
                    j++;
                }
                else{
                    myWriter.write(hex + " ");
                    myWriter.write( " \n");
                    j=0;
                }
            }

            myWriter.close();

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


    }
    // converts binary bi sequence to hex value returns as string
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


        for (int i = 0; i < binary.length()-3; ) {

            if (i < 2){
                sumHex = sumHex.concat(hexMaps.get(binary.substring(0, 2)));
                i = i + 2;
            }
            else if (i<binary.length()-4){
                //2 6 10 14
                sumHex = sumHex.concat(hexMaps.get(binary.substring(i, i + 4)));
                i=i+4;
            }
            else{
                sumHex = sumHex.concat(hexMaps.get(binary.substring(i)));
                i=binary.length();
            }
        }
        return sumHex;
    }

    // Branch Instructions , determines current instruction type then returns binary bit sequence of this instruction .
    static String BranchInstruction(int indexOfOpcode,int indexOfInst,String operation1,String operation2, String address){

        String n,p,z;
        String PCOffsetExtended;
        String binaryResult ="";
        String opp1_Converted;
        String opp2_Converted;

        // does bit extension of address
        PCOffsetExtended = String.format("%3s", address).replaceAll(" ", "0"); //PcOffset allocate 3 bit of adress if needed, it can be extended

        if(myOpcodeList.get(indexOfInst).get(0).equals("BEQ")) { //BEQ
            n = "0";
            p = "0";

            if (operation1.equals(operation2)) {
                z = "1";

                opp1_Converted =convertRegistersToBinary(myOpcodeList.get(indexOfInst).get(1));
                opp2_Converted= convertRegistersToBinary(myOpcodeList.get(indexOfInst).get(2));
                binaryResult = opcodeList[indexOfOpcode][1] + opp1_Converted + opp2_Converted + n + z + p + PCOffsetExtended; //ld instruction binary  result

            }
            else{
                z = "0";

                opp1_Converted =convertRegistersToBinary(myOpcodeList.get(indexOfInst).get(1));
                opp2_Converted= convertRegistersToBinary(myOpcodeList.get(indexOfInst).get(2));
                binaryResult = opcodeList[indexOfOpcode][1] + opp1_Converted + opp2_Converted  + n + z + p + PCOffsetExtended; //ld instruction binary  result

            }
        }
        else if(myOpcodeList.get(indexOfInst).get(0).equals("BLT")) {
            z = "0";
            p = "0";

            if (Integer.parseInt(operation1.split("R")[1]) < Integer.parseInt(operation2.split("R")[1])) { // BLT
                n = "1";

                opp1_Converted =convertRegistersToBinary(myOpcodeList.get(indexOfInst).get(1));
                opp2_Converted= convertRegistersToBinary(myOpcodeList.get(indexOfInst).get(2));

                binaryResult = opcodeList[indexOfOpcode][1] + opp1_Converted + opp2_Converted  + n + z + p + PCOffsetExtended; //ld instruction binary  result

            }
            else{
                n = "0";

                opp1_Converted =convertRegistersToBinary(myOpcodeList.get(indexOfInst).get(1));
                opp2_Converted= convertRegistersToBinary(myOpcodeList.get(indexOfInst).get(2));
                binaryResult = opcodeList[indexOfOpcode][1] + opp1_Converted + opp2_Converted  + n + z + p + PCOffsetExtended; //ld instruction binary  result

            }
        }
        else if(myOpcodeList.get(indexOfInst).get(0).equals("BGT")) {

            z = "0";
            n = "0";
            if (Integer.parseInt(operation1.split("R")[1]) > Integer.parseInt(operation2.split("R")[1])) {  //BGT
                p = "1";

                opp1_Converted =convertRegistersToBinary(myOpcodeList.get(indexOfInst).get(1));
                opp2_Converted= convertRegistersToBinary(myOpcodeList.get(indexOfInst).get(2));
                binaryResult = opcodeList[indexOfOpcode][1] + opp1_Converted + opp2_Converted  + n + z + p + PCOffsetExtended; //ld instruction binary  result

            }
            else{
                p = "0";

                opp1_Converted =convertRegistersToBinary(myOpcodeList.get(indexOfInst).get(1));
                opp2_Converted= convertRegistersToBinary(myOpcodeList.get(indexOfInst).get(2));
                binaryResult = opcodeList[indexOfOpcode][1] + opp1_Converted + opp2_Converted  + n + z + p + PCOffsetExtended; //ld instruction binary  result

            }
        }
        else if(myOpcodeList.get(indexOfInst).get(0).equals("BLE")) {
            p = "0";

            if (Integer.parseInt(operation1.split("R")[1]) <= Integer.parseInt(operation2.split("R")[1])) {  //BLE
                z = "1";
                n = "1";


                opp1_Converted =convertRegistersToBinary(myOpcodeList.get(indexOfInst).get(1));
                opp2_Converted= convertRegistersToBinary(myOpcodeList.get(indexOfInst).get(2));
                binaryResult = opcodeList[indexOfOpcode][1] + opp1_Converted + opp2_Converted  + n + z + p + PCOffsetExtended; //ld instruction binary  result


            }
            else{
                z = "0";
                n = "0";

                opp1_Converted =convertRegistersToBinary(myOpcodeList.get(indexOfInst).get(1));
                opp2_Converted= convertRegistersToBinary(myOpcodeList.get(indexOfInst).get(2));
                binaryResult = opcodeList[indexOfOpcode][1] + opp1_Converted + opp2_Converted  + n + z + p +PCOffsetExtended; //ld instruction binary  result

            }
        }
        else if(myOpcodeList.get(indexOfInst).get(0).equals("BGE")) {
            n = "0";

            if (Integer.parseInt(operation1.split("R")[1]) >= Integer.parseInt(operation2.split("R")[1])) {  //BGE
                z = "1";
                p = "1";

                opp1_Converted =convertRegistersToBinary(myOpcodeList.get(indexOfInst).get(1));
                opp2_Converted= convertRegistersToBinary(myOpcodeList.get(indexOfInst).get(2));
                binaryResult = opcodeList[indexOfOpcode][1] + opp1_Converted + opp2_Converted  + n + z + p + PCOffsetExtended; //ld instruction binary  result

            }
            else{
                z = "0";
                p = "0";
                opp1_Converted =convertRegistersToBinary(myOpcodeList.get(indexOfInst).get(1));
                opp2_Converted= convertRegistersToBinary(myOpcodeList.get(indexOfInst).get(2));
                binaryResult = opcodeList[indexOfOpcode][1] + opp1_Converted + opp2_Converted  + n + z + p + PCOffsetExtended; //ld instruction binary  result

            }
        }

        return binaryResult ;
    }

    // converts number to the binary bit sequence on non sign form
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

    // converts pozitive number to the binary bit sequence on two's complement form
    static String Positive_signed_numb(String number,int bitSize) {

        String Real_number = convertBinary(Integer.parseInt(number));
        Real_number = String.format("%0" + (bitSize - Real_number.length()) + "d%s", 0, Real_number); //complements 2 byte

        return Real_number;

    }

    // converts negative number to the binary bit sequence on two's complement form
    static String Negative_signed_numb(String number,int bitSize) {

        String Real_number = convertBinary(Integer.parseInt(number.substring(1)));
        Real_number = String.format("%0" + (bitSize - Real_number.length()) + "d%s", 0, Real_number); //complement 2 byte

        char[] chars = Real_number.toCharArray(); //to access each character
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

    // Function for returns binary for operations which are ADD,AND,OR,XOR instructions have same form .
    static String  andFuncForm(String opcode, int opIndex){
        String binaryResult= "";

            dest = convertRegistersToBinary(myOpcodeList.get(opIndex).get(1));
            src1 = convertRegistersToBinary(myOpcodeList.get(opIndex).get(2));
            src2 = String.format("%6s",  convertRegistersToBinary(myOpcodeList.get(opIndex).get(3))    ).replaceAll(" ", "0");
            binaryResult=opcode+dest+src1+src2;

        return  binaryResult;
    }

    // Function for returns binary for operations which are ADDI,ANDI,ORI,XORI will have same form.
    static String  andIFuncForm(String operation,int opIndex){
        String binaryResult= "";

            dest = convertRegistersToBinary(myOpcodeList.get(opIndex).get(1));
            src1 = convertRegistersToBinary(myOpcodeList.get(opIndex).get(2));
            // Since immediate can be negative or positive, determine it's sign

            if(Integer.parseInt(myOpcodeList.get(opIndex).get(3))<0){
                imm=Negative_signed_numb(myOpcodeList.get(opIndex).get(3),6);
            }
            else
                imm=Positive_signed_numb(myOpcodeList.get(opIndex).get(3),6);


            binaryResult=opcodeList[1][1]+dest+src1+imm;

        return  binaryResult;
    }

        // Split depend on 'R' from register and convert decimal number to binary as non-sign form
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

                }
        return binaryResult;
    }
}

