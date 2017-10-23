package by.imix.cms.nodedata.service.hib;

import by.imix.cms.nodedata.HistoryNode;
import by.imix.cms.nodedata.Node;
import org.hibernate.Hibernate;
import org.hibernate.LazyInitializationException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import by.imix.cms.nodedata.service.HistoryNodeService;

import java.util.List;

/**
 * Created by miha on 07.05.2015.
 */
@Transactional(readOnly = true)
public class HistNodeServiceGeneric<T extends HistoryNode, ID extends Number> extends NodeServiceGeneric<T, ID> implements HistoryNodeService<T, ID> {
    private static final Logger logger = LoggerFactory.getLogger(HistNodeServiceGeneric.class);

    //Метод возвращает все исторические узлы
//    public List<T> getAllHistory(){
//        return getSession().createCriteria(getPersistentClass()).add(Restrictions.eq("hystory", false)).add(Restrictions.eq("removed", false)).list();
//    }

    public List<T> getHistoryConcreteNode(T node) {
        Criterion rest1 = Restrictions.eq("hystPremParent", node.getHystPremParent());
        return findByCriteria(rest1);
    }

    @Override
    public List<T> getAll() {
        Criterion rest1 = Restrictions.eq("historical", false);
//        Criterion rest2 = Restrictions.eq("removed", false);
        return findByCriteria(rest1);
    }

    @Override // todo
    @Transactional
    public void removeNode(T node) throws IllegalArgumentException {
        throw new IllegalArgumentException("Object removed in HistoryNode without generated object not possible");
        //removeNode((HistoryNode)node,getById(new Long(2)));
    }

    @Transactional
    public void removeNode(T node, Node generatingNode) {
        node.setRemoved(true);
        saveOrUpdateNode(node, generatingNode);
    }

    @Override
    @Transactional
    public T saveOrUpdateNode(T node, Node generatingNode) {
        if ( ! Hibernate.isInitialized(node.getNodeProperties()) ){
            throw new IllegalArgumentException("объект предварительно должен быть полностью загружен (через метод - loadFullObject),для корректного обновления!");
        }
        // todo доделать чтоб когда восстанавливать дынне  можно былоо использовать
        if (null != node.isHistorical() && node.isHistorical()) throw new IllegalArgumentException("нельзя обновлять исторические данные");

        if (node.getHystPremParent() == null) {//если узел новый, то сохраняем его и устанавливаем его рут парент в него же самого
            node.setHystPremParent(node);
            node = super.saveOrUpdateNode(node, generatingNode);
            return node;
        }

        //если узел уже существовал, то загружаем копию узла из БД - та что была до этого
        T oldNode = (T) getById((ID) node.getId(), false);
        oldNode = loadFullObject(oldNode);
//        if (null != oldNode.isRemoved() && oldNode.isRemoved()) throw new IllegalArgumentException("нельзя обновлять удаленные данные");

        //если узел не менялся, то и смысла его пересохранять нету - просто возвращаем старый узел
        try {
            if (node.equals(oldNode)) {
                return oldNode;
            }
        } catch (LazyInitializationException e) {
            throw new RuntimeException("предварительно необходимо звгрузить сохраняемый объект полностью", e); // todo 14.08.2015 об
        }
        T newNode;
        try {
            newNode = (T) node.clone(); // обнуление всех ид объектов в методе клон каждой сущности
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        oldNode.setHistorical(true);//устанавливаем в старый узел свойство исторический
        super.saveOrUpdateNode(oldNode, generatingNode);

        newNode.setId(null);  // обнуляем, чтобы хибернейт мог сохранить сущность
        newNode.setHystParent(oldNode); // устанавливаем ссылку на предыдущее состояние объекта
        newNode = super.saveOrUpdateNode(newNode, generatingNode);

        return newNode;
    }

    @Override
    @Transactional
    public ID createNode(T node, Node generatingNode) throws IllegalArgumentException {
        return (ID) saveOrUpdateNode(node, generatingNode).getId();
    }
}
