package rw.ktc.cms.nodedata.state;

import rw.ktc.cms.nodedata.Node;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: miha
 * Date: 31.10.13
 * Time: 13:32
 * To change this template use File | Settings | File Templates.
 */
public interface State extends Serializable, Cloneable {
    void startAddStateEvent(Node node);
    void startRemoveStateEvent(Node node);
    Long getId();
    void setId(Long id);
    String getDescription();
    void setDescription(String simpleDescription);
}