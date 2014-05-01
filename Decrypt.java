import java.io.*;
import java.util.regex.*;
import java.util.AbstractCollection;

public class Decrypt {
	//File from which we read in the unigram probabilities
	public static String UNIGRAM_FILE = "unigrams.txt";
	
	//File from which we read in the bigram probabilities
	public static String BIGRAM_FILE = "bigrams.txt";
	
	//Array for storing bigram probabilities
    static double[][] bProbs = new double[27][27];
    
    //Array for unigram probabilities
    static double[] uProbs = new double[27];
    
    //Array for storing the emission probabilities
    static double[][] emit = new double[27][27];
    
    //Array for storing the counts from which we re-estimate the emission probabilities
    static double[][] counts = new double[27][27];

    //Array for storing the final decryption key
    static int[] decryptArr = new int[27]; 
    
	//Max number of passes we want to make (gets updated to the number of passes actually run)
	public static int num_passes = 50;
    
    /* function to translate a letter from its value in ASCII to   
     * its corresponding value from 1 to 26, with spaces going to  
     * 0 */
    public static char convert(char c) { 
    	return (char)((c == 32) ? 0 : (c - 'A') + 1); 
    }
    
    /*converts a string to a string of converted chars*/
	public static String convert(String str) {
    	String nstr = "";
    	for (int i = 0, len = str.length(); i < len; i++) { nstr += convert(str.charAt(i)); }
    	return nstr;
    }
    
    /*For each character in str deconvert plugs in the encrypted char into
     * the array containing the decryption key, then converts the result
     * back into letter form  */
    public static String deconvert(String str) {
    	String nstr = "";
    	for (int i = 0; i < str.length(); i++) {
    		nstr += (char)deconvert((char)decryptArr[str.charAt(i)]);
    	}
    	return nstr;
    }
    
    /*takes a value 0 through 27 back to the corresponding letter value */
    public static char deconvert(char c) {
    	return (char)((c==0) ? 32 : c + 'A' - 1);
    }
    
    /*Function that takes an encrypted line and returns the decrypted output*/
    public static String[] decrypt(String line, boolean inCL) {
    	
    	/*Read in the unigram and bigram probabilities from the files*/
        readUnigramsAndBigrams();
    	
    	/*initialize the emission probabilities*/
    	initEmits(line);
    	
    	//String array for storing partially decrypted strings (for the GUI)
    	//If it ends early we will indicate this with the empty string 
    	String[] partwayDecryptions = new String[num_passes];
    	
    	
    	/*** Run the algorithm to build the decrypt array ***/
		for (int k = 0; k < num_passes; k++) {
			for (int i = 0; i < 27; i++) {
				decryptArr[i] = -1;
				for(int j = 0; j<27; j++) {
					counts[i][j] = 0;
				}
			}
			double totalprob = 0;
			for (int start = 0, len = line.length(); start < len; start += 100) {                    	
				totalprob += fbpass(convert(line.substring(start, Math.min(len, start+100))));
			}	
					
			double totalcount;
			for (int i = 0; i < 27; i++) {
				totalcount = 0;
				for (int j = 0; j < 27; j++) {
					totalcount += counts[i][j];
				}
				
				for (int j = 0; j < 27; j++) {
					emit[i][j] = counts[i][j] / totalcount; 
				}
			}
				
			//threshold
			double thresh = 1.0 - (.8*k)/(double)num_passes;
			
			//boolean to check if the cipher has been found already
			boolean allones = true;
			
			//boolean to check if none of the cipher has been found
			boolean noones = true;
			
			for (int i = 0; i < 27; i++) {
				double maxp = -1.0e64;
				for(int j = 0; j < 27; j++) {
					double x = emit[i][j];
					if (x>maxp) {
						maxp=x;
					}
					if(x > thresh) {
						noones = false;
						for (int m = 0; m < 27; m++) {
							emit[i][m] = 0;
							emit[m][j] = 0;
						}
						emit[i][j]=1;
						decryptArr[j] = i;
					}
				}
				if (maxp < thresh) {
					allones = false;
				}
				//System.out.println(i + ": " + partwayDecryptedArr[i]);
			}
			
			if (noones && !inCL) {
				partwayDecryptions[k] = " ";
			}
			else {
				partwayDecryptions[k] = decryptArrayToString(line);
			}
			//System.exit(0);
			
			//if the cipher has been found, exit
			if (allones && k != num_passes - 1) {
				partwayDecryptions[k+1] = "";
				num_passes = k+1;
				break;
			}		
			
			//normalize the emission probabilities (should sum to 1 across the column)
			for (int i = 0; i < 27; i++) {
				double x = 0;
				for (int j = 0; j < 27; j++) { x += emit[i][j]; } 
				for (int j = 0; j < 27; j++) { emit[i][j] /= x; }
			}
			
			/* Print the following lines if you're running the program on the command line  *
			 * and want to see the emission probabilities update 							*/
			if (inCL) {	
				System.out.println("Pass #" + k);
				printEProbs(true);
				System.out.println(partwayDecryptions[k].substring(0, 1000));
				System.out.println();
			}
		}
		return partwayDecryptions;
    }
    
