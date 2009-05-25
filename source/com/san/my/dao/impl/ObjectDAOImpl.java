
package com.san.my.dao.impl;


import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.hibernate.type.Type;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.san.my.dao.ObjectDAO;

/**
 * This class provides the default implementation of the platform DAO interface.
 * Some methods it handles directly, others it delegates. All DAOs
 * (EntityAccessBeans) should extend from this class and should follow the
 * rules:
 * 
 * <pre><li>
 * Name of DAO should 'domainObjectName' + 'EntityAccessBean'
 * </li>
 * <li>
 * DAOs should contain &lt;i&gt;only&lt;/i&gt; finder methods and should be
 * stateless.
 * </pre>
 */
public class ObjectDAOImpl extends HibernateDaoSupport implements ObjectDAO
{
    private static final String GENERIC_MULTIPLE_VALUES_FOUND_MESSAGE = "Multiple values found when only one was expected.";

    /**
     * Checks for null bindings and if found will throw an exception. 
     * For any <code>bind[i]</code> for a non-zero length array, if <code>bind[i]</code> is null then an exception
     * is thrown. As of now we are allowing null value or empty value for <code>binds</code> array.
     * 
     * @param binds the array of bind values to be verified.
     * 
     * @return true if binds are valid. If bindings are invalid, throws IllegalArgumentException.
     * @throws IllegalArgumentException If any of the bindings are invalid/null.
     */
    protected static boolean isBindingValid(String sql, Object[] binds)
    {
        if (null != binds && 0 < binds.length) {
            for (int i = 0; i < binds.length; i++) {
                if (binds[i] == null) {
                    throw new IllegalArgumentException("Received null bindings, which are not supported in HQL: "+sql+"binds:"+binds);
                    //    + sql + " binds: " + StringUtils.join(binds, ","));
                }
            }
        }
        return true;
    }

    /**
     * @see com.agentrics.mgs.common.dao.ObjectDAO#flush()
     */
    public void flush()
    {
        getHibernateTemplate().flush();
    }

    public Object get(Class c, Serializable id) throws DataAccessException
    {
        return getHibernateTemplate().get(c, id);
    }

    /**
     * @see com.agentrics.mgs.common.dao.ObjectDAO#save(java.lang.Object)
     */
    public Serializable save(Object dataObject) throws DataAccessException
    {  /*if(dataObject instanceof BaseDO){
    	this.populateCreationDetails((BaseDO)dataObject);
       }*/
       
       return getHibernateTemplate().save(dataObject);
    }

    /**
     * @see com.agentrics.mgs.common.dao.ObjectDAO#save(java.lang.String,
     *      java.lang.Object)
     */
    public Serializable save(String persistentObjectName, Object o) throws DataAccessException
    {

        return getHibernateTemplate().save(persistentObjectName, o);
    }

    /**
     * @see com.agentrics.mgs.common.dao.ObjectDAO#delete(java.lang.Object)
     */
    public void delete(Object o) throws DataAccessException
    {
        getHibernateTemplate().delete(o);
    }

    /**
     * @see com.agentrics.mgs.common.dao.ObjectDAO#saveOrUpdate(java.lang.Object)
     */
    public void saveOrUpdate(Object o) throws DataAccessException
    {

        getHibernateTemplate().saveOrUpdate(o);
    }

    /**
     * @see com.agentrics.mgs.common.dao.ObjectDAO#saveOrUpdate(java.lang.String,
     *      java.lang.Object)
     */
    public void saveOrUpdate(String persistentObjectName, Object o) throws DataAccessException
    {

        getHibernateTemplate().saveOrUpdate(persistentObjectName, o);
    }

    /**
     * @see com.agentrics.mgs.common.dao.ObjectDAO#update(java.lang.Object)
     */
    public void update(Object dataObject) throws DataAccessException
    {
    	/*if(dataObject instanceof BaseDO){
        	this.populateCreationDetails((BaseDO)dataObject);
           }*/
        getHibernateTemplate().update(dataObject);
    }

    /**
     * @see com.agentrics.mgs.common.dao.ObjectDAO#merge(java.lang.Object)
     */
    public Object merge(Object dataObject) throws DataAccessException
    {
        return getHibernateTemplate().merge(dataObject);
    }

