package by.imix.cms.nodedata;


import by.imix.cms.nodedata.json.ViewFlag;
import by.imix.cms.nodedata.rules.Rule;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: miha
 * Date: 23.10.13
 * Time: 16:18
 * To change this template use File | Settings | File Templates.
 * Класс реализующий основную логику работы узлов. Все остальные классы наследуются от него.
 */

@Entity
@Table(name = "node")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class NodeImpl implements Node, Serializable, Cloneable {
    @Transient
    private static final Logger log = LoggerFactory.getLogger(NodeImpl.class);
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tbl-gen")
    @TableGenerator(name = "tbl-gen",
            pkColumnName = "entity_table_name", allocationSize = 150,
            table = "generators")
    @JsonView(ViewFlag.NodeBriefly.class)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usercreator")
    @JsonIgnore
    private NodeImpl nodeCreator;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usercorrector")
    @JsonIgnore
    private NodeImpl nodeCorrector;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datecreate")
    @JsonView(ViewFlag.NodeFull.class)
    private Date dateCreate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datecorrect")
    @JsonView(ViewFlag.NodeFull.class)
    private Date dateCorrect;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_node", referencedColumnName = "id", unique = true, nullable = false)
    @JsonIgnore
    private List<NodeProperty> nodeProperties = new ArrayList<>();
    //    @OneToMany(fetch = FetchType.EAGER, cascade= CascadeType.ALL, orphanRemoval=true)
//    @JoinColumn(name="id_node", referencedColumnName="id",unique = true, nullable = false)
    @Transient
    @JsonView(ViewFlag.NodeFull.class)
    private Set<NodeState> listStates = new HashSet<>();

    @ElementCollection
    @CollectionTable(name="node_state", joinColumns=@JoinColumn(name="id_node"))
    @Column(name="stateandnode",length = 500)
    @Access(AccessType.PROPERTY)
    //Объекты хранящие состояния объекта в стринговом виде. Для меньшего объема переменных
    @JsonIgnore
    protected Set<String> listStatesStr = new HashSet();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_node", referencedColumnName = "id", unique = true, nullable = false)
    @JsonIgnore
    private List<Rule> rules = new ArrayList<Rule>(); // todo возможно его не будет 14.08.2015

    //Класс для извлечения состояния узлов из json или xml или других состояний

    public NodeImpl() {
    }

    public NodeImpl(Node nodechanger) {
        try {
            if (getId() == null) {
                nodeCreator = (NodeImpl) nodechanger;
                dateCreate = new Date();
            }
            nodeCorrector = (NodeImpl) nodechanger;
        } catch (Exception e) {
            log.info("Создание узла без указания создающего ");
            dateCreate = new Date();
        }
        dateCorrect = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public NodeImpl getNodeCreator() {
        return nodeCreator;
    }

    @Override
    public void setNodeCreator(Node nodeCreator) {
        this.nodeCreator = (NodeImpl) nodeCreator;
    }

    @Override
    public Node getNodeCorrector() {
        return nodeCorrector;
    }

    @Override
    public void setNodeCorrector(Node nodeCorrector) {
        this.nodeCorrector = (NodeImpl) nodeCorrector;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Date getDateCorrect() {
        return dateCorrect;
    }

    public void setDateCorrect(Date dateCorrect) {
        this.dateCorrect = dateCorrect;
    }

    public List<NodeProperty> getNodeProperties() {
        return nodeProperties;
    }

    public void setNodeProperties(List<NodeProperty> nodeProperties) {
        this.nodeProperties = nodeProperties;
    }

    public Set<NodeState> getListStates() {
        return listStates;
    }

    public void setListStates(Set<NodeState> listStates) {
        this.listStates = listStates;
        //репарсируем объекты из стринга
    }


    protected Set<String> getListStatesStr() {
        listStatesStr = NodeStateReaderService.getNodeStateReader().parseSetObjToString(getListStates());
        return listStatesStr;
    }

    protected void setListStatesStr(Set<String> listStatesStr) {
        listStates = NodeStateReaderService.getNodeStateReader().parseStringToListObj(listStatesStr);
        this.listStatesStr = listStatesStr;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }


    @Transient
    public List<NodeProperty> getPropertysValue(Node node, String key) {
        List<NodeProperty> lnp = new ArrayList<NodeProperty>();
        if (node.getNodeProperties().size() != 0) {
            for (NodeProperty np : node.getNodeProperties()) {
                if (np.getKeyt().equals(key)) {
                    lnp.add(np);
                }
            }
        }
        return lnp;
    }

    @Transient
    public String getOnePropertyValue(Node node, String key) {
        List<NodeProperty> lnp = getPropertysValue(node, key);
        if (null != lnp && lnp.size() > 0) {
            return lnp.get(0).getValue();
        }
        return null;
    }

    @Transient
    //метод для добавления только одного свойства с одним ключем, при наличии нескольких проперти с одинаковым ключем
    //переопределит первое свойство.
    public void addOnlyOneProperty(String key, String value) {
        NodeProperty nP = null;
        List<NodeProperty> lnp = getPropertysValue(this, key);
        if (lnp != null && lnp.size() > 0) {
            nP = getPropertysValue(this, key).get(0);
        }
        if (nP == null) {
            getNodeProperties().add(new NodeProperty(key, value));
        } else {
            nP.setValue(value);
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        NodeImpl node = (NodeImpl) super.clone();
        node.nodeCreator = nodeCreator;
        node.nodeCorrector = nodeCorrector;
        node.dateCreate= (dateCreate==null)?null:(Date)dateCreate.clone();
        node.dateCorrect=(dateCorrect==null)?null:(Date)dateCorrect.clone();
        if(nodeProperties!=null) {
            List<NodeProperty> l = new ArrayList<>();
            for (NodeProperty np : nodeProperties) {
                NodeProperty np2 = (NodeProperty) np.clone();
                l.add(np2);
            }
            node.setNodeProperties(l);
        }
        if (listStates != null) {
            for (NodeState nsd : listStates) {
                node.listStates.add((NodeState) nsd.clone());
            }
        }
        if (rules != null) { // todo rules проверить
            List<Rule> listRules = new ArrayList<>();
            for (Rule r : rules) {
                Rule newR = (Rule) r.clone();
                listRules.add(newR);
            }
            node.setRules(listRules);
        }

        return node;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NodeImpl node = (NodeImpl) o;

        if (dateCorrect != null ? !dateCorrect.equals(node.dateCorrect) : node.dateCorrect != null) return false;
        if (dateCreate != null ? !dateCreate.equals(node.dateCreate) : node.dateCreate != null) return false;
        if (id != null ? !id.equals(node.id) : node.id != null) return false;
        if (nodeCorrector != null ? nodeCorrector.getId() != node.nodeCorrector.getId() : node.nodeCorrector != null)
            return false;
        if (nodeCreator != null ? nodeCreator.getId() != node.nodeCreator.getId() : node.nodeCreator != null)
            return false;
        if (getNodeProperties().size() != node.getNodeProperties().size()) return false;
        for (NodeProperty property: node.getNodeProperties()){
            if (!isExistPropertyAndEq(property)){
                return false;
            }
        }
        if (getListStates().size() != node.getListStates().size()) return false;
        for ( NodeState state : getListStates()){
            if (!isExistNodeState(state)){
                return false;
            }
        }
        // todo Rule не сравниваем вообще 14.08.2015 miha

        return true;
    }

    private boolean isExistPropertyAndEq(NodeProperty property){
        for (NodeProperty cp: getNodeProperties()){
            if (cp.getKeyt().equals(property.getKeyt()) && cp.getValue().equals(property.getValue())){
                return true;
            }
        }
        return false;
    }

    private boolean isExistNodeState(NodeState state){
        for (NodeState ns: getListStates()){
            if (ns.getNode().getId() == state.getNode().getId() && ns.getState().getId() == state.getState().getId() ){
                return true;
            }
        }
        return false;
    }


    //    todo дописать хэш функцию!
    @Override
    public int hashCode() {
        int result = 17;
        result = 7 * result + (int) (this.id != null ? (this.id - (this.id >>> 32)) : 0);
        result = 7 * result + ((this.dateCreate != null) ? this.dateCreate.hashCode() : 0);
        result = 7 * result + ((this.getDateCorrect() != null) ? this.dateCorrect.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "" + id;
    }
}
