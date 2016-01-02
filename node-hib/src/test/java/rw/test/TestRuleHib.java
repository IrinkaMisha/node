package rw.test;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import rw.ktc.cms.nodedata.Node;
import rw.ktc.cms.nodedata.NodeImpl;
import rw.ktc.cms.nodedata.rules.Rule;
import rw.ktc.cms.nodedata.rules.RulePerformer;
import rw.ktc.cms.nodedata.service.NodeService;
import rw.ktc.cms.nodedata.service.RuleService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by miha on 27.04.2015.
 * Тест показывает работу с правилами через спринг и хибернет
 */
public class TestRuleHib {
    private NodeService nodeService;
    private ApplicationContext ac;
    private  RuleService rs;

    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("test-context.xml");
        TestRuleHib trh=new TestRuleHib();
        trh.testService2(ac);

    }

    private void testService2(ApplicationContext ac) {
        rs=(RuleService)ac.getBean("ruleservicehib");
        nodeService=(NodeService)ac.getBean("nodehibservice");

        JsonParser jsonParser = new JsonParser();
        JsonObject jo = (JsonObject)jsonParser.parse("{'add':[{\"s\":1,\"n\":2},{\"s\":2,\"n\":2},{\"s\":3,\"n\":2}]}");
        JsonArray jsAadd=jo.get("add").getAsJsonArray();
        for(int i=0;i<jsAadd.size();i++){
            JsonElement je=jsAadd.get(i);
            je.getAsJsonObject().get("s").getAsLong();
            je.getAsJsonObject().get("n").getAsLong();
        }


        Rule rule=(Rule) ac.getBean("ruleCreateNode");
        //rule.parseToJSON();
        Node node=nodeService.getNodeById(NodeImpl.class,2400L);  //Нод должен существовать в бд в таблице ноде
        nodeService.loadFullObject(node);

        rs.saveOrUpdateRule(rule);

        doRuleforNode(rule.getId(),node);
    }

    private void testService1(ApplicationContext ac) {
        this.ac=ac;
        nodeService=(NodeService)ac.getBean("nodehibservice");
        Rule rule=(Rule) ac.getBean("ruleCreateNode");
        Node node=nodeService.getNodeById(Node.class, new Long(2400));
        nodeService.loadFullObject(node);
//        node.setNodeProperties(Arrays.asList(new NodeProperty("discription", "testBean"), new NodeProperty("test", "test1")));
//        doRuleforNode(rule, node);
        Rule rule2=(Rule) ac.getBean("ruleEditNode");
        doRuleforNode(rule2, node);
        nodeService.saveOrUpdateNode(node, new NodeImpl());
    }

    private void doRuleforNode(Rule rule,Node node) {
        RulePerformer rp=new RulePerformer();
        rp.doRule(node,rule);
    }
    private void doRuleforNode(Long id_rule,Node node) {
        RulePerformer rp=new RulePerformer(rs);
        rp.doRule(node,id_rule);

        List<Object> list=new ArrayList<Object>();
        List<String> variable = (List<String>)(List<?>) list;
    }
}