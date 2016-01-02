package rw.ktc.cms.nodedata.rules;

import rw.ktc.cms.nodedata.*;
import rw.ktc.cms.nodedata.service.NodeService;
import rw.ktc.cms.nodedata.service.RuleService;
import rw.ktc.cms.nodedata.state.State;
import rw.ktc.cms.nodedata.state.StateCreateDefault;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by miha on 20.07.2015.
 * Класс переопределяющий загрузчик правил - Все загрузчики можно реализовать имплементируя RuleLoader
 */
public class MockLoadRule implements RuleService {
    private NodeService nodeService;
//    private NodeStateReader nodeStateReader=new NodeStateReaderJSON();
    //Класс показывающий как можно загрузить правило, если нужно использовать БД - то здесь будет код загружающий
    //правила из БД
    public Rule loadRule(Long id_rule) throws NodeException{
        if(!id_rule.equals(1L)) throw new NodeException("Rule from this id_rule no exists");

        State stShow=new StateCreateDefault();
        stShow.setDescription("User show");
        State stCorrect=new StateCreateDefault();
        stCorrect.setDescription("User can correct");
        State stDelete=new StateCreateDefault();
        stDelete.setDescription("User can delete");
        State stHistory=new StateCreateDefault();
        stHistory.setDescription("User can to show history");

        Node user1=new NodeImpl();
        user1.setNodeProperties(Arrays.asList(new NodeProperty("name", "user1")));

        Rule r=new Rule();
        r.setDiscription("Rule create new document - это правило с ид "+id_rule);
        r.setRuleFlags(new RuleFlags(Arrays.asList(new NodeState(stShow, user1), new NodeState(stCorrect, user1), new NodeState(stDelete, user1)),new ArrayList<NodeState>()));

        return r;
    }

    @Override
    public Rule saveOrUpdateRule(Rule rule) {
        //Здесь будет сохранение или обновление правила
        return null;
    }

    @Override
    public boolean removeRule(Rule rule) {
        //Здесь будет удаление правила
        return false;
    }

    @Override
    public boolean removeRule(Long id_rule) {
        //Здесь будет удаление правила по id
        return false;
    }
}
