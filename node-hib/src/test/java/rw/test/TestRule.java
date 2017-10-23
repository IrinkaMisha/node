package rw.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import by.imix.cms.nodedata.Node;
import by.imix.cms.nodedata.NodeImpl;
import by.imix.cms.nodedata.NodeProperty;
import by.imix.cms.nodedata.rules.Rule;
import by.imix.cms.nodedata.rules.RulePerformer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Created by dima on 21.11.2014.
 * Тест показывает работу с правилами через спринг
 */
public class TestRule {

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ApplicationContext ac = new ClassPathXmlApplicationContext("test-context.xml");
        testService1(ac);
        Rule rule=(Rule) ac.getBean("ruleCreateNode");
        Node node=new NodeImpl();
        node.setNodeProperties(Arrays.asList(new NodeProperty("discription","testBean"),new NodeProperty("test","test1")));
        doRuleforNode(rule, node);
        Rule rule2=(Rule) ac.getBean("ruleEditNode");
        doRuleforNode(rule2, node);
    }

    private static void doRuleforNode(Rule rule,Node node) {
        RulePerformer rp=new RulePerformer();
        rp.doRule(node,rule);
        System.out.print(rp);
    }

    private static void testService1(ApplicationContext ac) {

    }
}
