package rw.ktc.cms.nodedata;

import rw.ktc.cms.nodedata.rules.Rule;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: miha
 * Date: 28.10.13
 * Time: 13:53
 * To change this template use File | Settings | File Templates.
 */
// Интерфейс позволяющий добавить в ваши объекты логику работы с универсальными объектами типа Node. Все остальные классы наследуются от него.

public interface Node extends Serializable, Cloneable{

    Long getId();
    void setId(Long id);

    Node getNodeCreator();
    void setNodeCreator(Node id_userCreator);

    Node getNodeCorrector();
    void setNodeCorrector(Node id_userCorrector);

    Date getDateCreate();
    void setDateCreate(Date dateCreate);

    Date getDateCorrect();
    void setDateCorrect(Date dateCorrect);

    List<NodeProperty> getNodeProperties();
    void setNodeProperties(List<NodeProperty> nodeProperties);

    Set<NodeState> getListStates();
    void setListStates(Set<NodeState> listStates);

//    Set<String> getListStatesStr();
//    void setListStatesStr(Set<String> listStatesStr);

    List<Rule> getRules();
    void setRules(List<Rule> rules);

    String getOnePropertyValue(Node node, String key);
    List<NodeProperty> getPropertysValue(Node node, String key);
    //метод для добавления только одного свойства с одним ключем, при наличии нескольких проперти с одинаковым ключем
    //переопределит первое свойство.
    void addOnlyOneProperty(String key,String value);

    Object clone() throws CloneNotSupportedException;
}
