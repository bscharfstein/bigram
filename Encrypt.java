import java.io.*;
import java.util.regex.*;
import java.util.AbstractCollection;
import java.util.Random;

public class Encrypt {

    /*
     * Applies a Caesar shift cipher
     */ 
    public static String shift(String str, int offset) {
        str = clean(str);
        String nstr = "";
        int num = 0;
        for (int i = 0; i < str.length(); i++) {
            num = (str.charAt(i)==32) ? 64 : str.charAt(i);
            num = (num - 'A' + 1 + offset) % 27 + 'A' - 1;
            nstr += (num == 64) ? (char)32 : (char)num;
        }
        return nstr;
    }

    /*
     * Generates an atbash cipher, then applies a shift
     */ 
    public static String atbash(String str, int offset) {
        str = clean(str);
        String nstr = "";
        int num = 0;
        for (int i = 0; i < str.length(); i++) {
            num = (str.charAt(i)==32) ? 64 : str.charAt(i);
            num = (-1 * (num - 'A' + 1) + 27 + offset ) % 27 + 'A' - 1;
            nstr += (num == 64) ? (char)32 : (char)num;
        }
        return nstr;
    }


    /*
     * Code inspired by http://www.java-forums.org/new-java/17317-very-simple-encryption.html
     * Generates a random 1-to-1 monoalphabetic substitution cipher
     */ 
    public static String rando(String str) {
        str = clean(str);
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

        /**for checking**

        System.out.println(nstr);

        for(int i = 0; i < 27; i++)
        {
            System.out.println(chars[i] + " " + cryptList[i]);
        }
        */
        return nstr;

        

    }

    public static String clean(String str) {
        String buffer = "";
        str = str.toUpperCase().trim();
        
        // Keep " ' " as part of the word as opposed to putting a space
        str = str.replaceAll("[']|[\u2019]", "");
        str = str.replaceAll("[^A-Z ]", " ");
        str = str.replaceAll("^\\s+", "");
        str = str.replaceAll("\\s+", " ");
        buffer += str;
        return buffer;
    }




    /*
     * The below Main function technically never runs within the GUI,
     * as the GUI encryption only calls upon the above functions individually.
     * However, we leave it uncommented, as it is sometimes used for quick command-line
     * checking of the encryption.
     */

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
            out2.write(shift(buffer, 1));
            
        	out1.close();
        	out2.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage() + "unable to open file" + "toencrypt.txt");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e.getMessage()); 
        }
        
    }
}
