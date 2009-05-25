
package com.san.my.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.Type;
import org.springframework.dao.DataAccessException;

/**
 * This class provides a facade for all the basic DAOs for all platforms. It
 * includes the basic CRUD activities like insert(), update(), delete(), as
 * well as all the other specific routines that are generic across all Apps.
 */
public interface ObjectDAO
{

    // //////////////////////////////////////////
    // Named Queries (AKA Externalized Queries)
    // //////////////////////////////////////////

    /**
     * Pass named query and an array of bindings returns desired Object or null
     * 
     * @see org.springframework.orm.hibernate3.support.HibernateDaoSupport
     * @param queryName externalized query
     * @param binds
     * @return desired object or null
     * @throws DataAccessException if a problem occurs
     */
    public Object findOneByNamedQuery(String queryName, Object[] binds) throws DataAccessException;

    /**
     * Pass named query and an binding returns desired Object or null
     * 
     * @see org.springframework.orm.hibernate3.support.HibernateDaoSupport
     * @param queryName queryName externalized query
     * @param bind
     * @return desired object or null
     * @throws DataAccessException if a problem occurs
     */
    public Object findOneByNamedQuery(String queryName, Object bind) throws DataAccessException;

    /**
     * Pass named query, binding, and paramName; returns a list of desired
     * objects
     * 
     * @see org.springframework.orm.hibernate3.support.HibernateDaoSupport
     * @param queryName externalized query
     * @param paramName
     * @param value
     * @return a list with the desired objects
     * @throws DataAccessException if a problem occurs
     */
    public Object findOneByNamedQueryAndNamedParam(String queryName, String paramName, Object value)
        throws DataAccessException;

    /**
     * Pass named query, bindings, and paramNames; returns a list of desired
     * objects
     * 
     * @see org.springframework.orm.hibernate3.support.HibernateDaoSupport
     * @param queryName externalized query
     * @param paramNames
     * @param values
     * @return a list with the desired object
     * @throws DataAccessException if a problem occurs
     */
    public Object findOneByNamedQueryAndNamedParam(String queryName, String[] paramNames, Object[] values)
        throws DataAccessException;

    /**
     * Pass named query and an array of bindings returns count
     * 
     * @param queryName externalized query
     * @param binds
     * @return count
     */
    public int countNamedQuery(String queryName, Object[] binds);

    /**
     * Pass named query and an bind returns count
     * 
     * @param queryName externalized query
     * @param bind
     * @return count
     */
    public int countNamedQuery(String queryName, Object bind);

    /**
     * Pass count query and bind
     * 
     * @param sql
     * @param bind
     * @return int of 0 or more...
     */
    public int countQuery(String sql, Object bind);

    /**
     * Pass count query and binds
     * 
     * @param sql
     * @param binds
     * @return int of 0 or more...
     */
    public int countQuery(String sql, Object[] binds);

    /**
     * Pass named query and an array of bindings returns a list of desired
     * objects
     * 
     * @see org.springframework.orm.hibernate3.support.HibernateDaoSupport
     * @param queryName externalized query
     * @param binds
     * @return a list of desired objects
     * @throws DataAccessException if a problem occurs
     */
    public List findByNamedQuery(final String queryName, final Object[] binds) throws DataAccessException;

    /**
     * Pass named query and binding returns a list of desired objects
     * 
     * @see org.springframework.orm.hibernate3.support.HibernateDaoSupport
     * @param queryName externalized query
     * @param bind
     * @return a list of desired objects
     * @throws DataAccessException if a problem occurs
     */
    public List findByNamedQuery(String queryName, Object bind) throws DataAccessException;

    /**
     * Pass named query returns a list of desired objects
     * 
     * @see org.springframework.orm.hibernate3.support.HibernateDaoSupport
     * @param queryName externalized query
     * @return list of desired objects
     * @throws DataAccessException if a problem occurs
     */
    public List findByNamedQuery(String queryName) throws DataAccessException;

    /**
     * Pass named query, binding, and paramName; returns a list of desired
     * objects
     * 
     * @see org.springframework.orm.hibernate3.support.HibernateDaoSupport
     * @param queryName externalized query
     * @param paramName
     * @param value
     * @return a list of desired objects
     * @throws DataAccessException if a problem occurs
     */
    public List findByNamedQueryAndNamedParam(String queryName, String paramName, Object value)
        throws DataAccessException;

    /**
     * Pass named query, an array of bindings, and paramNames; returns a list
     * of desired objects
     * 
     * @see org.springframework.orm.hibernate3.support.HibernateDaoSupport
     * @param queryName externalized query
     * @param paramNames
     * @param values
     * @return a list of desired objects
     * @throws DataAccessException if a problem occurs
     */
    public List findByNamedQueryAndNamedParam(final String queryName, final String[] paramNames, final Object[] values)
        throws DataAccessException;

