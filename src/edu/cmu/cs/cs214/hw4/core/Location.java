package edu.cmu.cs.cs214.hw4.core;

/**
 * Represents a location on the board. Simple basic class with just getters.
 * 
 * @author Akhil Prakash
 */
public class Location
{

	/**
	 * Invariant: the location is always a valid location on the board
	 */
	private final int X;
	private final int Y;
	
	public Location(int x, int y)
	{
		X = x;
		Y = y;
	}
	
	public int getX()
	{
		return X;
	}
	
	public int getY()
	{
		return Y;
	}
	
	@Override
	/**
	 * used for debugging
	 */
	public String toString()
	{
		return "Location : (" + X + "," + Y + ")";
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (! (obj instanceof Location))
		{
			return false;
		}
		Location loc = (Location) obj;
		return loc.X == X && loc.Y == Y;
	}
	
	@Override
	public int hashCode()
	{
		return ((Integer) X).hashCode() + ((Integer) Y).hashCode();
	}
}
