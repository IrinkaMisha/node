package rw.ktc.cms.nodedata.service.hib;

import org.hibernate.*;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import rw.ktc.cms.nodedata.service.GenericDAO;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: miha
 * Date: 28.10.13
 * Time: 11:31
 * To change this template use File | Settings | File Templates.
 */
@Transactional(readOnly = true)
public class GenericHibernateDAO<T extends Serializable, ID extends Serializable> implements GenericDAO<T, ID> {
    private static final Logger log = LoggerFactory.getLogger(GenericHibernateDAO.class);
    private Class<T> persistentClass;

    public Class<T> getPersistentClass() {
        try {
            if (persistentClass == null) {
                persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            }
        } catch (ClassCastException e) {
            log.error("что бы не было этого исключения необходимо наследоваться от generic класса и четко его параметризовать - например <Role, Long>:" + e.getLocalizedMessage());
            throw e;
        }
        return persistentClass;
    }

    protected SessionFactory sessionFactory;

    @Autowired
    @Qualifier("cmsSessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected SessionFactory getSessionFactory() {
        if (sessionFactory == null)
            throw new IllegalStateException("SessionFactory has not been set on DAO before usage");
        return sessionFactory;
    }

    public GenericHibernateDAO() {
    }

    public GenericHibernateDAO(Class persistentClass) {
        this.persistentClass = persistentClass;
    }

    public GenericHibernateDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public GenericHibernateDAO(Class persistentClass, SessionFactory sessionFactory) {
        this.persistentClass = persistentClass;
        this.sessionFactory = sessionFactory;
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    public T getById(ID id, boolean lock) {
        Object obj;
        if (lock) {
            obj = getSession().get(getPersistentClass(), id, LockOptions.UPGRADE);
        } else {
            obj = getSession().get(getPersistentClass(), id);
        }
        return (T) obj;
    }

    public List<T> getAll() {
        return findByCriteria();
    }

    public List<T> getAll(String sortName, Boolean asc) {
        return findByCriteria(sortName, asc);
    }

    public List<T> getAll(String sortName, Boolean asc, Integer cursor, Integer count) {
        return findByCriteria(sortName, asc, cursor, count);
    }

    public List<T> getByExample(T exampleInstance, String... excludeProperty) {
        return getByExampleOrder(exampleInstance, null, null, excludeProperty);
    }

    public List<T> getByExampleOrder(T exampleInstance, String nameOrder, Boolean asc, String... excludeProperty) {
        return getByExampleCursorOrder(exampleInstance, nameOrder, asc, null, null, excludeProperty);
    }

    public List<T> getByExampleCursorOrder(T exampleInstance, String nameOrder, Boolean asc, Integer cursor, Integer count, String... excludeProperty) {
        Criteria crit = getSession().createCriteria(getPersistentClass());
        Example example = Example.create(exampleInstance);
        for (String exclude : excludeProperty) {
            example.excludeProperty(exclude);
        }
        crit.add(example);
        if (cursor != null && count != null) {
            crit.setFirstResult(cursor).setMaxResults(count);
        }
        if (nameOrder != null) {
            crit.addOrder((asc) ? Order.asc(nameOrder) : Order.desc(nameOrder));
        }
        Set setL = new HashSet(crit.list());
        return new ArrayList(setL);
    }

    @Transactional
    public T saveOrUpdate(T entity) throws HibernateException {
        getSession().saveOrUpdate(entity);
        return entity;
    }

    @Transactional
    public void deleteById(ID id) {
        getSession().delete(getById(id, false));
    }

    @Transactional
    public void delete(T entity) {
        getSession().delete(entity);
    }

    /**
     * Use this inside subclasses as a convenience method.
     */
    protected List<T> findByCriteria(Criterion... criterion) {
        return findByCriteria(null, null, criterion);
    }

    protected List<T> findByCriteria(String sortName, Boolean asc, Criterion... criterion) {
        return findByCriteria(sortName, asc, null, null, criterion);
    }

    protected List<T> findByCriteria(String sortName, Boolean asc, Integer cursor, Integer count, Criterion... criterion) {
        Criteria crit = getSession().createCriteria(getPersistentClass());
        for (Criterion c : criterion) {
            crit.add(c);
        }
        if (cursor != null && count != null) {
            crit.setFirstResult(cursor).setMaxResults(count);
        }
        if (sortName != null) {
            crit.addOrder((asc) ? Order.asc(sortName) : Order.desc(sortName));
        }
        return crit.list();
    }
}