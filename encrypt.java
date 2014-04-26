import java.io.*;
import java.util.regex.*;
import java.util.AbstractCollection;

public class encrypt {
    public static String shift(String str, int offset) {
    	String nstr = "";
    	int num = 0;
    	for (int i = 0; i < str.length(); i++) {
    		num = (str.charAt(i)==32) ? 64 : str.charAt(i);
			num = (num - 64 + offset)%27 + 64;
			nstr += (num == 64) ? (char)32 : (char)num;
    	}
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
