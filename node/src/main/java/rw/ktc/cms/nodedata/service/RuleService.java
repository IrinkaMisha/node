package rw.ktc.cms.nodedata.service;

import rw.ktc.cms.nodedata.NodeException;
import rw.ktc.cms.nodedata.rules.Rule;

/**
 * Created by miha on 20.07.2015.
 * Класс сервис предназначен для совершения CRUD операций над Rule-ем
 */
public interface RuleService {
    Rule loadRule(Long id_rule) throws NodeException;
    Rule saveOrUpdateRule(Rule rule);
    boolean removeRule(Rule rule);
    boolean removeRule(Long id_rule);
}
