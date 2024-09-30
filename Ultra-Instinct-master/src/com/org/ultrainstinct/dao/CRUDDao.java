package com.org.ultrainstinct.dao;

import java.util.List;

/**
 * <p>
 * CRUDDao interface
 * </p>
 *
 * @author MinhNgoc
 */
public interface CRUDDao<T, ID> {

    /**
     * <p>
     * Save entity
     * </p>
     *
     * @param entity T
     * @author MinhNgoc
     */
    void save(T entity);

    /**
     * <p>
     * Update entity by id
     * </p>
     * @param entity T
     * @author MinhNgoc
     */
    void update(T entity, ID id);

    /**
     * <p>
     * Find entity by ID
     * </p>
     *
     * @param id ID
     * @return Optional<T>
     * @author MinhNgoc
     */
    T findById(ID id);

    /**
     * <p>
     * Find all entity
     * </p>
     *
     * @return List<T>
     * @author MinhNgoc
     */
    List<T> findAll();

    /**
     * <p>
     * Delete entity by ID
     * </p>
     *
     * @param id ID
     * @author MinhNgoc
     */
    void deleteById(ID id);
    
   
}
