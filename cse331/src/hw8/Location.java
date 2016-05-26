package hw8;

import javafx.util.Pair;

/**
 * <b>Location</b> represents a <b>immutable</b> location that has a short
 * name, long name, and x and y coordinates.
 * <p>
 * 
 * @author pinyiw
 */
public class Location {
	
	/** The short name this Location stores. */
	private final String shortName;
	/** The long name this Location stores. */
	private final String longName;
	/** The x coordinate this Location stores. */
	private final String x;
	/** The y coordinate this Location stores. */
	private final String y;
	
	// Abstraction Function:
	// Location, l, is a location that stores a short name, long name, x, and y.
	// An example of Location will be (A, B, x1, y1), where A is shortName, B is
	// longName, x1 is x, and y1 is y.
	//
	// Representation invariant for every Location l:
	//	shortName != null &&
	//	longName != null &&
	//	x != null &&
	//	y != null.
	
	/**
	 * @param shortName the short name of the location to be stored.
	 * @param longName the long name of the location to be stored.
	 * @param x the x coordinate of the location to be stored.
	 * @param y the y coordinate of the location to be stored.
	 * @effect Constructs a new Location with the given shortName, longName, x
	 * 		   and y.
	 * @throws IllegalArgumentException if shortName == null || longName == null
	 * 		   || x == null || y == null.
	 */
	public Location(String shortName, String longName, String x, String y) {
		if (shortName == null || longName == null || x == null || y == null) {
			throw new IllegalArgumentException();
		}
		this.shortName = shortName;
		this.longName = longName;
		this.x = x;
		this.y = y;
		checkRep();
	}
	
	/**
	 * @requires key and value in coordinates != null.
	 * @param coordinates the Pair that stores the x and y coordinates that
	 * 		  will be stored.
	 * @effect Constructs a new Location with the given coordinates, and empty
	 * 		   shortName and longName.
	 * @throws IllegalArgumentException if coordinates == null
	 */
	public Location(Pair<String, String> coordinates) {
		if (coordinates == null) {
			throw new IllegalArgumentException();
		}
		this.x = coordinates.getKey();
		this.y = coordinates.getValue();
		this.shortName = "";
		this.longName = "";
		checkRep();
	}
	
	/**
	 * Returns the short name of this Location.
	 * 
	 * @return the short name of this Location.
	 */
	public String shortName() {
		return shortName;
	}
	
	/**
	 * Returns the long name of this Location.
	 * 
	 * @return the long name of this Location.
	 */
	public String longName() {
		return longName;
	}
	
	/**
	 * Returns the x coordinate of this Location.
	 * 
	 * @return x of this Location.
	 */
	public String x() {
		return x;
	}
	
	/**
	 * Returns the y coordinate of this Location.
	 * 
	 * @return y of this Location.
	 */
	public String y() {
		return y;
	}
	
	/**
	 * Returns whether this Location has name or not.
	 * 
	 * @return false if this Location has no shortName or longName,
	 * 		   otherwise, true.
	 */
	public boolean hasName() {
		return shortName.length() != 0 && longName.length() != 0;
	}
	
	/**
	 * Returns whether this Location is equals to other.
	 * 
	 * @param other the object to be compare.
	 * @return true if other is of type Location and its x and y coordinates
	 * 		   equals to that of this Location,
	 * 		   otherwise, false.
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Location)) {
			return false;
		} else {
			Location o = (Location) other;
			return this.x.equals(o.x) && this.y.equals(o.y);
		}
	}
	
	/**
	 * Returns the hash code of this Location.
	 * 
	 * @return the hash code of this Location.
	 */
	@Override
	public int hashCode() {
		return x.hashCode() * 7 + y.hashCode();
	}
	
	/**
	 * Checks that the representation invariant holds.
	 */
	public void checkRep() {
		assert (shortName != null) : "short name is null";
		assert (longName != null) : "long name is null";
		assert (x != null) : "x is null";
		assert (y != null) : "y is null";
	}
}
