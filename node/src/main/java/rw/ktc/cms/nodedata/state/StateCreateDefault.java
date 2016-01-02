package rw.ktc.cms.nodedata.state;

import rw.ktc.cms.nodedata.Node;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: miha
 * Date: 13.11.13
 * Time: 14:28
 * To change this template use File | Settings | File Templates.
 */
public class StateCreateDefault extends StateDefault implements State, Serializable {

    public void startAddStateEvent(Node node) {
//        node.getListStates().add(this);
//        System.out.println("With node "+node+" add State"+this);
    }

    public void startRemoveStateEvent(Node node) {
//        System.out.println("In node "+node+" remove State"+this);
    }
}