    /**
     * @see org.springframework.orm.hibernate3.support.HibernateDaoSupport
     * @param queryName externalized query
     * @param valueBean
     * @return a list of desired objects
     * @throws DataAccessException if a problem occurs
     */
    public List findByNamedQueryAndValueBean(final String queryName, final Object valueBean)
        throws DataAccessException;

    // end named queries section

    /**
     * Flushes the Hibernate buffers.
     */
    public void flush();

    /**
     * Inserts a new object into the database and attaches it to the current
     * session.
     * 
     * @param o the object to save
     * @return the serialized key
     * @throws DataAccessException if something explodes.
     */
    public Serializable save(Object o) throws DataAccessException;

    /**
     * Inserts a new object into the database and attaches it to the current
     * session.
     * 
     * @param persistantObjectName
     * @param o the object to save
     * @return the serialized key
     * @throws DataAccessException if something explodes.
     */
    public Serializable save(String persistentObjectName, Object o) throws DataAccessException;

    /**
     * Copy the state of the given object onto the persistent object with the
     * same identifier. In case of a new entity, the state will be copied over
     * as well. Note that merge will not update the identifiers in the
     * passed-in object graph.
     * 
     * @param o the object to merge with the corresponding persistence instance
     * @return the updated, registered persistent instance
     * @throws DataAccessException in case of Hibernate errors
     */
    public Object merge(Object o) throws DataAccessException;

    /**
     * Gets the instance of the class with that key, or null if no such object
     * exists.
     * 
     * @param c the class of the object to load.
     * @param id the PK for the object.
     * @return the object, or null if it can't be found.
     * @throws DataAccessException
     */
    public Object get(Class c, Serializable id) throws DataAccessException;

    /**
     * Deletes an object from the database, cascading to any objects whose
     * mapping files have cascade set.
     * 
     * @param o the object to nuke
     * @throws DataAccessException if something explodes.
     */
    public void delete(Object o) throws DataAccessException;

    /**
     * Saves an object to the database if has never been saved before, or
     * updates it if it has. Use this only when you're not sure whether the
     * object HAS been saved before and think it might need to be. As long as a
     * session-attached object's session remains open, it'll automatically
     * flush updates to the DB.
     * 
     * @param o the object to save or update.
     * @throws DataAccessException if something explodes.
     */
    public void saveOrUpdate(Object o) throws DataAccessException;

    /**
     * Saves an object to the database if has never been saved before, or
     * updates it if it has. Use this only when you're not sure whether the
     * object HAS been saved before and think it might need to be. As long as a
     * session-attached object's session remains open, it'll automatically
     * flush updates to the DB.
     * 
     * @param persistentObjectName to use for persistence
     * @param o the object to save or update.
     * @throws DataAccessException if something explodes.
     */
    public void saveOrUpdate(String persistentObjectName, Object o) throws DataAccessException;

    /**
     * Update the persistent state associated with the given identifier. An
     * exception is thrown if there is a persistent instance with the same
     * identifier in the current session.
     * 
     * @param obj a transient instance containing updated state
     */
    public void update(Object dataObject) throws DataAccessException;

    /**
     * Refreshes the state of the object from the database. This is important
     * when you think an object might be stale and you want to ensure that it
     * processes OK.
     * 
     * @param obj a transient instance that you'd like to refresh.
     */
    public void refresh(Object dataObject) throws DataAccessException;

    /**
     * Refreshes the state of the object from the database. This is important
     * when you think an object might be stale and you want to ensure that it
     * processes OK. This will also lock the object, which prevents other
     * access until you are committed. You can still get StateObject exceptions
     * from other threads, but you shouldn't get them from this one.
     * 
     * @param obj a transient instance that you'd like to refresh.
     */
    public void refreshAndLock(Object dataObject) throws DataAccessException;

    /**
     * Evicts the given <code>dataObject</code> from the current session.
     */
    public void evict(Object dataObject) throws DataAccessException;
    
    /**
     * Returns a single object loaded by the sql provided. This will return
     * null if the object cannot be found, and throw an exception if more than
     * one object was returned.
     * 
     * @param sql the HQL to use to do the finding.
     * @return the object, if it was found, null if not.
     * @throws DataAccessException if more than one object was found.
     */
    public Object findOne(String sql) throws DataAccessException;

    /**
     * Returns a single object loaded by the sql, binds, and bind types
     * provided. This will return null if the object cannot be found, and throw
     * an exception if more than one object was returned.
     * 
     * @param sql the HQL to use to do the finding.
     * @param binds the binds to use, or null to use none.
     * @return the object, if it was found, null if not.
     * @throws DataAccessException if more than one object was found.
     */
    public Object findOne(String sql, Object bind) throws DataAccessException;

