import java.io.*;
import java.util.regex.*;
import java.util.AbstractCollection;

public class b1 {
    public static void printArray(int[][] array, String txtfile) {
    	try {
    		FileWriter fstream = new FileWriter(txtfile);
    		BufferedWriter out = new BufferedWriter(fstream);
        	for (int i = 0; i<array.length; i++) {
                double rowsum = 0;
                for(int k = 0; k < array[0].length; k++) {
                    rowsum += array[i][k];
                }
           	 	for (int j = 0; j<array[0].length; j++) {
           	     	out.write((float)array[i][j]/rowsum + " ");
           	 	}
           		out.write("\n");
        	}
        	out.close();
        }catch (Exception e) {
        	System.err.println("Error: " + e.getMessage());
        }
    }
    public static void printArray(int[] array, String txtfile) {
   		 try {
    		FileWriter fstream = new FileWriter(txtfile);
    		BufferedWriter out = new BufferedWriter(fstream);
    		long nChar = 0;
        	for (int i = 0; i < array.length; i++) {nChar += array[i];}
        	for (int i = 0; i < array.length; i++) {
           		out.write((float)array[i]/nChar + " ");
        	}
        	out.close();
        }catch (Exception e) {
        	System.err.println("Error: " + e.getMessage());
        }
    }
    
    public static void main(String args[]) {
        
        File dir = new File("Text");
        //File dir = new File(".");
        File [] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
		return name.endsWith(".txt");
            }
        });

        int[][] bigrams = new int[27][27];
        for (int i = 0; i < bigrams.length; i++) {
            for (int j = 0; j < bigrams[0].length; j++) {
                bigrams[i][j] = 1;
            }
        }
        
        //create one dimensional unigram array of zeroes
        int[] unigrams = new int[27];
        for (int j = 0; j < unigrams.length; j++) {
                unigrams[j] = 1;
        }
        
        //go through all the files
        for (File txtfile : files) {
            long nChar = 0;
            try {
                BufferedReader input = new BufferedReader(new FileReader(txtfile));
                String line;
                while ((line = input.readLine()) != null) {
                        line = line.toUpperCase().trim();
                        //replace all non-letters with spaces
                        line = line.replaceAll("[^A-Z ]", " ");
                        //replace all spaces at the beginning of a line with nothing
                        line = line.replaceAll("^\\s+", "");
                        //replace all strings of spaces with one space
                        line = line.replaceAll("\\s+", " ");
                    
                    for (int i = 0; i < line.length()-1; i++) {
                    	int b = (line.charAt(i)==32)? 0 :line.charAt(i)-64;
                        bigrams[b][(line.charAt(i+1)==32)? 0 :line.charAt(i+1)-64]++;
                        unigrams[b]++;
                    }
                    if (line.length() > 0) {
                    	unigrams[(line.charAt(line.length()-1)==32) ? 0 :line.charAt(line.length()-1)-64]++;
                    	nChar += line.length();
                    }
                    
                    
      	            //if (nChar<12000 && line.length()>0) {System.out.println(line);}
                }
            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage() + "unable to open file" + txtfile);
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e.getMessage()); 
            }
            
            printArray(bigrams, "bigrams.txt");
            printArray(unigrams, "unigrams.txt");
//            System.out.println("nChar="+nChar);

        }
    }
}
