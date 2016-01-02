package rw.ktc.cms.nodedata.service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: miha
 * Date: 01.11.13
 * Time: 10:22
 * To change this template use File | Settings | File Templates.
 */
public interface GenericDAO<T, ID> {
    // не должно быть вообще - класс нужен только для реализации, а каким образом его получили - не важно.
//    Class<T> getPersistentClass();

    T getById(ID id, boolean lock);

    List<T> getAll();

    List<T> getAll(String sortName, Boolean asc);

    List<T> getAll(String sortName, Boolean asc, Integer cursor, Integer count);

    List<T> getByExample(T exampleInstance, String... excludeProperty);

    List<T> getByExampleOrder(T exampleInstance, String nameOrder, Boolean asc, String... excludeProperty);

    List<T> getByExampleCursorOrder(T exampleInstance, String nameOrder, Boolean asc, Integer cursor, Integer count, String... excludeProperty);

    T saveOrUpdate(T entity);

    void deleteById(ID id);

    void delete(T entity);
}
