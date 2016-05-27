package hw8;

/**
 * 
 * @author pinyiw
 */
public class Pair<K, V> {
	
	private final K key;
	private final V value;
	
	public Pair(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
	public K getKey() {
		return key;
	}
	
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
}
