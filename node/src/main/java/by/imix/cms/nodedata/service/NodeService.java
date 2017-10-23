package by.imix.cms.nodedata.service;

import by.imix.cms.nodedata.state.State;
import by.imix.cms.nodedata.Node;
import by.imix.cms.nodedata.NodeProperty;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: miha
 * Date: 25.04.14
 * Time: 13:26
 * To change this template use File | Settings | File Templates.
 */
public interface NodeService<T extends Node, ID extends Number> extends GenericDAO<T, ID> {
    //Метод возвращает все свойства узла с ключем key
    List<NodeProperty> getPropertysValue(T node, String key);

    //Метод возвращает все свойства узла
    List<NodeProperty> getAllPropertys(T node);

    //Метод возвращает узел по идентификатору узла со всеми подгруженными списками
    T getFullNodeById(ID id);

    //Метод возвращает все возможные состояния, которые может принять узел
    List<State> getStates(T node);

    //Метод возвращает состояние по идентификатору
    State loadState(Long idState);

    //Метод удаляет узел
    void removeNode(T node) throws IllegalArgumentException;

    //Метод сохраняет узел node - узел который нужно сохранить, generatingNode - узел который ответственнй за сохранение
    T saveOrUpdateNode(T node, Node generatingNode);

    ID createNode(T node, Node generatingNode) throws IllegalArgumentException;

    //Метод загружает все связанные объекты
    T loadFullObject(T node);

    //Метод возвращает все узлы у которых есть проперти с key и value
    List<T> getAllNodeFromPrKey(String key, String value);


    //Метод возвращает единственное свойство с ключем key
    NodeProperty getSinglePropertysValue(T node, String key);

    // этот метод нужен для сохранения/обновления списка значений Объекта как свойства объекта Node
    // например для сохранения списка permissions объекта Role ,
    // т.е. тех объектов , для которых не создавались поля в таблице( ну или чтоб не создавать дополнительные таблицы)
    // например role = replaceProperties(role, Role.NAME_FIELD_LIST_PERMISSIONS_FOR_NODE_PROPERTY, values)
    T replaceProperties(T node,String nameProperty, List<String> values);
    T addProperties(T node,String nameProperty, List<String> values);

    List<T> getAllNodeFromType(String type);

    @Deprecated
    Node getNodeById(Class clazz, ID id);
    @Deprecated
    List<Node> getAllNodeFromPrKey(Class clazz, String key, String value);
    //Метод возвращает виртуальные(узлы классы которых различаются свойством type) узлы по типу
    @Deprecated
    List<T> getAllNodeFromType(Class clazz,String type);

}