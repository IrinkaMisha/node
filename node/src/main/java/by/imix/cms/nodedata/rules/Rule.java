package by.imix.cms.nodedata.rules;

import by.imix.cms.nodedata.Node;
import by.imix.cms.nodedata.NodeState;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: miha
 * Date: 31.10.13
 * Time: 17:01
 * To change this template use File | Settings | File Templates.
 * Этот класс на основе которого можно изменять документ, управляя состояниями
 */
@Entity
@Table(name = "rule")
public class Rule implements Serializable,Cloneable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String discription;
    private String fullPr;
    @Transient
    private RuleFlags ruleFlags;
    @Transient
    private IRuleJSONDecompiler ruleJSONdecompiler;

    public Rule() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        ruleJSONdecompiler.parseToJSON(this);
        return k;
    }

    public boolean addRemovedState(NodeState stN){
        boolean k=getRuleFlags().getRem().add(stN);
        ruleJSONdecompiler.parseToJSON(this);
        return k;
    }

    public boolean delAddedState(NodeState stN){
        boolean k=getRuleFlags().getAdd().remove(stN);
        ruleJSONdecompiler.parseToJSON(this);
        return k;
    }

    public boolean delRemovedState(NodeState stN){
        boolean k=getRuleFlags().getRem().add(stN);
        ruleJSONdecompiler.parseToJSON(this);
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
        Rule rule = (Rule) super.clone();
        rule.discription = discription;
        rule.fullPr = fullPr;
        rule.ruleFlags=(RuleFlags)ruleFlags.clone();
        return rule;
    }
}
