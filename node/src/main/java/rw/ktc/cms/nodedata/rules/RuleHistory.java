package rw.ktc.cms.nodedata.rules;

/**
 * Created by miha on 20.07.2015.
 */

import com.google.gson.Gson;
import rw.ktc.cms.nodedata.HistoryNodeImpl;
import rw.ktc.cms.nodedata.Node;
import rw.ktc.cms.nodedata.NodeState;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: miha
 * Date: 31.10.13
 * Time: 17:01
 * To change this template use File | Settings | File Templates.
 * Этот класс на основе которого можно изменять документ, управляя состояниями тоже самое что и Rule только с использо-
 * ванием историчность и всем плюшек нодахистори
 */
//You will uncomment this code you are getting rules of history and all possible link from this
//@Entity
//@Table(name = "ruleH")
//@AttributeOverrides({
//        @AttributeOverride(name="id_usercreator", column=@Column(name="id_usercreator")),
//        @AttributeOverride(name="id_usercorrector", column=@Column(name="id_usercorrector")),
//        @AttributeOverride(name="datecreate", column=@Column(name="datecreate")),
//        @AttributeOverride(name="datecorrect", column=@Column(name="datecorrect")),
//        @AttributeOverride(name="id_hystPremParent", column=@Column(name="id_hystPremParent")),
//        @AttributeOverride(name="id_hystParent", column=@Column(name="id_hystParent")),
//        @AttributeOverride(name="hystory", column=@Column(name="hystory"))
//})
public class RuleHistory extends HistoryNodeImpl implements Serializable,Cloneable {
    private String discription;
    private String fullPr;
    @Transient
    private RuleFlags ruleFlags;
    @Transient
    private Gson gson = new Gson();

    public RuleHistory() {
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getFullPr() {
        return fullPr;
    }

    public void setFullPr(String fullPr) {
        this.fullPr = fullPr;
    }

    public boolean addAddedState(NodeState stN){
        boolean k=getRuleFlags().getAdd().add(stN);
        parseToJSON();
        return k;
    }

    private void parseToJSON() {
        fullPr=gson.toJson(ruleFlags);
    }

    public boolean addRemovedState(NodeState stN){
        boolean k=getRuleFlags().getRem().add(stN);
        parseToJSON();
        return k;
    }

    public boolean delAddedState(NodeState stN){
        boolean k=getRuleFlags().getAdd().remove(stN);
        parseToJSON();
        return k;
    }

    public boolean delRemovedState(NodeState stN){
        boolean k=getRuleFlags().getRem().add(stN);
        parseToJSON();
        return k;
    }

    public RuleFlags getRuleFlags() {
        return ruleFlags;
    }

    public void setRuleFlags(RuleFlags ruleFlags) {
        this.ruleFlags = ruleFlags;
    }

    /**
     *
     * @param node - узел который запрашивает выполнение правила
     */
    public void rulePerform(Node node){
        //загружаем Правило (Rule) для конкретного узла (Node).
        //если такого правила для узла нету ничего не делаем

        //если правило есть, то по имени состояния загружаем соответствующий класс и вызываем у него метод startAddStateEvent либо startRemoveStateEvent в зависимости от правила

        for(NodeState nsd:getRuleFlags().getAdd()){
            nsd.getState().startAddStateEvent(nsd.getNode());
        }
        for(NodeState nsd:getRuleFlags().getRem()){
            nsd.getState().startRemoveStateEvent(nsd.getNode());
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        RuleHistory rule = (RuleHistory) super.clone();
        rule.discription = discription;
        rule.fullPr = fullPr;
        rule.ruleFlags=(RuleFlags)ruleFlags.clone();
        return rule;
    }
}

