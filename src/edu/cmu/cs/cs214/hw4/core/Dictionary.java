package edu.cmu.cs.cs214.hw4.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Represents the dictionary which is used to check that the words made are actual words
 * 
 * I made everything in this class static because then I don't need to create an instance of the Dictionary and pass
 * it as a parameter everywhere. Also I don't need to store it in a variable.
 * 
 * @author Akhil Prakash
 */
public final class Dictionary
{
	
	private static final Set<String> dictionary = Collections.unmodifiableSet(readFromFile("assets/words.txt"));
	
	/**
	 * Reads all the words from the file and inserts them into a set
	 * 
	 * Precondition: the file should have one word per line
	 * 
	 * @param fileName the name of the file which is to be read
	 * 
	 * @return a lists of all the words in the file
	 */
	private static Set<String> readFromFile(String fileName)
	{
		Set<String> words = new HashSet<String> ();
		try 
		{
			Scanner scanner = new Scanner(new File(fileName));
			while (scanner.hasNext())
			{
				String word = scanner.nextLine();
				words.add(word);
			}
			scanner.close();
		}
		catch (FileNotFoundException e)
		{
			return words;
		}
		return words;
	}
	
	/**
	 * Precondition: words != null
	 * 
	 * @param dictionary a set of words to check if they are English words
	 * 
	 * @return true  if all the words are in the dictionary
	 *         false otherwise
	 */
	public static boolean areWords(Set<String> toBeChecked)
	{
		for (String word : toBeChecked)
		{
			if (! dictionary.contains(word))
			{
				return false;
			}
		}
		return true;
	}
}
