package edu.cmu.cs.cs214.hw4.core;

/**
 * This class is used for storing an immutable character and two immutable integers. In the context of scrabble,
 * it is used when initializing the bag to hold the score and letter for each tile and the quantity of each tile 
 * to put in the bag.
 *  
 * I did not make this type generic because java was giving me errors about creating generic arrays.
 * 
 * @author Akhil Prakash
 */
public class Triple
{

	private final Character first;
	private final Integer second;
	private final Integer third;

	public Triple(Character a, Integer b, Integer c)
	{
		first = a;
		second = b;
		third = c;
	}

	public Character getFirst()
	{
		return first;
	}

	public Integer getSecond()
	{
		return second;
	}

	public Integer getThird()
	{
		return third;
	}
}