    /**
     * Returns a single object loaded by the sql, binds, and bind types
     * provided. This will return null if the object cannot be found, and throw
     * an exception if more than one object was returned.
     * 
     * @param sql query
     * @param bind array
     * @return object desired
     * @throws DataAccessException if there is a problem
     */
    public Object findOne(String sql, Object[] bind) throws DataAccessException;

    /**
     * Load object matching the given key and return it. This will throw an
     * exception if the object cannot be found. This is generally less
     * preferable than using get(), unless it's a real case where you have an
     * object you KNOW must exist and are planning on throwing if it doesn't.
     * 
     * @param refClass the class you're trying to load
     * @param key the object's key
     * @return the object
     * @throws DataAccessException if something blows up OR the object is not
     *         found.
     */
    public Object load(Class refClass, Serializable key) throws DataAccessException;

    /**
     * loads all the data objects from the database
     * 
     * @param clazz
     * @return
     * @throws DataAccessException
     */
    public List findAll(Class clazz) throws DataAccessException;

    /**
     * Loads the objects as requested and returns them
     * 
     * @param queryString the HQL to execute
     * @return the List of objects.
     * @throws DataAccessException if something blows up OR the object is not
     *         found.
     */
    public List find(String queryString) throws DataAccessException;

    /**
     * Returns a list of objects
     * 
     * @param queryString
     * @param bind
     * @return list of objects
     * @throws DataAccessException if there is a problem
     */
    public List find(String queryString, Object bind) throws DataAccessException;

    /**
     * Returns a list of objects
     * 
     * @param queryString
     * @param bind array
     * @return list of objects
     * @throws DataAccessException if there is a problem
     */
    public List find(String queryString, Object[] bind) throws DataAccessException;

    /**
     * Loads the objects For Update as requested and returns them
     * 
     * @param queryString the HQL to execute
     * @param values the values to bind
     * @return the List of objects.
     * @throws DataAccessException if something blows up OR the object is not
     *         found.
     */
    public List findAndLock(String sql, Object[] binds);

    /**
     * Does a find but allows the use of named parameters instead of positional
     * arguments.
     * 
     * @param queryString the query
     * @param paramNames the names of the parameters in the SQL
     * @param values the objects to use as queries
     * @return the list of objects
     * @throws DataAccessException
     */
    public List findByNamedParam(String queryString, String[] paramNames, Object[] values) throws DataAccessException;

    /**
     * Does a Select for Update on the given row.
     * 
     * @param refClass the class you're trying to load
     * @param key the key of the object you're trying to load
     * @return the object
     */
    public Object lockAndLoad(Class refClass, Serializable key);

    /**
     * Gets an object using query by example
     * 
     * @param example
     * @return the object
     */
    public Object queryByExample(Object example);

    /**
     * Clears the Hibernate Session
     */
    public void clear();

    public List findAndLockByNamedQuery(String queryName, Object bind);

    public long count(final String queryString, final boolean isHQL);

    public long count(final String queryString, final Object value, final boolean isHQL);

    public long count(final String queryString, final Object[] values, final boolean isHQL);

    /**
     * Same as findByNamedQuery(queryName,binds). The additional input transformer
     * determines the shape of the result. For e.g passing a value Transformers.ALIAS_TO_ENTITY_MAP
     * will return a list of maps . Each map representing one row of the underlying resultset. The key 
     * being the column name and value the return value. This is very useful in case of raw sqls spanning 
     * over multiple tables. One can also pass Transformer.aliasToBean(ValueBean.class) if he/she wants the 
     * result as a custom value bean i.e ValueBean.
     *
     */
    public List findByNamedQueryAsTransformer(String queryName, Object[] binds, ResultTransformer transformer);

    /**
     * This executes the the given sql query and returns the results as a 
     * list of given aliasClass objects of type Class<T>. 
     * @param <T> Type of the given aliasClass.
     * @param query The given sql query
     * @param scalars Its a map of <column name, Type>. Make sure that the 
     *        given aliasClass Class has properties with the same names and 
     *        matching types.
     * @param binds Binding parameters
     * @param aliasClass The bean to which query results will be mapped to.
     * @return
     */
    public <T> List<T> findBySqlQuery(String query, Map<String, Type> scalars, List<Object> binds, Class<T> aliasClass);

    /**
     * Shadow method that returns the underlying hibernate session.
     * @return
     */
    public Session getHibSession();

    public Integer executeUpdateByNamedQuery(final String queryName, final Object[] binds);
    
    /**
     * This method updates the given collection of entities
     * 
     * @param Collection<? extends Object>
     */
 	public void updateAll(Collection<? extends Object> objectCollection) ;
 	
 	 /**
     * This method saves the given collection of entities
     * 
     * @param Collection<? extends Object>
     */
 	public void saveAll(Collection<? extends Object> objectCollection);
 	 /**
     * This method deletes the given collection of entities
     * 
     * @param Collection<? extends Object>
     */
 	public void deleteAll(Collection<? extends Object> objectCollection);
}
