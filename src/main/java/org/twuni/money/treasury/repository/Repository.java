package org.twuni.money.treasury.repository;

public interface Repository<K, V> {

	public V findById( K key );

	public void save( V value );

	public void delete( V value );

}