    /**
     * @see com.agentrics.mgs.common.dao.ObjectDAO#refresh(java.lang.Object)
     */
    public void refresh(Object dataObject) throws DataAccessException
    {
        getHibernateTemplate().refresh(dataObject);
    }

    /** 
     * {@inheritDoc}
     */
    public void evict(Object dataObject) throws DataAccessException
    {
        getHibernateTemplate().evict(dataObject);
    }
    
    /**
     * @see com.agentrics.mgs.common.dao.ObjectDAO#refreshAndLock(java.lang.Object)
     */
    public void refreshAndLock(Object dataObject) throws DataAccessException
    {
        getHibernateTemplate().refresh(dataObject, LockMode.UPGRADE);
    }

    /**
     * @see com.agentrics.mgs.common.dao.ObjectDAO#findOne(java.lang.String)
     */
    public Object findOne(String sql) throws DataAccessException
    {
        List list = getHibernateTemplate().find(sql);
        if (list.size() > 1)
            throw new RuntimeException(GENERIC_MULTIPLE_VALUES_FOUND_MESSAGE);
        return getFirstElement(list);
    }

    /**
     * @see com.agentrics.mgs.common.dao.ObjectDAO#findOne(String sql, Object[]
     *      binds)
     */
    public Object findOne(String sql, Object[] binds) throws DataAccessException
    {
        return findOne(sql, binds, GENERIC_MULTIPLE_VALUES_FOUND_MESSAGE);
    }

    /**
     * @see com.agentrics.mgs.common.dao.ObjectDAO#findOne(java.lang.String,
     *      java.lang.Object)
     */
    public Object findOne(String sql, Object binds) throws DataAccessException
    {
        return this.findOne(sql, new Object[] { binds });
    }

    /**
     * @see com.agentrics.mgs.common.dao.ObjectDAO#findOne(String sql, Object[]
     *      binds)
     */
    public Object findOne(String sql, Object[] binds, String multipleFoundErrorKey) throws DataAccessException
    {
        if (!isBindingValid(sql, binds)) {
            // bindings included nulls
            return null;
        }

        List list = getHibernateTemplate().find(sql, binds);
        if (list.size() > 1)
            throw new RuntimeException(multipleFoundErrorKey);
        return getFirstElement(list);
    }

    // //////////////////////////////////////////
    // Named Queries (AKA Externalized Queries)
    // //////////////////////////////////////////

    /**
     * @see com.agentrics.mgs.common.dao.ObjectDAO#findOneByNamedQuery(java.lang.String,
     *      java.lang.Object[])
     */
    public Object findOneByNamedQuery(String queryName, Object[] values) throws DataAccessException
    {
        return findOneByNamedQuery(queryName, values, GENERIC_MULTIPLE_VALUES_FOUND_MESSAGE);
    }

    /**
     * @see com.agentrics.mgs.common.dao.ObjectDAO#findOneByNamedQuery(java.lang.String,
     *      java.lang.Object)
     */
    public Object findOneByNamedQuery(String queryName, Object value) throws DataAccessException
    {
        return findOneByNamedQuery(queryName, value, GENERIC_MULTIPLE_VALUES_FOUND_MESSAGE);
    }

    /**
     * @see com.agentrics.mgs.common.dao.ObjectDAO#findOneByNamedQueryAndNamedParam(java.lang.String,
     *      String paramName, java.lang.Object)
     */
    public Object findOneByNamedQueryAndNamedParam(String queryName, String paramName, Object value)
        throws DataAccessException
    {
        return findOneByNamedQueryAndNamedParam(queryName, paramName, value, GENERIC_MULTIPLE_VALUES_FOUND_MESSAGE);
    }

    /**
     * @see com.agentrics.mgs.common.dao.ObjectDAO#findOneByNamedQueryAndNamedParam(java.lang.String,
     *      String[] paramNames, java.lang.Object[] values)
     */
    public Object findOneByNamedQueryAndNamedParam(String queryName, String[] paramNames, Object[] values)
        throws DataAccessException
    {
        return findOneByNamedQueryAndNamedParam(queryName, paramNames, values, GENERIC_MULTIPLE_VALUES_FOUND_MESSAGE);
    }

