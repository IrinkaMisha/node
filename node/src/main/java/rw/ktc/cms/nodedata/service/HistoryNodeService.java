package rw.ktc.cms.nodedata.service;

import rw.ktc.cms.nodedata.HistoryNode;
import rw.ktc.cms.nodedata.Node;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: miha
 * Date: 25.04.14
 * Time: 13:44
 * To change this template use File | Settings | File Templates.
 */
public interface HistoryNodeService<T extends HistoryNode, ID extends Number> extends NodeService<T, ID>{
    //Метод возвращает все исторические узлы
//    List<T> getAllHistory();

    //Метод возвращает все исторические объекты(состояния) конкретного узла
    List<T> getHistoryConcreteNode(T node);

    void removeNode(T node, Node generatingNode);

    @Override
    ID createNode(T node, Node generatingNode) throws IllegalArgumentException;
}