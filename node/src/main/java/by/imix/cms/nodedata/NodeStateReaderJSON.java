package by.imix.cms.nodedata;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import by.imix.cms.nodedata.service.NodeService;
import by.imix.cms.nodedata.service.StateService;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: miha
 * Date: 31.10.13
 * Time: 14:50
 * To change this template use File | Settings | File Templates.
 * Класс состояния узла
 */
public class NodeStateReaderJSON implements NodeStateReader {
    private static final Logger log = LoggerFactory.getLogger(NodeStateReaderJSON.class);

    private NodeService nodeService;

    private StateService stateService;

    public NodeStateReaderJSON() {
    }
//    public boolean addState(State state, Node node){
//        if(node.getListStates().add(new NodeState(state, node))){
//            parseObjToListStr(node);
//            return true;
//        }
//        return false;
//    }

//    public boolean removeState(State state, Node node){
//        NodeState nsd=new NodeState(state,node);
//        boolean d=false;
//        for(NodeState nsdl:node.getListStates()){
//            if(nsdl.equals(nsd)){
//                d=node.getListStates().remove(nsdl);
//            }
//        }
//        if(d){
//            parseObjToListStr(node);
//        }
//        return d;
//    }

//    public void parseListStrToObj(Node node,Set<String> listStr) {
//        if(listStr!=null && listStr.size()>0) {
//            Set<NodeState> map = node.getListStates();
//            ObjectMapper mapper = new ObjectMapper();
//            StringBuilder sb = new StringBuilder();
//            for (String nsd : listStr) {
//                sb.append(nsd);
//            }
//            //12 - минимальный json в котором можно записать состояние
//            if(sb.toString().length()>12) {
//                try {
//                    JsonNode jsonN = mapper.readTree(sb.toString());
//                    for(JsonNode ojn:jsonN){
//                        map.add(new NodeStateProxy(ojn.get("s").asLong(),ojn.get("n").asLong()));
//                    }
//                } catch (IOException e) {
//                     log.error("Not get to create State objects");
//                }
//            }
//        }
//    }

//    public void parseObjToListStr(Node node) {
////        ObjectMapper mapper = new ObjectMapper();
////        try {
////            node.setListStatesStr(new HashSet<String>(Arrays.asList(mapper.writeValueAsString(node.getListStates()))));
////        } catch (JsonProcessingException e) {
////            e.printStackTrace();
////        }
//
//        StringBuilder sb=new StringBuilder();
//        sb.append("[");
//        for(NodeState nsd:node.getListStates()){
//            sb.append(nsd.parseToObj()).append(",");
//        }
//        ((NodeImpl)node).setListStatesStr(new HashSet<String>(Arrays.asList(sb.substring(0,sb.length()-1)+"]")));
//    }

    @Override
    public Set<String> parseSetObjToString(Set<NodeState> nodeStates) {
        StringBuilder sb=new StringBuilder();
        sb.append("[");
        for(NodeState nsd:nodeStates){
            sb.append(nsd.parseToObj()).append(",");
        }
        if (nodeStates.size() > 0) {
            return new HashSet<String>(Arrays.asList(sb.substring(0,sb.length()-1)+"]"));
        }
        return new HashSet();
    }

    @Override
    public Set<NodeState> parseStringToListObj(Set<String> listJson) {

        if(listJson!=null && listJson.size()>0) {
            Set<NodeState> map = new HashSet<>();
            ObjectMapper mapper = new ObjectMapper();
            StringBuilder sb = new StringBuilder();
            for (String nsd : listJson) {
                sb.append(nsd);
            }
            //12 - минимальный json в котором можно записать состояние
            if(sb.toString().length()>12) {
                try {
                    JsonNode jsonN = mapper.readTree(sb.toString());
                    for(JsonNode ojn:jsonN){
                        map.add(new NodeStateProxy(ojn.get("s").asLong(),ojn.get("n").asLong(), getNodeService(),getStateService() ) );
                    }
                    return map;
                } catch (IOException e) {
                    log.error("can't create State objects");
                }
            } else if (sb.length()>0){
                log.warn("Incorrect json notation of object NodeState");
            }
        }

        return new HashSet<>();
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
    protected Object clone() throws CloneNotSupportedException {
        NodeStateReaderJSON nodeS = (NodeStateReaderJSON) super.clone();
//        nodeS.stateAndNode=stateAndNode;
//        for(NodeState nsd:listStates){
//            nodeS.listStates.add((NodeState)nsd.clone());
//        }
        return nodeS;
    }
}