    /**
     * @see com.agentrics.mgs.common.dao.ObjectDAO#countNamedQuery(java.lang.String,
     *      java.lang.Object[])
     */
    public int countNamedQuery(String sql, Object[] binds)
    {
        List results = this.findByNamedQuery(sql, binds);
        if (results.isEmpty()) {
            return 0;
        }
        return extractNumberFromList(results);
    }

    /**
     * @see com.agentrics.mgs.common.dao.ObjectDAO#countNamedQuery(java.lang.String,
     *      java.lang.Object)
     */
    public int countNamedQuery(String sql, Object bind)
    {
        return this.countNamedQuery(sql, new Object[] { bind });
    }

    /**
     * @see com.agentrics.mgs.common.dao.ObjectDAO#findByNamedQuery(java.lang.String,
     *      java.lang.Object[])
     */
    public List findByNamedQuery(final String queryName, final Object[] values) throws DataAccessException
    {
        if (isBindingValid(queryName, values)) {
            return super.getHibernateTemplate().findByNamedQuery(queryName, values);
        }
        else {
            return Collections.EMPTY_LIST;
        }
    }

    /**
     * @see com.agentrics.mgs.common.dao.ObjectDAO#findByNamedQuery(java.lang.String,
     *      java.lang.Object)
     */
    public List findByNamedQuery(String queryName, Object value) throws DataAccessException
    {
        if (value != null) {
            return super.getHibernateTemplate().findByNamedQuery(queryName, value);
        }
        else {
            return Collections.EMPTY_LIST;
        }
    }

    /**
     * @see com.agentrics.mgs.common.dao.ObjectDAO#findByNamedQuery(java.lang.String)
     */
    public List findByNamedQuery(String queryName) throws DataAccessException
    {
        return super.getHibernateTemplate().findByNamedQuery(queryName);
    }

