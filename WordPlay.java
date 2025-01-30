
/*
 * Student Name: Christian Ivanov
 *
 * WordPlay
 * 
 * This client examines the performance of the following symbol table (ST) implementations:
 * Sequential search, binary search, BST, and red black BST from the algs4 library
 * 
 * One ST implementation is chosen via the "activeST" variable.
 * 
 * This client 
 * -- reads a text file 
 * -- creates a symbol table (ST) with (word, word frequency) as (key, value), called "WF" ST.
 * -- reports the time it took to build this ST 
 * -- reports statistics concerning this WF ST
 * -- reports the number of unique words in the input file that are greater than "minlen"
 * -- reports the minimum and maximum words based on lexicographical ordering and the associated frequencies
 * 
 * Next this client creates a second ST with (key, value) = (frequency, word) called "FW"
 * -- using this FW ST, the max and min frequencies with associated words are reported
 * 
 * Reporting is made with prints to the console. 
 * 
 * (The program actually processes strings; it does not extract words necessarily; 
 * Example "file." is a "word".)
 *
 *  
 * 
 */
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.BST;
import edu.princeton.cs.algs4.BinarySearchST;
import edu.princeton.cs.algs4.SequentialSearchST;
import edu.princeton.cs.algs4.Stopwatch;

public class WordPlay {

	// Do not instantiate.
	private WordPlay() {
	}

	public static void main(String[] args) {

		StdOut.println("Hello WordPlay!");

		/*
		 * ************************************************************************
		 * Choose one input file by removing the comment characters
		 * *************************************************************************
		 */
		// In inputFile = new In("dickens.txt");
		// In inputFile = new In("quickFox.txt");
		// In inputFile = new In("mobydick.txt");
		In inputFile = new In("aesop.txt");

		// create a symbol table with key as string and value as integer

		// #1
		SequentialSearchST<String, Integer> WFssST = new SequentialSearchST<String, Integer>();
		// #2
		BinarySearchST<String, Integer> WFbsST = new BinarySearchST<String, Integer>();
		// #3
		BST<String, Integer> WFbstST = new BST<String, Integer>();
		// #4
		RedBlackBST<String, Integer> WFrbST = new RedBlackBST<String, Integer>();

		// *********************************************
		// Choose one of the ST implementations to use
		// Numbers indicated in ST declaration
		// *********************************************
		int activeST = 4;

		StdOut.println("activeST = " + activeST);
		if (activeST < 1 || activeST > 4) {
			StdOut.println("ERROR: activeST must be set to 1 - 4.  Setting to 1 ");
			activeST = 1;
		}

		switch (activeST) {
			case 1:
				StdOut.println("ST Implementation: Sequential Search");
				break;
			case 2:
				StdOut.println("ST Implementation: Binary Search");
				break;
			case 3:
				StdOut.println("ST Implementation: BST");
				break;
			case 4:
				StdOut.println("ST Implementation: Red Black BST");
				break;
		}

		// set the min length of words to enter into the symbol table
		int minlen = 5;
		StdOut.println("Minimum length of word   minlen = " + minlen);

		// number of words read from file
		// int your variable here
		int totalWords = 0;
		// number of words >= minlen
		// int your variable here
		int wordsMinLen = 0;

		StdOut.println("Create a ST of (key,value) = (word, frequency) where length >=  " + minlen);

		StdOut.println("Set timer to measure time to build ST");

		// start timer to measure time to build (word, freq) ST
		Stopwatch timer = new Stopwatch();

		// Build the WF ST (You will need a switch statement )
		while (!inputFile.isEmpty()) { // Check if the input stream is not empty
			String text = inputFile.readString(); // Read the next string from the input file
			totalWords++;

			if (text.length() >= minlen) {
				wordsMinLen++;
				switch (activeST) {
					case 1:
						updateFrequency(WFssST, text);
						break;
					case 2:
						updateFrequency(WFbsST, text);
						break;
					case 3:
						updateFrequency(WFbstST, text);
						break;
					case 4:
						updateFrequency(WFrbST, text);
						break;
				}
			}
		}
		// stop timer
		// number of unique words in the ST
		int uniqueWords = (activeST == 1) ? WFssST.size()
				: (activeST == 2) ? WFbsST.size() : (activeST == 3) ? WFbstST.size() : WFrbST.size();

		// Calculate percentage of words with length >= minlen
		double percentageMinLen = (totalWords > 0) ? (double) wordsMinLen / totalWords * 100 : 0;

		// Calculate percentage of unique words
		double percentageUniqueWords = (totalWords > 0) ? (double) uniqueWords / totalWords * 100 : 0;

		// report information
		double buildTime = timer.elapsedTime();

		// Report on (word, frequency) ST build
		StdOut.println("Build ST Time = " + buildTime);

		// create variables for this section and then uncomment

		StdOut.println("Number of words read from file:  " + totalWords);
		StdOut.println("Number of words in file >= " + minlen + " = " + wordsMinLen);
		StdOut.printf("Percentage of words >= min length:  %3.1f", percentageMinLen);
		StdOut.println("Number of unique words (#keys in ST):  " + uniqueWords);
		StdOut.printf("Percentage of unique words:  %3.1f", percentageUniqueWords);
		StdOut.println("Build ST Time = " + buildTime);
		StdOut.println("Create (Frequency, Word) ST ");
		RedBlackBST<Integer, String> FWrbST = new RedBlackBST<>(); // Ensure this is defined
		// Print up to the first 10 items in the ST: word and frequency
		// Add some text to the output to communicate what is being printed
		// Use the for each statement
		StdOut.println("First 10 items in the WF ST:");
		switch (activeST) {
			case 1:
				printFirstN(WFssST, 10);
				break;
			case 2:
				printFirstN(WFbsST, 10);
				break;
			case 3:
				printFirstN(WFbstST, 10);
				break;
			case 4:
				printFirstN(WFrbST, 10);
				break;
		}
		// Ensure that you print the first 10 items
		StdOut.println("First 10 items in the FW ST:");

		/*
		 * Print min and max word and its associated frequency from the WF ST
		 * 
		 * Only do this if using a class with ordered operations
		 */
		if (activeST == 1) {
			// SequentialSearchST does not have min/max methods, so skip these
			StdOut.println("Sequential Search does not support min/max operations.");
		} else {
			String minWord = (activeST == 2) ? WFbsST.min() : (activeST == 3) ? WFbstST.min() : WFrbST.min();
			String maxWord = (activeST == 2) ? WFbsST.max() : (activeST == 3) ? WFbstST.max() : WFrbST.max();

		}

		/* ****************************** */
		/*
		 * PART 2: Create a (frequency, word) ST
		 * using the words and frequencies in the WF ST
		 * Call it "FW" ST
		 * Just as above, there will be 4 ST structures
		 */
		/****************************** */

		// Build the FW ST
		switch (activeST) {
			case 1:
				buildFWST(WFssST, FWrbST);
				break;
			case 2:
				buildFWST(WFbsST, FWrbST);
				break;
			case 3:
				buildFWST(WFbstST, FWrbST);
				break;
			case 4:
				buildFWST(WFrbST, FWrbST);
				break;
		}

		// Print up to the first 10 items in the ST: frequency and word

		// Add some text to the output to communicate what is being printed
		// Use the for each statement

		// Print min and max frequencies with associated word
		// Only do for ST classes with ordered operations
		if (FWrbST.size() > 0) { // Only for ordered operations
			Integer minFrequency = FWrbST.min();
			Integer maxFrequency = FWrbST.max();
			StdOut.println("Min frequency: " + minFrequency + " -> " + FWrbST.get(minFrequency));
			StdOut.println("Max frequency: " + maxFrequency + " -> " + FWrbST.get(maxFrequency));
		}

	} // end main
		// Additional methods needed for frequency handling

