package by.imix.cms.nodedata.service.hib;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.LockOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import by.imix.cms.nodedata.Node;
import by.imix.cms.nodedata.NodeProperty;
import by.imix.cms.nodedata.service.NodeService;
import by.imix.cms.nodedata.state.State;
import by.imix.cms.nodedata.state.StateDefault;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by miha on 07.05.2015.
 */
@Transactional(readOnly = true)
public class NodeServiceGeneric<T extends Node, ID extends Number> extends GenericHibernateDAO<T,ID> implements NodeService<T,ID> {
    private static final Logger log = LoggerFactory.getLogger(NodeServiceGeneric.class);

//    @Autowired
//    public NodeServiceGeneric(SessionFactory sessionFactory) {
//        super(sessionFactory);
//    }

    public List<NodeProperty> getPropertysValue(T node, String key) {
        List<NodeProperty> lnp = new ArrayList<NodeProperty>();
        if (!Hibernate.isInitialized(node.getNodeProperties())) {
            try {
                getSession().buildLockRequest(LockOptions.NONE).lock(node);
                Hibernate.initialize(node.getNodeProperties());
            } catch (HibernateException e) {
                log.error(e.getMessage());
                return lnp;
            }
        }
        if (node.getNodeProperties()!=null && node.getNodeProperties().size() != 0) {
            for (NodeProperty np : node.getNodeProperties()) {
                if (np.getKeyt().equals(key)) {
                    lnp.add(np);
                }
            }
        }
        return lnp;
    }

    public NodeProperty getSinglePropertysValue(T node, String key) {
        List<NodeProperty> lnp = getPropertysValue(node,key);
        if(lnp==null || lnp.size()==0){
            log.warn("This property no found");
            return null;
        }
        if(lnp.size()>1){
            log.warn("This property have multi value");
            return null;
        }
        return lnp.get(0);
    }


    public List<NodeProperty> getAllPropertys(T node) {
        getSession().refresh(node);
        if (!Hibernate.isInitialized(node.getNodeProperties())) {
            Hibernate.initialize(node.getNodeProperties());
        }
        return node.getNodeProperties();
    }

    public T getFullNodeById(ID id) {
        T node = getById(id, false);
        loadFullObject(node);
        return node;
    }

    public List<T> getAllNodeFromType(String type) {
        return getAllNodeFromType(getPersistentClass(), type);
    }

    //Метод возвращает узлы по их динамическому типу (значит что класса не существует а обеъкт может быть)
    public List<T> getAllNodeFromType(Class clazz, String type) {
        String sql = "SELECT n FROM " + clazz.getName() + " n LEFT JOIN n.nodeProperties np WHERE np.keyt='type' AND np.value=:type";
        return getSession().createQuery(sql).setString("type", type).list();
    }

    public State loadState(Long idState) {
        return (State) getSession().get(StateDefault.class, idState, LockOptions.NONE);
    }

    public List<T> getAllNodeFromPrKey(String key, String value) {
        return getSession().createQuery("SELECT n FROM " + getPersistentClass().getName() + " n LEFT JOIN n.nodeProperties np WHERE np.keyt=:key AND np.value=:value").setString("key", key).setString("value", value).list();
    }

    public List<Node> getAllNodeFromPrKey(Class clazz, String key, String value) {
        return getSession().createQuery("SELECT n FROM " + clazz.getName() + " n LEFT JOIN n.nodeProperties np WHERE np.keyt=:key AND np.value=:value").setString("key", key).setString("value", value).list();
    }

    public List<State> getStates(T node) {
        try {
            return getSession().createCriteria(State.class).list();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public void removeNode(T node) throws IllegalArgumentException {
        getSession().delete(node);
    }
    
    @Transactional
    public T saveOrUpdateNode(T node, Node generatingNode) throws IllegalArgumentException {
        if (generatingNode == null) throw new IllegalArgumentException("не указан параметр:generatingNode");
        if (generatingNode == node)
            throw new IllegalArgumentException("сохраняемый узел не может быть указан в качестве родителя: generatingNode will not be eq node");
        if (node.getId() == null && node.getNodeCreator() == null) {
            node.setNodeCreator(generatingNode);
            node.setDateCreate(new Date());
        } else {
            node.setNodeCorrector(generatingNode);
            node.setDateCorrect(new Date());
        }
        getSession().saveOrUpdate(node);

        return node;
    }

    public ID createNode(T node, Node generatingNode) throws IllegalArgumentException {
        if (generatingNode == null) throw new IllegalArgumentException("не указан параметр:generatingNode");
        if (generatingNode == node)
            throw new IllegalArgumentException("сохраняемый узел не может быть указан в качестве родителя: generatingNode will not be eq node");
        node.setNodeCreator(generatingNode);
        node.setDateCreate(new Date());
        ID id = (ID) getSession().save(node);
        getSession().flush();
        return id;
    }

    @Deprecated
    public Node getNodeById(Class clazz, ID id) {
        return (Node) getSession().get(clazz, id);
    }

//    public T getById(ID id) {
//        T t = (T) getSession().get(getPersistentClass(), id);
//        return t;
//    }

//    public T getFullNodeById(ID id) {
//        T t = getById(id, false);
//        if (null != t) {
//            return loadFullObjectHib(t);
//        }
//        return t;
//    }

    public T loadFullObject(T node) {
        if (null == node) throw new IllegalArgumentException("node не может равен null , для выполнения загрузки");
        getSession().refresh(node);
        return loadFullObjectHib(node);
    }

    private T loadFullObjectHib(T node) {
        Hibernate.initialize(node.getNodeCreator());
        Hibernate.initialize(node.getNodeCorrector());
        Hibernate.initialize(node.getNodeProperties());
        Hibernate.initialize(node.getRules());
        return node;
    }

//    public List<T> getAll() {
//        return getSession().createCriteria(getPersistentClass()).list();
//    }

    public T replaceProperties(T node, String nameProperty, List<String> values) {
        List<NodeProperty> props = node.getNodeProperties();
        List<NodeProperty> removeProps = new ArrayList<>();
        for (NodeProperty prop : props) {
            if (prop.getKeyt().equals(nameProperty)) {
                removeProps.add(prop);
            }
        }
        props.removeAll(removeProps);
        addProperties(node, nameProperty, values);
        return node;
    }

    public T addProperties(T node, String nameProperty, List<String> values) {
        List<NodeProperty> props = node.getNodeProperties();
        for (String value : values) {
            props.add(new NodeProperty(nameProperty, value));
        }
        return node;
    }
}