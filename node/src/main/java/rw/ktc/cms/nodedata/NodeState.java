package rw.ktc.cms.nodedata;

import rw.ktc.cms.nodedata.state.State;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: miha
 * Date: 31.10.13
 * Time: 15:14
 * To change this template use File | Settings | File Templates.
 * Класс для установки состояния (Наделяет узел node правами взаимодействия с текущим узлом- текущий узел, тот узел у которого есть этот nodeState)
 */
public class NodeState implements Serializable,Cloneable{
    private State state;
    private Node node;

    public NodeState(State state, Node node) {
        this.state = state;
        this.node = node;
    }

    public State getState() {
        return state;
    }

    protected void setState(State state) {
        this.state = state;
    }

    public Node getNode() {
        return node;
    }

    protected void setNode(Node node) {
        this.node = node;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NodeState that = (NodeState) o;

        if (node != null ? !node.equals(that.node) : that.node != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = state != null ? state.hashCode() : 0;
        result = 31 * result + (node != null ? node.hashCode() : 0);
        return result;
    }

    public String parseToObj() {
        StringBuilder sb = new StringBuilder("{\"s\":");
        sb.append(getState().getId());
        sb.append(",\"n\":");
        sb.append(getNode());
        sb.append("}");
        return sb.toString();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        NodeState nodeSD = (NodeState) super.clone();
        nodeSD.state=state;
        nodeSD.node=node;
        return nodeSD;
    }
}
