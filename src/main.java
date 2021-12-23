import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
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

        // print
        for ( int i = 0; i < myOpcodeList.size(); i++ ) {
            for ( int j = 0; j < myOpcodeList.get(i).size(); j++ ) {
                System.out.println(myOpcodeList.get(i).get(j));
            }
        }
        String dest;
        String src1;
        String src2;
        String imm;
        String opp1;
        String opp2;
        String addr;


        for ( int i = 0; i < myOpcodeList.size(); i++ ) {
            if (myOpcodeList.get(i).get(0).equals(opcodeList[0][0])) {// AND
                dest = myOpcodeList.get(i).get(1);
                src1 = myOpcodeList.get(i).get(2);
                src2 = myOpcodeList.get(i).get(3);

                  andFuncForm(opcodeList[0][1],dest,src1,src2);
            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[1][0])) {   // ANDI
                dest = myOpcodeList.get(i).get(1);
                src1 = myOpcodeList.get(i).get(2);
                imm = myOpcodeList.get(i).get(3);

                andIFuncForm(opcodeList[1][1],dest,src1,imm);



            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[2][0])) { // ADD

                dest = myOpcodeList.get(i).get(1);
                src1 = myOpcodeList.get(i).get(2);
                src2 = myOpcodeList.get(i).get(3);

                andFuncForm(opcodeList[2][1], dest, src1, src2);

            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[3][0])) {// ADDI
                dest = myOpcodeList.get(i).get(1);
                src1 = myOpcodeList.get(i).get(2);
                imm = myOpcodeList.get(i).get(3);

                andIFuncForm(opcodeList[3][1],dest,src1,imm);

            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[4][0])) {//  OR

                dest = myOpcodeList.get(i).get(1);
                src1 = myOpcodeList.get(i).get(2);
                src2 = myOpcodeList.get(i).get(3);

                andFuncForm(opcodeList[4][1], dest, src1, src2);

            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[5][0])) {//  ORI
                System.out.println(opcodeList[5][1]);
            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[6][0])) {  //  XOR
                System.out.println(opcodeList[6][1]);

                dest = myOpcodeList.get(i).get(1);
                src1 = myOpcodeList.get(i).get(2);
                src2 = myOpcodeList.get(i).get(3);

                andFuncForm(opcodeList[6][1], dest, src1, src2);

            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[7][0])) {   // XORI
                System.out.println(opcodeList[7][1]);
            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[8][0])) { // LD


                dest = myOpcodeList.get(i).get(1);
                addr = myOpcodeList.get(i).get(2);


                if (Integer.parseInt(addr) < 0) {
                    System.out.println("Address for LD must be greater than 0 !");
                    break;


                }
                if ( Integer.parseInt(addr) > 1024){  // 2'10
                    System.out.println("Address can\'t be greater than 256 for LD !");
                    break;
                }

            // eksik

            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[9][0])) {// ST
                System.out.println(opcodeList[9][1]);
            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[10][0])) {  // JUMP
                System.out.println(opcodeList[10][1]);
            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[11][0])) {  //  BEQ
                System.out.println(opcodeList[11][1]);
            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[12][0])) {  //  BGT
                System.out.println(opcodeList[12][1]);
            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[13][0])) {   // BLT
                System.out.println(opcodeList[13][1]);
            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[14][0])) {    // BGE
                System.out.println(opcodeList[14][1]);
            } else if (myOpcodeList.get(i).get(0).equals(opcodeList[15][0])) {// BLE
                System.out.println(opcodeList[15][1]);
            } else {
                System.out.println("Please enter correct input.");
            }
        }


    }


    static String  andFuncForm(String operation,String dest,String src1,String src2){
        if (operation.equals("ADD") || operation.equals( "AND")){

           // binary_register_values = self.__convert_registers_to_binary(dest = dest, src_1 = src_1, src_2 = src_2)

            //binary_code += ''.join(binary_register_values)
        }
        return " ";
    }

    static String  andIFuncForm(String operation,String dest,String src1,String imm){
        if (operation.equals("ADD") || operation.equals( "AND")){

            // binary_register_values = self.__convert_registers_to_binary(dest = dest, src_1 = src_1, src_2 = src_2)

            //binary_code += ''.join(binary_register_values)
        }
        return " ";
    }

    static String  convertRegistersToBinary(){


        return "";
    }



}

