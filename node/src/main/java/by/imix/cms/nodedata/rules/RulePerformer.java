package by.imix.cms.nodedata.rules;

import by.imix.cms.nodedata.Node;
import by.imix.cms.nodedata.NodeException;
import by.imix.cms.nodedata.NodeState;
import by.imix.cms.nodedata.service.RuleService;

/**
 * Created with IntelliJ IDEA.
 * User: miha
 * Date: 13.11.13
 * Time: 14:44
 * To change this template use File | Settings | File Templates.
 * Класс для выполнения правил.
 */

public class RulePerformer implements RuleIface<Long>{
    private RuleService ruleService;
    public RulePerformer(){}
    public RulePerformer(RuleService ruleService) {
        this.ruleService = ruleService;
    }

    /**
     *
     * @param node - узел который запрашивает выполнение правила
     * @param id_rule - id правила которое нужно выполнить
     */
    public void doRule(Node node, Long id_rule) throws NodeException {
        //загружаем Правило (Rule) для конкретного узла (Node).
        //если такого правила для узла нету ничего не делаем

        //если правило есть, то по имени состояния загружаем соответствующий класс и вызываем у него метод startAddStateEvent либо startRemoveStateEvent в зависимости от правила
        Rule r= null;
        try {
            r = ruleService.loadRule(id_rule);
        } catch (NodeException e) {
            throw e;
        }
        doRule(node,r);
    }

    /**
     *
     * @param node - узел который запрашивает выполнение правила
     * @param rule - правило которое нужно выполнить
     */
    public void doRule(Node node, Rule rule){
        //загружаем Правило (Rule) для конкретного узла (Node).
        //если такого правила для узла нету ничего не делаем

        //если правило есть, то по имени состояния загружаем соответствующий класс и вызываем у него метод startAddStateEvent либо startRemoveStateEvent в зависимости от правила
//        Rule r=loadRule(1);
        for(NodeState nsd:rule.getRuleFlags().getAdd()){
            nsd.getState().startAddStateEvent(node);
            node.getListStates().add(nsd);
        }
        for(NodeState nsd:rule.getRuleFlags().getRem()){
            nsd.getState().startRemoveStateEvent(node);
            node.getListStates().remove(nsd);
        }
        //todo проверить нужно ли это.
//        nodeStateReader.parseObjToListStr(node);
    }
}