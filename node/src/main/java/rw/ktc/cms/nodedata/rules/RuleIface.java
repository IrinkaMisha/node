package rw.ktc.cms.nodedata.rules;

import rw.ktc.cms.nodedata.Node;
import rw.ktc.cms.nodedata.NodeException;

/**
 * Created with IntelliJ IDEA.
 * User: miha
 * Date: 22.01.14
 * Time: 13:49
 * To change this template use File | Settings | File Templates.
 */
public interface RuleIface<ID extends Number> {
    void doRule(Node node, ID id_rule) throws NodeException;
    void doRule(Node node, Rule rule);
}