    /* Function that takes the decrypt Array (decryptArr) in whatever state       *
     * it's in and uses it to decrypt the line. If the value in the array has not *
     * yet been found (the emission probability is not sufficiently close to 1),  *
     * it will print the encrypted character.This is useful for showing the       *
     * process of decryption 													  */
    public static String decryptArrayToString(String line) {
    	//System.out.println(line);
    	String decryptedLine = "";
		for(int i = 0; i < line.length(); i++) {
			int c = convert(line.charAt(i));
			//decryptedLine += deconvert((char)decryptArr[c]);
			if (decryptArr[c] != -1) {
            	decryptedLine += deconvert((char)decryptArr[c]);
            } else {
            	decryptedLine += deconvert((char)c);
            }
        }
		//System.out.println("DecryptedLine: " + decryptedLine);
		return decryptedLine;
    }

    public static double fbpass(String line) {
    	//forward pass
    	double[][] ftr = new double[27][line.length()];
    	
    	for (int i = 0; i < 27; i++) {
    		//System.out.println("i: " + (int) line.charAt(0));
    		ftr[i][0] = uProbs[i]*emit[i][line.charAt(0)];
    	}
    	
        for (int i = 1, len = line.length(); i < len; i++) {
    		for (int j = 0; j < 27; j++) {
                double tosum = 0;
    			for (int k = 0; k < 27; k++) { 
    				tosum += ftr[k][i-1]*bProbs[k][j]; 
    			}
                ftr[j][i] = tosum*emit[j][line.charAt(i)];
            }
    	}
        
        double fpass = 0;
    	for (int i = 0; i < 27; i++) { 
    		fpass += ftr[i][line.length()-1]; 
    	}
		
    	//backward pass
    	double[][] btr = new double[27][line.length()];
    	
    	for (int i = 0; i < 27; i++) { 
    		btr[i][line.length()-1] = 1; 
    	}
    	
    	for (int i = line.length()-2; i >= 0; i--) {
    		for (int j = 0; j < 27; j++) {
    			double tosum = 0;
    			for (int k = 0; k < 27; k++) { 
    				tosum += btr[k][i+1]*emit[k][line.charAt(i+1)]*bProbs[j][k];
    			}
    			btr[j][i] = tosum;
    		}
    	}

    	for (int i = 0; i < 27; i++) {
    		for (int j = 0; j < line.length(); j++) {
    			counts[i][line.charAt(j)] += ftr[i][j]*btr[i][j]/fpass;
    		}
    	}
    	
    	return Math.log(fpass);
    } 
    
    public static void initEmits(String line) {
    	BufferedReader reader;
    	double[] dirtyProbs = new double[27];
    	long nchar = 0;
    	
		try {
        	//Initialize all probabilities from the encrypted string to 0
        	for (int j = 0; j < dirtyProbs.length; j++) {
           	     dirtyProbs[j] = 0;
        	} 
		
			for (int i = 0, len = line.length(); i < len; i++) {
				dirtyProbs[(line.charAt(i)==32) ? 0 : line.charAt(i)-64]++;
				nchar++;
			}
		} catch (Exception e) {
        	System.err.println("Error: " + e.getMessage());
        }		
        
		//normalize from counts into probabilities
		for (int i = 0; i < 27; i++) {
			dirtyProbs[i] /= nchar;
		}
		
		double [][] diff = new double[27][27];
		double avgSquareSum = 0;
		double var = 0;
		for (int i = 0; i < 27; i++) {
			for (int j = 0; j < 27; j++) {
				diff[i][j] = Math.abs(dirtyProbs[i] - uProbs[j]) + Math.abs(dirtyProbs[j] - uProbs[i]);
				avgSquareSum += (diff[i][j]*diff[i][j]);
			}
		}
		avgSquareSum /= 27*27;
		var = avgSquareSum;
		
		//set probabilities
		for (int i = 0; i < 27; i++) {
			double x = 0;
			for (int j = 0; j < 27; j++) {
				double y = diff[i][j];
				x += (emit[i][j] = Math.exp(-y*y/(2*var)));
			}
			
			for (int j = 0; j < 27; j++) {
				emit[i][j] /= x;
			}
		}
    }
    
