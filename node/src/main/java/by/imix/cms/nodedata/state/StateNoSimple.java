package by.imix.cms.nodedata.state;

import by.imix.cms.nodedata.Node;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: miha
 * Date: 01.11.13
 * Time: 14:00
 * To change this template use File | Settings | File Templates.
 */
@Entity
@DiscriminatorValue(value = "NOSIMPLE")
public class StateNoSimple extends StateDefault implements State, Serializable {

    public void startAddStateEvent(Node node) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void startRemoveStateEvent(Node node) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}