	private static void buildFWST(SequentialSearchST<String, Integer> WF, RedBlackBST<Integer, String> FW) {
		for (String key : WF.keys()) {
			int frequency = WF.get(key);
			FW.put(frequency, key);
		}
	}

	private static void buildFWST(BinarySearchST<String, Integer> WF, RedBlackBST<Integer, String> FW) {
		for (String key : WF.keys()) {
			int frequency = WF.get(key);
			FW.put(frequency, key);
		}
	}

	private static void buildFWST(BST<String, Integer> WF, RedBlackBST<Integer, String> FW) {
		for (String key : WF.keys()) {
			int frequency = WF.get(key);
			FW.put(frequency, key);
		}
	}

	private static void buildFWST(RedBlackBST<String, Integer> WF, RedBlackBST<Integer, String> FW) {
		for (String key : WF.keys()) {
			int frequency = WF.get(key);
			FW.put(frequency, key);
		}
	}

	private static void printFirstN(SequentialSearchST<String, Integer> st, int n) {
		int count = 0;
		for (String key : st.keys()) {
			if (count < n) {
				StdOut.println(key + ": " + st.get(key));
				count++;
			} else {
				break;
			}
		}
	}

	private static void printFirstN(BinarySearchST<String, Integer> st, int n) {
		int count = 0;
		for (String key : st.keys()) {
			if (count < n) {
				StdOut.println(key + ": " + st.get(key));
				count++;
			} else {
				break;
			}
		}
	}

	private static void printFirstN(BST<String, Integer> st, int n) {
		int count = 0;
		for (String key : st.keys()) {
			if (count < n) {
				StdOut.println(key + ": " + st.get(key));
				count++;
			} else {
				break;
			}
		}
	}

	private static void printFirstN(RedBlackBST<String, Integer> st, int n) {
		int count = 0;
		for (String key : st.keys()) {
			if (count < n) {
				StdOut.println(key + ": " + st.get(key));
				count++;
			} else {
				break;
			}
		}
	}

	private static void updateFrequency(SequentialSearchST<String, Integer> st, String word) {
		if (!st.contains(word)) {
			st.put(word, 1);
		} else {
			st.put(word, st.get(word) + 1);
		}
	}

	private static void updateFrequency(BinarySearchST<String, Integer> st, String word) {
		if (!st.contains(word)) {
			st.put(word, 1);
		} else {
			st.put(word, st.get(word) + 1);
		}
	}

	private static void updateFrequency(BST<String, Integer> st, String word) {
		if (!st.contains(word)) {
			st.put(word, 1);
		} else {
			st.put(word, st.get(word) + 1);
		}
	}

	private static void updateFrequency(RedBlackBST<String, Integer> st, String word) {
		if (!st.contains(word)) {
			st.put(word, 1);
		} else {
			st.put(word, st.get(word) + 1);
		}
	}

} // end class
