package edu.cmu.cs.cs214.hw4.core;

/**
 * Generic implementation of an immutable object which store two pieces of immutable data.
 * 
 * @author Akhil Prakash
 *
 * @param <A> some type
 * @param <B> some other type. It is possible for A and B to be the same type.
 */
public class Pair<A, B>
{

	private final A first;
	private final B second;
	
	public Pair(A first, B second)
	{
		this.first = first;
		this.second = second;
	}

	public A getFirst()
	{
		return first;
	}

	public B getSecond()
	{
		return second;
	}
	
}