    /* The following three functions are essentially debugging print functions. *
     * printEProbs prints the emission probabilities, which we can use to 		*
     * see whether they are being updated correctly. printDecryptArr functions	*
     * similarly except with the actual decryption array. printTProbs is for	*
     * debugging more within the forward backward pass, and prints transition	*
     * probabilities.															*/
    public static void printEProbs (boolean onlyPrintMax) {
        String letters = new String("#ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		for (int i = 0; i < 27; i++) {
	    	double maxp = -1.0e64;
	    	int    maxj = 0;
	    	for (int j = 0; j < 27; j++) { 
				double x = emit[i][j];
				if (x>maxp) {maxj=j; maxp=x;}
	    	}
	    	if (!onlyPrintMax) {
	    		System.out.println(letters.charAt(i)+"->"+letters.charAt(i)+" = "+emit[i][i]);
	    	}
	    	System.out.println(letters.charAt(i)+"->"+letters.charAt(maxj)+" = "+emit[i][maxj]);
		}
    }
    
    public static void printDecryptArr () {
        String letters = new String("#ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    	for(int i = 0; i < 27; i++) {
    		System.out.println(letters.charAt(i)+"-->"+letters.charAt(decryptArr[i]));
    	}
    }

    public static void printTProbs () {
        String letters = new String("#ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        System.out.println("Unigram Probs:");
        double sum = 0;
		for(int i=0; i<27; i++) {
			System.out.println(letters.substring(i,i+1)+" "+uProbs[i]);
			sum += uProbs[i];
		}
        System.out.println("Sum = "+sum);
        System.out.println("Bigram Probs:");
		for(int i=0; i<27; i++) {
			sum = 0;
			for (int j=0; j<27; j++) {
				System.out.println(letters.substring(i,i+1)+letters.substring(j,j+1)+" "+bProbs[i][j]);
				sum += bProbs[i][j];
			}
			System.out.println("Sum = "+sum);
		}
    }

    public static void readUnigramsAndBigrams() {
    	File unigram = new File(UNIGRAM_FILE);
    	File bigram = new File(BIGRAM_FILE);
    	try {
    		BufferedReader uinput = new BufferedReader(new FileReader(unigram));
    		BufferedReader binput = new BufferedReader(new FileReader(bigram));
    		int count = 0;
    		String line = "";
    		while ((line = binput.readLine()) != null) {
    			String[] narray = line.split(" ");
    			for (int i = 0; i < narray.length; i++) {
    				bProbs[count][i] = Float.parseFloat(narray[i]);
    			}
    			count++;
    		}
   	 		while ((line = uinput.readLine()) != null) {
    			String[] narray = line.split(" ");
    			for (int i = 0; i < narray.length; i++) {
    				uProbs[i] = Float.parseFloat(narray[i]);
    			}
    		}
    		uinput.close();
   		 	binput.close();
   		} catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage() + "unable to open file" + "unigrams.txt or bigrams.txt");
            System.exit(0);
    	} catch (IOException e) {
            System.out.println(e.getMessage()); 
        }
    }
    
    public static void main(String args[]) {
    	String[] partialDecryptions;
    	String ENCRYPTED_FILE = "";
    	String DECRYPT_TO = "";
    	if (args.length != 2) {
    		System.out.println("Please specify an input and an output file");
    		System.exit(0);
    	}
    	else {
			if (args[0].split("\\.").length != 2 | !args[0].split("\\.")[1].equals("txt")) {
				System.out.println("Please run again with a .txt file");
				System.exit(0);
			}
			else {
				ENCRYPTED_FILE = args[0];
			}
			
			if (args[1].split("\\.").length != 2 | !args[1].split("\\.")[1].equals("txt")) {
    			System.out.println("Please run again with a .txt file");
    			System.exit(0);
    		}
    		else {
				DECRYPT_TO = args[1];
			}
    	}
    	
		File encrypted = new File(ENCRYPTED_FILE);
		String decryptedLine = "";
		try {
			BufferedReader enc = new BufferedReader(new FileReader(encrypted));
			String line = "";
			
			line = enc.readLine();
			decryptedLine = decrypt(line, true)[num_passes];
			
			enc.close();
		} catch (FileNotFoundException ex) {
        	System.out.println(ex.getMessage() + "unable to open file " + ENCRYPTED_FILE);
        	System.exit(0);
    	} catch (IOException e) {
            System.out.println(e.getMessage()); 
        }  
        
        try {
    		FileWriter fstream = new FileWriter(new File(DECRYPT_TO));
    		BufferedWriter out = new BufferedWriter(fstream);

    		out.write(decryptedLine);
			out.close();
		} catch (Exception e) {
        	System.err.println("Error: " + e.getMessage());
        }
    } 
}
