package rw.ktc.cms.nodedata.rules;

import rw.ktc.cms.nodedata.NodeState;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: miha
 * Date: 02.05.14
 * Time: 9:45
 * To change this template use File | Settings | File Templates.
 */
public class RuleFlags implements Serializable,Cloneable{
    private List<NodeState> add= new ArrayList<NodeState>();
    private List<NodeState> rem= new ArrayList<NodeState>();

    public RuleFlags() {
    }

    public RuleFlags(List<NodeState> add, List<NodeState> rem) {
        this.add = add;
        this.rem = rem;
    }

    public List<NodeState> getAdd() {
        return add;
    }

    public void setAdd(List<NodeState> add) {
        this.add = add;
    }

    public List<NodeState> getRem() {
        return rem;
    }

    public void setRem(List<NodeState> rem) {
        this.rem = rem;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        RuleFlags rf=(RuleFlags) super.clone();

        for(NodeState nsd:add){
            rf.add.add((NodeState)nsd.clone());
        }

        for(NodeState nsd:rem){
            rf.rem.add((NodeState)nsd.clone());
        }

        return rf;
    }
}
