
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.*;

public class Driver {
	public static void main(String args[]) {
		int[] freeThrowsMadeArray = {16,9,15,22,20,38,27,18,26,26,77,32,26,88,15,17,38,40,18,15,35,25,15,30,20,16,74};
		int[] freeThrowAttemptedArray = {20,12,20,25,23,44,32,25,35,30,88,41,30,107,20,21,55,50,20,20,41,35,20,34,25,23,94};
		int totalFTmade = 0;
		int totalFTattempted = 0;
		
		if(freeThrowsMadeArray.length==freeThrowAttemptedArray.length) {
			for(int i=0;i<freeThrowsMadeArray.length;i++) {
				totalFTmade += freeThrowsMadeArray[i];
				totalFTattempted += freeThrowAttemptedArray[i];
			}
			System.out.println(freeThrowCalculator(totalFTmade,totalFTattempted));
			System.out.println(highestFTpercentage(freeThrowsMadeArray,freeThrowAttemptedArray));
			System.out.println(lowestFTpercentage(freeThrowsMadeArray,freeThrowAttemptedArray));
			System.out.println(totalFTmade(freeThrowsMadeArray));
			int z = freeThrowsMissed(total(freeThrowAttemptedArray),total(freeThrowsMadeArray));
			System.out.println("Number of Free Throws Missed: " + z);
			System.out.println(totalFTattempted(freeThrowAttemptedArray));
			System.out.println(calculateSD(freeThrowsMadeArray,freeThrowAttemptedArray));
			System.out.println("=====================================================");
			System.out.println("=====================================================");
			System.out.println("=====================================================");
			displayFTsessions(freeThrowsMadeArray,freeThrowAttemptedArray);
			}
		else{
			System.out.println("Please check your arrays again. They are not the same length!");
		}
		
	//creating a file
    File file = new File("FTpercentagePerSession.csv");
    try {
      createFile(file, freeThrowsMadeArray, freeThrowAttemptedArray);
    } catch(IOException e) {
      e.printStackTrace();
      System.out.println("This did not work!");
    }
	}

  
	 /* Method closes a FileWriter. 
  Prints exception message if closing fails. */
  public static void closeFileWriter(FileWriter fileWriter) {
    try {
      if(fileWriter != null) { // Ensure fileWriter references a valid object
        // System.out.println("Closing file.");
        fileWriter.close(); // close() may throw IOException if fails
      }
    } catch (IOException closeExcpt) {
      System.out.println("Exception encountered while closing file.");
  }
}
  
  public static void createFile(File f, int[] FTMade, int[] FTAttempted) throws IOException {
    FileWriter fileWriter = null;
    ArrayList<Double> PercentagePerSession = new ArrayList<Double>();
    double proportion = 0.0;
    for(int i=0;i<FTMade.length;i++) {
      double FTproportion = (((double)FTMade[i])/((double)FTAttempted[i]))*100;
      PercentagePerSession.add(FTproportion);
    }
    
    // creating a file
    try {
      fileWriter = new FileWriter(f);
    } catch(IOException e) {
      System.out.println("Exception encountered when creating file");
      return;
      }/*
      for(int i=0;i<PercentagePerSession.size();i++) {
        try {
          fileWriter.write("Session " + i + " percentage: " + (printoutPerSession(PercentagePerSession.get(i)) + "%\n"));
        } catch(Exception e) {
          System.out.println("Exception encountered while writing to the file for the value N = " + PercentagePerSession.get(i));
          System.out.println(e.getMessage());
          return;
        }
      }
      double numOfFTMade = 0;
      double numOfFTAttempted = 0;
      double proportionOfTotalFT = 0;
      for(int i=0;i<FTMade.length;i++) {
        numOfFTMade += FTMade[i];
        numOfFTAttempted += FTAttempted[i];
        proportionOfTotalFT = numOfFTMade / numOfFTAttempted;
        try {
          fileWriter.write("After Session " + i + ", the Total Free Throw Percentage: " + printoutTotal(proportionOfTotalFT) + "%\n");
        } catch(Exception e) {
          System.out.println(e.getMessage());
        }
      }
      */
      String tableHeader = "FT made,FT attempted,Session FT percentage,Total FT percentage\n";
      try {
        fileWriter.write(tableHeader);
      } catch(Exception e) {
        System.out.println(e.getMessage());
      }
      double numOfFTMade2 = 0;
      double numOfFTAttempted2 = 0;
      double proportionOfTotalFT2 = 0;
      for(int j=0;j<FTMade.length;j++) {
        numOfFTMade2 += FTMade[j];
        numOfFTAttempted2 += FTAttempted[j];
        proportionOfTotalFT2 = numOfFTMade2 / numOfFTAttempted2;
        try {
          fileWriter.write(FTMade[j] + "," + FTAttempted[j] + "," + 
              (printoutPerSession(PercentagePerSession.get(j))) + "," + (printoutTotal(proportionOfTotalFT2)) + "\n");
        } catch(Exception e) {
          System.out.println(e.getMessage());
        }
      }
      // method that closes the file
      closeFileWriter(fileWriter);
    }
  
  
	
	
	
