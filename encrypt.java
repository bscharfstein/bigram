import java.io.*;
import java.util.regex.*;
import java.util.AbstractCollection;

public class encrypt {

//  For all of these ciphers the capitalization invariant must
//  be satisfied or else they break. We might want to fix that
//  Generates normal caesar cipher
    public static String shift(String str, int offset) {
        String nstr = "";
        int num = 0;
        for (int i = 0; i < str.length(); i++) {
            num = (str.charAt(i)==32) ? 64 : str.charAt(i);
            num = (num - 'A' + 1 + offset) % 27 + 'A' - 1;
            nstr += (num == 64) ? (char)32 : (char)num;
        }
        return nstr;
    }

//  Generates an atbash cipher and then applies a caesarg cipher after
    public static String atbash(String str, int offset) {
        String nstr = "";
        int num = 0;
        for (int i = 0; i < str.length(); i++) {
            num = (str.charAt(i)==32) ? 64 : str.charAt(i);
            num = (-1 * (num - 'A' + 1) + 27 + offset ) % 27 + 'A' - 1;
            nstr += (num == 64) ? (char)32 : (char)num;
        }
        return nstr;
    }

//  some code borrowed from http://www.java-forums.org/new-java/17317-very-simple-encryption.html
//  Generates a random cipher
    public static String rando(String str) {
        Random gen = new Random();
        char[] chars = new char[] { 32, 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        char[] randList = new char[27];

        int replaced = 0;
        while(replaced < 27)
        {
            int num = gen.nextInt(27);
            if(randList[num] == '\u0000')
            {
                randList[num] = chars[replaced];
                replaced++;
            }
        }

        String nstr = "";
        int numo = 0;
        for (int i = 0; i < str.length(); i++) {
            numo = (str.charAt(i) == 32) ? 64 : str.charAt(i);
            numo = (numo - 'A' + 1) % 27;
            nstr += randList[numo];
        }
//        **for checking**
//        System.out.println(nstr);
//
//        for(int i = 0; i < 27; i++)
//        {
//            System.out.println(chars[i] + " " + cryptList[i]);
//        }
        return nstr;

    }



    public static void main(String args[]) {
        
        File toencrypt = new File("toencrypt.txt");
        
    	try {
            BufferedWriter out1 = new BufferedWriter(new FileWriter("CleanNotEncrypted.txt"));
			BufferedWriter out2 = new BufferedWriter(new FileWriter("CleanEncrypted.txt"));
			BufferedReader input = new BufferedReader(new FileReader(toencrypt));
        
       	 	String line;
            String buffer = "";
			while ((line = input.readLine()) != null) {
                line = line.toUpperCase().trim();
           		line = line.replaceAll("[^A-Z ]", " ");
            	line = line.replaceAll("^\\s+", "");
            	line = line.replaceAll("\\s+", " ");
                buffer += line;
            }
            
            out1.write(buffer);
//          shifts text by 1. Need to make this user customizable
            out2.write(shift(buffer, 1));
    /*        
            int j = 0;
            for (int i = 0; i < buffer.length(); i++) {
                if ((i-j+1 >= 10 && buffer.charAt(i) == ' ') || i == buffer.length()-1) {
                        out1.write(buffer.substring(j,i) + "\n");
                        out2.write(shift(buffer.substring(j,i),1) + "\n");
                        j = i+1;
               // }
*/
            /*
                System.out.println(line.length());
        		line = line.toUpperCase().trim();
           		line = line.replaceAll("[^A-Z ]", " ");
            	line = line.replaceAll("^\\s+", "");
            	line = line.replaceAll("\\s+", " ");*/

				/*if (line.length() > 0) {
					out1.write(line);
					out2.write(shift(line,1));
				}*/
            
        	out1.close();
        	out2.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage() + "unable to open file" + "toencrypt.txt");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e.getMessage()); 
        }
        
        //System.out.println("HELLO ZA");
        //System.out.println(shift("HELLO ZA", 1));
    }
}
