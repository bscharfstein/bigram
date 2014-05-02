import java.io.*;
import java.util.regex.*;
import java.util.AbstractCollection;

public class ReadTrainingData {
	public static double[] unigrams = new double[27];
	public static double[][] bigrams = new double[27][27];

	//function for printing out the two dimensional bigram array to a given file
    public static void printArray(double[][] array, String txtfile) {
    	try {
    		FileWriter fstream = new FileWriter(txtfile);
    		BufferedWriter out = new BufferedWriter(fstream);
        	for (int i = 0; i<array.length; i++) {
           	 	for (int j = 0; j<array[0].length; j++) {
           	     	out.write((float)array[i][j] + " ");
           	 	}
           		out.write("\n");
        	}
        	out.close();
        } catch (Exception e) {
        	System.err.println("Error: " + e.getMessage());
        }
    }
    
    public static void printArray(double[] array, String txtfile) {
   		 try {
    		FileWriter fstream = new FileWriter(txtfile);
    		BufferedWriter out = new BufferedWriter(fstream);
        	for (int i = 0; i < array.length; i++) {
           		out.write((float)array[i] + " ");
        	}
        	out.close();
        } catch (Exception e) {
        	System.err.println("Error: " + e.getMessage());
        }
    }
    
    public static void readFiles(File directory) {
    	File [] files = directory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
		return name.endsWith(".txt");
            }
        });
                
        //initialize one dimensional unigram array of zeroes
        for (int j = 0, len = unigrams.length; j < len; j++) {
                unigrams[j] = 1;
        }
        
        //initialize two dimensional array of zeroes
        for (int i = 0, rowlen = bigrams.length; i < rowlen; i++) {
            for (int j = 0, collen = bigrams[0].length; j < collen; j++) {
                bigrams[i][j] = 1;
            }
        }
        
        for (File txtfile : files) {
            long nChar = 0;
            try {
                BufferedReader input = new BufferedReader(new FileReader(txtfile));
                String line;
                while ((line = input.readLine()) != null) {
                	line = Encrypt.clean(line);
                    
                    for (int i = 0; i < line.length()-1; i++) {
                    	int b = (line.charAt(i)==32)? 0 :line.charAt(i)-64;
                        bigrams[b][(line.charAt(i+1)==32)? 0 :line.charAt(i+1)-64]++;
                        unigrams[b]++;
                    }
                    if (line.length() > 0) {
                    	unigrams[(line.charAt(line.length()-1)==32) ? 0 :line.charAt(line.length()-1)-64]++;
                    	nChar += line.length();
                    }
                }
            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage() + "unable to open file" + txtfile);
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e.getMessage()); 
            }
        } 
    }
    
    public static double[] returnUnigrams() {
    	readFiles(new File("Text"));
		long nChar = 0;
		for (int i = 0, len = unigrams.length; i < len; i++) {nChar += unigrams[i];}
		for (int i = 0, len = unigrams.length; i < len; i++) {
			unigrams[i] = (float)unigrams[i]/nChar;
		}
    	return unigrams;
    }
    
    public static double[][] returnBigrams() {
    	//if readFiles has yet to be called, call it
    	if (bigrams[0][0] == 1) {
    		readFiles(new File("Text"));
    	}
    	//turn the counts into probabilities
    	for (int i = 0, rowlen = bigrams.length; i < rowlen; i++) {
			double rowsum = 0;
			for(int k = 0, collen = bigrams[0].length; k < collen; k++) {
				rowsum += bigrams[i][k];
			}
			for (int j = 0, collen = bigrams[0].length; j < collen; j++) {
				bigrams[i][j] = (float)bigrams[i][j]/rowsum;
			}
		}
    	return bigrams;
    }

    public static void main(String args[]) {
    	printArray(returnUnigrams(), "unigrams.txt");
    	printArray(returnBigrams(), "bigrams.txt");
    }
}