	public static String calculateSD(int[] a, int[] b) {
		double sum= 0.0, standardDeviation=0.0, average=0.0, number=0.0;
		int length = a.length;
		
		for(int i=0;i<a.length;i++) {
			double FTproportionOne = (((double)a[i])/((double)b[i]))*100;
			double next = FTproportionOne;
            sum = sum + next;
		}
		average = sum/length;
		
		for(int j=0;j<a.length;j++) {
			double FTproportionTwo = (((double)a[j])/((double)b[j]))*100;
			
			number += Math.pow(FTproportionTwo - average, 2);
		}
		standardDeviation = Math.sqrt(number/length);
		
		DecimalFormat df = new DecimalFormat("##.#");
		df.setRoundingMode(RoundingMode.CEILING);
		
		String standardDeviationString = df.format(standardDeviation);
		
		return("Standard Deviation of shooting: " + standardDeviationString + "%");
	}
	
	public static String freeThrowCalculator(int a, int b) {
		double FTproportion = (((double)a)/((double)b))*100;
		
		DecimalFormat df = new DecimalFormat("##.#");
		df.setRoundingMode(RoundingMode.CEILING);
		
		String FTpercentage = df.format(FTproportion);
		
		return ("Your Free Throw Percentage: " + FTpercentage + "%");
	}
	
	public static String highestFTpercentage(int[] a, int[] b) {
		double maxproportion = (((double)(a[0]))/((double)(b[0])))*100;
		for(int i=1;i<a.length;i++) {
			double proportion = (((double)(a[i]))/((double)(b[i])))*100;
			if(maxproportion < proportion) {
				maxproportion = proportion;
			}
		}
		DecimalFormat df = new DecimalFormat("##.#");
		df.setRoundingMode(RoundingMode.CEILING);
		String highestFTpercentage = df.format(maxproportion);
		return ("Your highest Free Throw Percentage in session: " + highestFTpercentage + "%");
	}
	
	public static String lowestFTpercentage(int[] a, int[] b) {
		double lowestproportion = (((double)(a[0]))/((double)(b[0])))*100;
		for(int i=1;i<a.length;i++) {
			double proportion = (((double)(a[i]))/((double)(b[i])))*100;
			if(lowestproportion > proportion) {
				lowestproportion = proportion;
			}
		}
		DecimalFormat df = new DecimalFormat("##.#");
		df.setRoundingMode(RoundingMode.CEILING);
		String lowestFTpercentage = df.format(lowestproportion);
		return ("Your lowest Free Throw Percentage in session: " + lowestFTpercentage + "%");
	}
	public static String totalFTmade(int[] a) {
		int total=0;
		for(int i=0;i<a.length;i++) {
			total += a[i];
		}
		return ("Total number of Free Throws Made: " + total);
	}
	
	public static int freeThrowsMissed(int a, int b) {
		return a - b;
	}
	
	public static String totalFTattempted(int[] a) {
		int total=0;
		for(int i=0;i<a.length;i++) {
			total += a[i];
		}
		return ("Total number of Free Throws Attempted: " + total);
	}
	
	public static int total(int[] a) {
		int total = 0;
		for(int x: a) {
			total += x;
		}
		return total;
	}
	
	public static void displayFTsessions(int[]a, int[] b) {
		for(int i=0;i<a.length;i++) {
			double FTproportion = (((double)a[i])/((double)b[i]))*100;
			
			DecimalFormat df = new DecimalFormat("##.#");
			df.setRoundingMode(RoundingMode.CEILING);
			
			String FTpercentage = df.format(FTproportion);
			
			System.out.println ("Session " + (i+1) + ": " + FTpercentage + "%");
		}
	}
	
	public static String printoutPerSession(double FTProportion) {
    
    DecimalFormat df = new DecimalFormat("##.#");
    df.setRoundingMode(RoundingMode.CEILING);
    
    String s = df.format(FTProportion);
    // String s = Double.toString(FTProportion);
    return s;
  }

	public static String printoutTotal(double p) {
	  DecimalFormat df = new DecimalFormat("##.#");
    df.setRoundingMode(RoundingMode.CEILING);
    double newp = p * 100;
    String s = df.format(newp);
    // String s = Double.toString(FTProportion);
    return s;
	}
}