    /**
     * @see com.agentrics.mgs.common.dao.ObjectDAO#findByNamedQueryAndNamedParam(java.lang.String,
     *      java.lang.String, java.lang.Object)
     */
    public List findByNamedQueryAndNamedParam(String queryName, String paramName, Object value)
        throws DataAccessException
    {
        return super.getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, paramName, value);
    }

    /**
     * @see com.agentrics.mgs.common.dao.ObjectDAO#findByNamedQueryAndNamedParam(java.lang.String,
     *      java.lang.String[], java.lang.Object[])
     */
    public List findByNamedQueryAndNamedParam(final String queryName, final String[] paramNames, final Object[] values)
        throws DataAccessException
    {
        return super.getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, paramNames, values);
    }

    /**
     * @see com.agentrics.mgs.common.dao.ObjectDAO#findByNamedQueryAndValueBean(java.lang.String,
     *      java.lang.Object)
     */
    public List findByNamedQueryAndValueBean(final String queryName, final Object valueBean)
        throws DataAccessException
    {
        return super.getHibernateTemplate().findByNamedQueryAndValueBean(queryName, valueBean);
    }

    // end named queries section

    /**
     * Load object matching the given key and return it. This will throw an
     * exception if the object cannot be found. This is generally less
     * preferable than using get(), unless it's a real case where you have an
     * object you KNOW must exist and are planning on throwing if it doesn't.
     * 
     * @param refClass
     *            the class you're trying to load
     * @param key
     *            the object's key
     * @return the object
     * @throws DataAccessException
     *             if something blows up OR the object is not found.
     */
    public Object load(Class refClass, Serializable key) throws DataAccessException
    {
        return getHibernateTemplate().load(refClass, key);
    }

    /**
     * @see com.agentrics.mgs.common.dao.ObjectDAO#lockAndLoad(java.lang.Class,
     *      java.io.Serializable)
     */
    public Object lockAndLoad(Class refClass, Serializable key)
    {
        return getHibernateTemplate().load(refClass, key, LockMode.UPGRADE);
    }

    /**
     * @see com.agentrics.mgs.common.dao.ObjectDAO#find(java.lang.String)
     */
    public List find(String queryString) throws DataAccessException
    {
        return getHibernateTemplate().find(queryString);
    }

    /**
     * @see com.agentrics.mgs.common.dao.ObjectDAO#find(java.lang.String,
     *      java.lang.Object)
     */
    public List find(String queryString, Object bind) throws DataAccessException
    {
        if (bind != null) {
            return getHibernateTemplate().find(queryString, bind);
        }

        return Collections.EMPTY_LIST;
    }

    public List find(String queryString, Object[] binds) throws DataAccessException
    {
        if (isBindingValid(queryString, binds)) {
            return getHibernateTemplate().find(queryString, (Object[]) binds);
        }

        return Collections.EMPTY_LIST;
    }

    /**
     * @see com.agentrics.mgs.common.dao.ObjectDAO#findAndLock(java.lang.String,
     *      java.lang.Object[])
     */
    public List findAndLock(String sql, Object[] binds)
    {
        List list = find(sql, binds);
        // kludge... for now just loop through... find out more efficient way
        // later...
        int size = list.size();
        for (int i = 0; i < size; i++) {
            getSession().lock(list.get(i), LockMode.UPGRADE);
        }
        return list;
    }

    /**
     * @see com.agentrics.mgs.common.dao.ObjectDAO#findByNamedParam(java.lang.String,
     *      java.lang.String[], java.lang.Object[])
     */
    public List findByNamedParam(String queryString, String[] paramNames, Object[] values) throws DataAccessException
    {
        if (isBindingValid(queryString, values)) {
            return getHibernateTemplate().findByNamedParam(queryString, paramNames, values);
        }
        else {
            // bindings included nulls
            return Collections.EMPTY_LIST;
        }
    }

    /**
     * @see com.agentrics.mgs.common.dao.ObjectDAO#queryByExample(java.lang.Object)
     */
    public Object queryByExample(Object example)
    {
        Session session = null;
        session = getSession();
        Criteria criteria = session.createCriteria(example.getClass());
        criteria.add(Example.create(example));
        List results = criteria.list();
        if (results.size() == 0) {
            return null;
        }
        else {
            return results.get(0);
        }
    }

    /**
     * Acts like a regular find(), but limits the results returned.
     * 
     * @param queryString
     *            the HQL to execute.
     * @param values
     *            the values to bind
     * @param maxRows
     *            the maximum number of rows you want to return.
     * @return a List, which may be empty, of the things you're searching for.
     */
    public List findWithLimit(final String queryString, final Object[] values, final int maxRows)
    {
        return (List) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException
            {
                Query queryObject = session.createQuery(queryString);

                if (values != null) {
                    for (int i = 0; i < values.length; i++) {
                        queryObject.setParameter(i, values[i]);
                    }
                }

                if (maxRows > 0) {
                    queryObject.setMaxResults(maxRows);
                }

                return queryObject.list();
            }
        }, true);
    }

    @SuppressWarnings("unchecked")
    public List executeSQLQuery(final String queryString, final Object value)
    {
        return executeSQLQuery(queryString, new Object[] { value }, null);
    }
    
    @SuppressWarnings("unchecked")
    public List executeSQLQuery(final String queryString, final Object[] value)
    {
        return executeSQLQuery(queryString, value, null);
    }


    @SuppressWarnings("unchecked")
    public List executeSQLQuery(final String queryString, final Object[] values, final ResultTransformer transformer)
    {
        return (List) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException
            {
                SQLQuery queryObject = session.createSQLQuery(queryString);

                if (values != null) {
                    for (int i = 0; i < values.length; i++) {
                        queryObject.setParameter(i, values[i]);
                    }
                }
                
                if(null != transformer) {
                    queryObject.setResultTransformer(transformer);
                }
                return queryObject.list();
            }
        }, true);
    }
    
    @SuppressWarnings("unchecked")
    public List executeSQLQuery(final String queryString, final Object value, final ResultTransformer transformer)
    {
        return executeSQLQuery(queryString, new Object[] { value }, transformer);
    }

    public long count(final String queryString, final boolean isHQL)
    {
        return count(queryString, new Object[] {}, isHQL);
    }

    public long count(final String queryString, final Object value, final boolean isHQL)
    {
        return count(queryString, new Object[] { value }, isHQL);
    }

    @SuppressWarnings("unchecked")
    public long count(final String queryString, final Object[] values, final boolean isHQL)
    {
        return (Long) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException
            {
                if (isHQL) {
                    Query query = session.createQuery(queryString);

                    if (values != null) {
                        for (int i = 0; i < values.length; i++) {
                            query.setParameter(i, values[i]);
                        }
                    }
                    return ((Long) query.iterate().next());
                }
                else {
                    SQLQuery query = session.createSQLQuery(queryString);

                    if (values != null) {
                        for (int i = 0; i < values.length; i++) {
                            query.setParameter(i, values[i]);
                        }
                    }
                    
                    // iterate() on SQLQuery is not supported.
                    // SQL queries do not currently support iteration
                    List list = query.list();
                    if (null != list && 0 < list.size()) {
                        return ((Number) list.iterator().next()).longValue();
                    }
                    else {
                        return Long.MIN_VALUE;
                    }
                }
            }
        }, true);
    }

    /**
     * @see com.agentrics.mgs.common.dao.ObjectDAO#countQuery(java.lang.String,
     *      java.lang.Object)
     */
    public int countQuery(String sql, Object bind)
    {
        return countQuery(sql, new Object[] { bind });
    }

    /**
     * @see com.agentrics.mgs.common.dao.ObjectDAO#countQuery(java.lang.String,
     *      java.lang.Object[])
     */
    public int countQuery(String sql, Object[] binds)
    {
        List results = find(sql, binds);
        if (results.isEmpty()) {
            return 0;
        }
        return extractNumberFromList(results);
    }

    /**
     * @see com.agentrics.mgs.common.dao.ObjectDAO#findAll(java.lang.Class)
     */
    public List findAll(Class clazz) throws DataAccessException
    {
        String sql = "from " + clazz.getName();
        return find(sql);
    }

    /**
     * @see com.agentrics.mgs.common.dao.ObjectDAO#clear()
     */
    public void clear()
    {
        getSession().clear();
    }

    /** 
     * {@inheritDoc}
     */
    public Session getHibSession()
    {
        return getSession();

    }

    public List findAndLockByNamedQuery(String queryName, Object bind)
    {
        List list = findByNamedQuery(queryName, bind);
        for (Object obj : list) {
            getSession().lock(obj, LockMode.UPGRADE);
        }
        return list;
    }

    /**
     * Same as findByNamedQuery(queryName,binds). The additional input transformer
     * determines the shape of the result. For e.g passing a value Transformers.ALIAS_TO_ENTITY_MAP
     * will return a list of maps . Each map representing one row of the underlying resultset. The key 
     * being the column name and value the return value. This is very useful in case of raw sqls spanning 
     * over multiple tables. One can also pass Transformer.aliasToBean(ValueBean.class) if he/she wants the 
     * result as a custom value bean i.e ValueBean.
     */
    public List findByNamedQueryAsTransformer(
        final String queryName,
        final Object[] binds,
        final ResultTransformer transformer)
    {
        return (List) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException
            {
                Query query = getSession().getNamedQuery(queryName);
                if (binds != null) {
                    for (int i = 0; i < binds.length; i++) {
                        query.setParameter(i, binds[i]);
                    }
                }
                query.setResultTransformer(transformer);
                return query.list();
            }
        });
        /*Query query = getSession().getNamedQuery(queryName);
        if (binds != null) {
            for (int i = 0; i < binds.length; i++) {
                query.setParameter(i, binds[i]);
            }
        }
        query.setResultTransformer(transformer);
        return query.list();*/
    }

    
    /** 
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> findBySqlQuery(
        final String queryString,
        final Map<String, Type> scalars,
        final List<Object> binds,
        final Class<T> aliasClass)
    {
        return (List<T>) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException
            {
                SQLQuery query = session.createSQLQuery(queryString);

                //add the binds
                if (binds != null) {
                    for (int i = 0; i < binds.size(); i++) {
                        query.setParameter(i, binds.get(i));
                    }
                }

                if (scalars != null) {
                    for (String key : scalars.keySet()) {
                        query.addScalar(key, scalars.get(key));
                    }
                }

                if (aliasClass != null) {
                    if (aliasClass.equals(Map.class)) {
                        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                    }
                    else {
                        query.setResultTransformer(Transformers.aliasToBean(aliasClass));
                    }
                }
                return (List<T>) query.list();
            }

        });
    }

    public Integer executeUpdateByNamedQuery(final String queryName, final Object[] binds)
    {
        return (Integer) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException
            {
                Query updateQuery = session.getNamedQuery(queryName);

                //add the binds 
                if (binds != null) {
                    for (int i = 0; i < binds.length; i++) {
                        updateQuery.setParameter(i, binds[i]);
                    }
                }
                return updateQuery.executeUpdate();
            }

        });
    }
    
    public Integer executeUpdateSQLQuery(final String queryString, final Object[] values)
    {
        return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
            
            public Object doInHibernate(Session session) throws HibernateException, SQLException
            {
                SQLQuery queryObject = session.createSQLQuery(queryString);

                if (values != null) {
                    for (int i = 0; i < values.length; i++) {
                        queryObject.setParameter(i, values[i]);
                    }
                }

                return queryObject.executeUpdate();
            }
        });
    }

    /**
     * Helpful method that will return the int value of the first thing on the
     * List. Assuming it is a Long or an Integer. Needed because of a change in
     * Hibernate.
     * 
     * @param list
     * @return the int value
     */
    protected int extractNumberFromList(List list)
    {
        if (list == null) {
            throw new IllegalArgumentException("ObjectDAOImpl.extractNumberFromList was passed a null List.");
        }
        if (list.size() == 0) {
            throw new IllegalArgumentException("ObjectDAOImpl.extractNumberFromList was passed a List of zero length.");
        }
        Object object = list.get(0);
        if (object instanceof Integer) {
            Integer integerValue = (Integer) object;
            return integerValue.intValue();
        }
        else if (object instanceof Long) {
            Long longValue = (Long) object;
            return longValue.intValue();
        }
        else {
            throw new IllegalArgumentException(
                "ObjectDAOImpl.extractNumberFromList was passed a List containing at position 0 an object of Class "
                    + object.getClass().getName() + " which is not supported.");
        }
    }

    protected Object findOneByNamedQueryAndNamedParam(
        String queryName,
        String paramName,
        Object value,
        String multipleFoundErrorKey) throws DataAccessException
    {
        List list = this.findByNamedQueryAndNamedParam(queryName, paramName, value);
        if (list.size() > 1)
            throw new RuntimeException(multipleFoundErrorKey);
        return getFirstElement(list);
    }

    protected Object findOneByNamedQueryAndNamedParam(
        String queryName,
        String[] paramNames,
        Object[] values,
        String multipleFoundErrorKey) throws DataAccessException
    {
        List list = this.findByNamedQueryAndNamedParam(queryName, paramNames, values);
        if (list.size() > 1)
            throw new RuntimeException(multipleFoundErrorKey);
        return getFirstElement(list);
    }

    protected Object findOneByNamedQuery(String queryName, Object value, String multipleFoundErrorKey)
        throws DataAccessException
    {
        return this.findOneByNamedQuery(queryName, new Object[] { value });
    }

    protected Object findOneByNamedQuery(String queryName, Object[] values, String multipleFoundErrorKey)
        throws DataAccessException
    {
        List list = this.findByNamedQuery(queryName, values);
        if (list.size() > 1)
            throw new RuntimeException(multipleFoundErrorKey);
        return getFirstElement(list);
    }

    /**
     * Gets the first element from the list.
     * 
     * @param list
     * @return returns the first element from the list null if the list is empty
     */
    private Object getFirstElement(List list)
    {
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }


	 /**
    * This method updates the given collection of entities
    * 
    * @param Collection<? extends Object>
    */
	public void updateAll(Collection<? extends Object> objectCollection) {
		Iterator itr = objectCollection.iterator();
		while(itr.hasNext()){
			Object object = itr.next();
			this.update(object);
		}
	
	}
	
	
	 /**
    * This method saves the given collection of entities
    * 
    * @param Collection<? extends Object>
    */
	public void saveAll(Collection<? extends Object> objectCollection) {
		Iterator itr = objectCollection.iterator();
		while(itr.hasNext()){
			Object object = itr.next();
			this.save(object);
		}
	
	}
	
	public void saveOrUpdateAll(Collection<? extends Object> objectCollection){
		Iterator itr = objectCollection.iterator();
		while(itr.hasNext()){
			Object object = itr.next();
			this.saveOrUpdate(object);
		}
	}
	
	
	 /**
    * This method deletes the given collection of entities
    * 
    * @param Collection<? extends Object>
    */
	public void deleteAll(Collection<? extends Object> objectCollection) {
		Iterator itr = objectCollection.iterator();
		while(itr.hasNext()){
			Object object = itr.next();
			this.delete(object);
		}
	
	}



}
