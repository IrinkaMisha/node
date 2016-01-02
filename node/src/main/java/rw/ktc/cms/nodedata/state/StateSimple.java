package rw.ktc.cms.nodedata.state;

import rw.ktc.cms.nodedata.Node;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;


/**
 * Created with IntelliJ IDEA.
 * User: miha
 * Date: 01.11.13
 * Time: 10:44
 * To change this template use File | Settings | File Templates.
 */
@Entity
@DiscriminatorValue(value = "SIMPLE")
public class StateSimple extends StateDefault implements State, Serializable {
    public StateSimple() {
    }

    public StateSimple(String discription) {
        setDescription(discription);
    }

    public void startAddStateEvent(Node node) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void startRemoveStateEvent(Node node) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
