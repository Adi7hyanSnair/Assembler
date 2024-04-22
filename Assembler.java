import java.io.*;
import java.util.ArrayList;
import java.util.*;
public class Assembler{
    static Symbol_Table s1 = new Symbol_Table();
    public Assembler(){
        s1.pre_C();
    }
    static int var = 16;

    ////REMOVE WHITE SPACE AND COMMENTS
    public void remove_whitespace() throws IOException {
        BufferedReader read = new BufferedReader(new FileReader("RAW.asm"));

        ArrayList<String> lines = new ArrayList<String>();
        String line = read.readLine();
        while (line != null) {
            lines.add(line);
            line = read.readLine();
        }
        read.close();
        ArrayList<String> nowhitespace = new ArrayList<>();
        for(String i : lines){
            if(i.contains(" ")){
                i = i.replace(" ","");
            }
            if (i.equals("") || i.charAt(0) == '/'){
                continue;
            }
            else{
                i = i.replace("\n", "").replace("\t", "").replace(" ", "");
                if (i.indexOf("/") != -1){
                    String[] substring = i.split("//");
                    nowhitespace.add(substring[0]);
                } else {
                    nowhitespace.add(i);
                }
            }
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter("nospace.asm"));
        for (String i:nowhitespace){
            writer.write(i);
            writer.newLine();
        }
        writer.close();
    }


    ///FIRST PASS (ADDING LABELS TO SYMBOL TABLE)
    public void firstpass(String input_File) throws IOException{
        BufferedReader r2 = new BufferedReader(new FileReader((input_File)));
        String line;
        int line_num = 1;
        while((line= r2.readLine()) != null){
            String code;
            if (line.isEmpty()){
                continue;
            }
            if(line.startsWith("(")){
                String label = line.substring(1,line.indexOf(")"));
                if(!s1.contains(label)){
                    s1.labels.put(label.toLowerCase(),line_num+1);
                }
                line_num++;
            }
            else{
                line_num++;
            }
        }
        r2.close();
    }


    ///SECOND PASS(C-INSTRUCTION,A-INSTRUCTION,VARIABLES)
    public void secondpass(String input_File,String output_file) throws IOException{
        BufferedReader b1 = new BufferedReader(new FileReader(input_File));
        String l;
        FileWriter w1 = new FileWriter(output_file);
        while((l = b1.readLine()) != null){
            String bin_code;
            if(l.isEmpty() || l.startsWith("(")){
                continue;
            }
            if(l.startsWith("@")){
                String c = l.substring(1);
                if(Character.isDigit(c.charAt(0))){
                    int dig = Integer.parseInt(c);
                    bin_code = Integer.toBinaryString(dig);
                    bin_code = String.format("%016d", Integer.parseInt(bin_code));
                }
                else if(Character.isLetter(c.charAt(c.length()-1))){
                    if(s1.contains(c)){
                        int v = s1.labels.get(c);
                        bin_code = Integer.toBinaryString(v);
                        bin_code = String.format("%016d",Integer.parseInt(bin_code));
                    }
                    else if(s1.contains(c)) {
                        int v = s1.variables.get(c);
                        bin_code = Integer.toBinaryString(v);
                        bin_code = String.format("%016d", Integer.parseInt(bin_code));
                    }
                    else{
                        s1.variables.put(c, var);
                        bin_code = Integer.toBinaryString(var);
                        bin_code = String.format("%016d", Integer.parseInt(bin_code));
                        var++;
                    }
                }
                else{
                    bin_code = Integer.toBinaryString(s1.Predefined_symbols(c));
                    bin_code = String.format("%016d", Integer.parseInt(bin_code));
                }
                w1.write(bin_code+"\n");
            }
            else{
                String dest;
                String comp;
                String jump;
                if(l.contains("=")){
                    String[] parts = l.split("=");
                    dest = parts.length>1 ? parts[0]:null;
                    String compJump = parts[parts.length -1];
                    if(compJump.contains(";")){
                        String[] n  = compJump.split(";");
                        comp = n[0];
                        jump = n[n.length-1];
                    }
                    else{
                        comp = compJump;
                        jump = "null";
                    }
                }
                else{
                    String[] j = l.split(";");
                    comp = j[0];
                    dest = "null";
                    jump = j[j.length-1];
                }
                String compcode = s1.Computation.get(comp);
                String jumpcode = s1.jump.get(jump);
                String destcode = s1.Destination.get(dest);
                String bin_code2 = "111"+compcode+destcode+jumpcode;
                w1.write(bin_code2+"\n");
            }
        }
        b1.close();
        w1.close();
    }
    public static void main(String[] args) throws IOException {
        Assembler a = new Assembler();
        a.remove_whitespace();
        a.firstpass("nospace.asm");
        a.secondpass("nospace.asm","binarycode.hack");
    }
}