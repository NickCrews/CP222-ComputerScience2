/*
ListGen.java
Nick Crews
12/2/15

Used to make txt files populated with random integer randoms, for comparing sorting algorithms
*/

import java.util.*;
import java.io.*;

public class ListGen{
	
	public static boolean write(int numberEntries, int max, String fileName){

		// writes a series of random integers to a file

		// make our random numbers
		int[] random = ListGen.gen(numberEntries, max);

		// write to to a file
		try {
			BufferedWriter wr = new BufferedWriter(new FileWriter(fileName));
			for (int i : random){
				wr.write(Integer.toString(i));
				wr.newLine();
			}
			wr.flush();
			wr.close();
			return true;
		} 
		catch (Exception e) {
		  System.out.println(e.getMessage());
		  return false;
		} 

	}

	public static int[] read(String fileName){

		// reads a file (which should have an integer on each line)
		// and makes an integer array from the contents

		try {
			Scanner scanner = new Scanner(new File(fileName));
			ArrayList<Integer> random = new ArrayList<Integer>();
			while(scanner.hasNextInt()){
			   random.add(new Integer(scanner.nextInt()));
			}

			int[] ret = new int[random.size()];
		    for (int i=0; i < ret.length; i++)
		    {
		        ret[i] = random.get(i).intValue();
		    }
		    return ret;

		}
		catch (Exception e){
			System.out.println(e);
			return null;
		}
	}

	public static int[] gen(int numberEntries, int max){
		int[] out = new int[numberEntries];
		Random r = Random.getInstance();
		for (int i = 0; i < numberEntries; i++){
			out[i] = r.nextInt(max);
		}
		return out;
	}

	
}



