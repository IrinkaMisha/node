package rw.ktc.cms.nodedata.rules;

import com.google.gson.*;
import rw.ktc.cms.nodedata.Node;
import rw.ktc.cms.nodedata.NodeState;
import rw.ktc.cms.nodedata.service.NodeService;
import rw.ktc.cms.nodedata.service.StateService;
import rw.ktc.cms.nodedata.state.State;

/**
 * Created by miha on 21.07.2015.
 */
public class RuleJSONdecompiler implements IRuleJSONDecompiler{
    private StateService stateService;
    private NodeService nodeService;

    public RuleJSONdecompiler(StateService stateService, NodeService nodeService) {
        this.stateService=stateService;
        this.nodeService=nodeService;
    }

    public String parseToJSON(Rule rule) {
        if(rule.getRuleFlags().getAdd().size()==0 && rule.getRuleFlags().getRem().size()==0) return null;
        StringBuilder sb=new StringBuilder();
        sb.append("{");
        if(rule.getRuleFlags().getAdd().size()>0){
            sb.append("'add':[");
            for(NodeState nsd:rule.getRuleFlags().getAdd()){
                sb.append(nsd.parseToObj()).append(",");
            }
            sb.delete(sb.length() - 1, sb.length()).append("]");
            if(rule.getRuleFlags().getRem().size()>0){
                sb.append(", ");
            }
        }
        if(rule.getRuleFlags().getRem().size()>0){
            sb.append("'rem':[");
            for(NodeState nsd:rule.getRuleFlags().getRem()){
                sb.append(nsd.parseToObj()).append(",");
            }
            sb.delete(sb.length() - 1, sb.length()).append("]");
        }
        sb.append("}");
        rule.setFullPr(sb.toString());
        return sb.toString();
    }

    public Rule reparseRuleFromJSON(String json,Rule rule){
        JsonParser jsonParser = new JsonParser();
        JsonObject jo = (JsonObject)jsonParser.parse(rule.getFullPr());
        JsonArray jsAadd=jo.get("add").getAsJsonArray();
        RuleFlags rf=new RuleFlags();
        for(int i=0;i<jsAadd.size();i++){
            JsonElement je=jsAadd.get(i);
            Long id_s=je.getAsJsonObject().get("s").getAsLong();
            Long id_n=je.getAsJsonObject().get("n").getAsLong();

            State sm= (State) stateService.getById(id_s,false);
            Node n=nodeService.getFullNodeById(id_n);

            rf.getAdd().add(new NodeState(sm,n));
        }
        JsonArray jsArem=jo.get("rem").getAsJsonArray();
        for(int i=0;i<jsArem.size();i++){
            JsonElement je=jsArem.get(i);
            Long id_s=je.getAsJsonObject().get("s").getAsLong();
            Long id_n=je.getAsJsonObject().get("n").getAsLong();

            State sm= (State) stateService.getById(id_s,false);
            Node n=nodeService.getFullNodeById(id_n);

            rf.getRem().add(new NodeState(sm,n));
        }
        rule.setRuleFlags(rf);
        return rule;
    }
}
