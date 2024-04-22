import java.util.HashMap;

public class Symbol_Table {
    public static HashMap<String, Integer> Predefined_Values = new HashMap<>();
    public static HashMap<String, String> Computation = new HashMap<>();
    public static HashMap<String, String> Destination = new HashMap<>();
    public static HashMap<String, String> jump = new HashMap<>();
    public static HashMap<String,Integer> labels = new HashMap<>();
    public static HashMap<String,Integer> variables = new HashMap<>();

    public static void pre_C(){
        //Predefine Values
        Predefined_Values.put("R0",0);
        Predefined_Values.put("R1",1);
        Predefined_Values.put("R2",2);
        Predefined_Values.put("R3",3);
        Predefined_Values.put("R4",4);
        Predefined_Values.put("R5",5);
        Predefined_Values.put("R6",6);
        Predefined_Values.put("R7",7);
        Predefined_Values.put("R8",8);
        Predefined_Values.put("R9",9);
        Predefined_Values.put("R10",10);
        Predefined_Values.put("R11",11);
        Predefined_Values.put("R12",12);
        Predefined_Values.put("R13",13);
        Predefined_Values.put("R14",14);
        Predefined_Values.put("R15",15);
        Predefined_Values.put("SCREEN",16384);
        Predefined_Values.put("KBD",24576);
        Predefined_Values.put("SP",0);
        Predefined_Values.put("LCL",1);
        Predefined_Values.put("ARG",2);
        Predefined_Values.put("THIS",3);
        Predefined_Values.put("THAT",4);
        Predefined_Values.put("STOP",18);
        Predefined_Values.put("END",22);

        //C - Instruction (Computation)
        Computation.put("0", "0101010");
        Computation.put("1", "0111111");
        Computation.put("-1", "0111010");
        Computation.put("D", "0001100");
        Computation.put("A", "0110000");
        Computation.put("!D", "0001101");
        Computation.put("!A", "0110001");
        Computation.put("-D", "0001111");
        Computation.put("-A", "0110011");
        Computation.put("D+1", "0011111");
        Computation.put("A+1", "0110111");
        Computation.put("D-1", "0001110");
        Computation.put("A-1", "0110010");
        Computation.put("D+A", "0000010");
        Computation.put("A+D", "0000010");
        Computation.put("D-A", "0010011");
        Computation.put("A-D", "0000111");
        Computation.put("D&A", "0000000");
        Computation.put("D|A", "0010101");
        Computation.put("M", "1110000");
        Computation.put("!M", "1110001");
        Computation.put("-M", "1110011");
        Computation.put("M+1", "1110111");
        Computation.put("M-1", "1110010");
        Computation.put("D+M", "1000010");
        Computation.put("M+D", "1000010");
        Computation.put("D-M", "1010011");
        Computation.put("M-D", "1000111");
        Computation.put("D&M", "1000000");
        Computation.put("D|M", "1010101");

        //C - Instruction (Destination)
        Destination.put("null", "000");
        Destination.put("M", "001");
        Destination.put("D", "010");
        Destination.put("MD", "011");
        Destination.put("A", "100");
        Destination.put("AM", "101");
        Destination.put("AD", "110");
        Destination.put("AMD", "111");

        //C - Instruction (Jump)
        jump.put("null", "000");
        jump.put("JGT", "001");
        jump.put("JEQ", "010");
        jump.put("JGE", "011");
        jump.put("JLT", "100");
        jump.put("JNE", "101");
        jump.put("JLE", "110");
        jump.put("JMP", "111");
    }
    public static int Predefined_symbols(String str2){
        int v = Predefined_Values.get(str2);
        return v;
    }
    public static String C_instruction(String str1, String str2){
        String a = null;
        if(str1.equals("dest")){
            a = Destination.get(str2);
        }
        else if(str1.equals("comp")){
            a = Computation.get(str2);
        }
        else if(str1.equals("jump")){
            a = jump.get(str2);
        }
        return a;
    }
    public static boolean contains(String str1){
        if(labels.get(str1) == null){
            return false;
        }
        else{
            return true;
        }
    }
    public static void main(String[] args){
    }
}
