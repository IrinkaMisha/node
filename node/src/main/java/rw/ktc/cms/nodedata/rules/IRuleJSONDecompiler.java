package rw.ktc.cms.nodedata.rules;

/**
 * Created by miha on 21.07.2015.
 */
public interface IRuleJSONDecompiler {
    Rule reparseRuleFromJSON(String json,Rule rule);
    String parseToJSON(Rule rule);
}
