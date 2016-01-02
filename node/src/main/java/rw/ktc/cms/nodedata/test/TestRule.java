package rw.ktc.cms.nodedata.test;

import rw.ktc.cms.nodedata.Node;
import rw.ktc.cms.nodedata.NodeImpl;
import rw.ktc.cms.nodedata.NodeProperty;
import rw.ktc.cms.nodedata.NodeState;
import rw.ktc.cms.nodedata.rules.MockLoadRule;
import rw.ktc.cms.nodedata.rules.Rule;
import rw.ktc.cms.nodedata.rules.RuleFlags;
import rw.ktc.cms.nodedata.rules.RulePerformer;
import rw.ktc.cms.nodedata.state.State;
import rw.ktc.cms.nodedata.state.StateCreateDefault;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: miha
 * Date: 02.05.14
 * Time: 10:39
 * To change this template use File | Settings | File Templates.
 *
 * Test show how work classes Rule, State with Node classes
 */
public class TestRule {

    public TestRule() {
        Node user1=new NodeImpl();
        user1.setNodeProperties(Arrays.asList(new NodeProperty("name", "user1")));

        Node document=new NodeImpl();
        document.setNodeProperties(Arrays.asList(new NodeProperty("name", "Order list")));

        State stShow=new StateCreateDefault();
        stShow.setDescription("User show");
        State stCorrect=new StateCreateDefault();
        stCorrect.setDescription("User can correct");
        State stDelete=new StateCreateDefault();
        stDelete.setDescription("User can delete");
        State stHistory=new StateCreateDefault();
        stHistory.setDescription("User can to show history");


        Rule ruleCreate=new Rule();
        ruleCreate.setDiscription("Rule create new document");
        ruleCreate.setRuleFlags(new RuleFlags(Arrays.asList(new NodeState(stShow, user1),new NodeState(stCorrect, user1),new NodeState(stDelete, user1)),new ArrayList<NodeState>()));

        Rule ruleChange=new Rule();
        ruleChange.setDiscription("Rule change document");
        ruleChange.setRuleFlags(new RuleFlags(Arrays.asList(new NodeState(stHistory, user1)),Arrays.asList(new NodeState(stCorrect, user1))));

        System.out.println("----- Document doesn't have state ------");
        doRuleforNode(ruleCreate, document);
        //or this code is it equally
        // doRuleforNode(1L, document);

        System.out.println("----- Document have 3 state for view, correct and delete ------");
        for(NodeState lns:document.getListStates()){
            System.out.println(lns.getState().getDescription());
        }
        doRuleforNode(ruleChange, document);
        System.out.println("----- Document have 3 state for view, delete and view history, but doesn't have for correct ------");
        for(NodeState lns:document.getListStates()){
            System.out.println(lns.getState().getDescription());
        }
    }

    public static void main(String[] args) {
        new TestRule();
    }

    private void doRuleforNode(Rule rule,Node node) {
        RulePerformer rp=new RulePerformer();
        rp.doRule(node,rule);
    }

    private void doRuleforNode(Long id_rule,Node node) {
        RulePerformer rp=new RulePerformer(new MockLoadRule());
        rp.doRule(node,id_rule);
    }
}
