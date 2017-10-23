package by.imix.cms.nodedata;

import by.imix.cms.nodedata.service.NodeService;
import by.imix.cms.nodedata.state.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import by.imix.cms.nodedata.service.StateService;

/**
 * Created by miha on 13.05.2015.
 */
public class NodeStateProxy extends NodeState{
    private static final Logger log = LoggerFactory.getLogger(NodeStateProxy.class);

    private Long id_state;
    private Long id_node;
    private NodeService nodeService;
    private StateService stateService;

    public NodeStateProxy(State state, Node node) {
        super(state, node);
    }

    public NodeStateProxy(Long id_state, Long id_node) {
        super(null, null);
        this.id_state=id_state;
        this.id_node=id_node;
    }

    public NodeStateProxy(Long id_state, Long id_node, NodeService nodeService, StateService stateService) {
        super(null, null);
        this.id_state = id_state;
        this.id_node = id_node;
        this.nodeService = nodeService;
        this.stateService = stateService;
    }

    public State getState() {
        if(super.getState()==null) {
            if(stateService!=null) {
                State lazyState = (State) stateService.getById(getId_state(), false);
                setState(lazyState);
            }else{
                log.error("Check your stateService");
                return null;
                  //                throw new NodeException("Check get stateService");
            }
        }
        return super.getState();
    }

    public Node getNode() {
        if(super.getNode()==null) {
            if(nodeService!=null) {
                Node lazyNode = (Node) nodeService.getById(getId_node(), false);
                setNode(lazyNode);
            } else{
                log.error("Check your nodeService");
                return null;
//                throw new NodeException("Check get nodeService");
            }
        }
        return super.getNode();
    }

    public Long getId_state() {
        return id_state;
    }

    public void setId_state(Long id_state) {
        this.id_state = id_state;
    }

    public Long getId_node() {
        return id_node;
    }

    public void setId_node(Long id_node) {
        this.id_node = id_node;
    }

    public NodeService getNodeService() {
        return nodeService;
    }

    public void setNodeService(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    public StateService getStateService() {
        return stateService;
    }

    public void setStateService(StateService stateService) {
        this.stateService = stateService;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        NodeStateProxy that = (NodeStateProxy) o;

        if (id_node != null ? !id_node.equals(that.id_node) : that.id_node != null) return false;
        if (id_state != null ? !id_state.equals(that.id_state) : that.id_state != null) return false;
        if (nodeService != null ? !nodeService.equals(that.nodeService) : that.nodeService != null) return false;
        if (stateService != null ? !stateService.equals(that.stateService) : that.stateService != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id_state != null ? id_state.hashCode() : 0);
        result = 31 * result + (id_node != null ? id_node.hashCode() : 0);
        result = 31 * result + (nodeService != null ? nodeService.hashCode() : 0);
        result = 31 * result + (stateService != null ? stateService.hashCode() : 0);
        return result;
    }
}
