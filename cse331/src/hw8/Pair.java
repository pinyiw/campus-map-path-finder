package hw8;

/**
 * <b>Location</b> represents a <b>immutable</b> pair that stores a key and a value.
 * 
 * @author pinyiw
 * @param <K> the type of the key.
 * @param <V> the type of the value.
 */
public class Pair<K, V> {
	
	/** The key of this Pair. */
	private final K key;
	/** The value of this Pair. */
	private final V value;
	
	// Abstraction Function:
	// Pair, p, is a pair that stores a key as 'key' and a value as 'value'.
	// An example of Pair will be (k, v), where k is the key and v is the value.
	//
	// Representation invariant for every Pair p:
	// 	key != null &&
	//	value != null.
	
	/**
	 * @param key the key of this Pair stores.
	 * @param value the value of this Pair stores.
	 * @effect Construct a new Pair with the key and value.
	 * @throws IllegalArgumentException if key == null || value == null.
	 */
	public Pair(K key, V value) {
		if (key == null || value == null) {
			throw new IllegalArgumentException();
		}
		this.key = key;
		this.value = value;
	}
	
	/**
	 * Returns the key of this Pair.
	 * 
	 * @return the key of this Pair.
	 */
	public K getKey() {
		return key;
	}
	
	/**
	 * Returns the value of this Pair.
	 * 
	 * @return the value of this Pair.
	 */
	public V getValue() {
		return value;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Pair)) {
			return false;
		}
		Pair other = (Pair) o;
		return this.key.equals(other.getKey()) && this.value.equals(other.getValue());
	}
	
	@Override
	public int hashCode() {
		return key.hashCode() + value.hashCode();
	}
	
	/**
	 * Checks the representation invariant of this.
	 */
	private void checkRep() {
		assert (key != null) : "key equals to null";
		assert (value != null) : "value equals to null";
	}
}
