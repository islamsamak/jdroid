package com.jdroid.java.repository;

import java.util.Collection;
import java.util.List;
import com.jdroid.java.domain.Identifiable;

/**
 * Interface that all repositories must adhere to. It provides basic repository functionality.
 * 
 * @param <T> This is a parameterized interface.
 */
public interface Repository<T extends Identifiable> {
	
	/**
	 * Retrieves an {@link Identifiable} from the repository according to an id or null in case no {@link Identifiable}
	 * with the given id is not found
	 * 
	 * @param id the id for the {@link Identifiable} to retrieve
	 * @return the {@link Identifiable} retrieved.
	 */
	public T get(Long id);
	
	/**
	 * Adds an {@link Identifiable} to the repository.
	 * 
	 * @param item The {@link Identifiable} to add.
	 */
	public void add(T item);
	
	/**
	 * Adds a collection of {@link Identifiable}s to the repository.
	 * 
	 * @param items The {@link Identifiable}s to add.
	 */
	public void addAll(Collection<T> items);
	
	/**
	 * Update an {@link Identifiable} on the repository.
	 * 
	 * @param item The {@link Identifiable} to update.
	 */
	public void update(T item);
	
	/**
	 * Removes an {@link Identifiable} from the repository.
	 * 
	 * @param item The {@link Identifiable} to remove
	 */
	public void remove(T item);
	
	/**
	 * Removes all the {@link Identifiable}s that the repository has.
	 */
	public void removeAll();
	
	public void removeAll(Collection<T> items);
	
	/**
	 * @param fieldName
	 * @param values
	 * @return items the items with the fieldName that match with values.
	 */
	public List<T> findByField(String fieldName, Object... values);
	
	/**
	 * Obtains a list containing all the {@link Identifiable}s in the repository
	 * 
	 * @return the list of {@link Identifiable}s
	 */
	public List<T> getAll();
	
	/**
	 * @param ids
	 * @return All the items with the ids
	 */
	public List<T> getAll(List<Long> ids);
	
	/**
	 * Removes the {@link Identifiable} with the id
	 * 
	 * @param id The {@link Identifiable} id to be removed
	 */
	public void remove(Long id);
	
	/**
	 * @return If the repository has data or not
	 */
	public Boolean isEmpty();
	
	public Long getSize();
	
	/**
	 * Replaces all the {@link Identifiable}s in the repository by new ones.
	 * 
	 * @param items The new {@link Identifiable}s to replace the old ones.
	 */
	public void replaceAll(Collection<T> items);
	
	/**
	 * @return The unique instance
	 */
	public T getUniqueInstance();
}
