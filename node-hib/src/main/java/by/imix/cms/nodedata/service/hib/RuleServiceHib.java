package by.imix.cms.nodedata.service.hib;

import by.imix.cms.nodedata.NodeException;
import by.imix.cms.nodedata.rules.Rule;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import by.imix.cms.nodedata.service.RuleService;

/**
 * Created by miha on 20.07.2015.
 */
@Transactional(readOnly = true)
@Service("ruleservicehib")
public class RuleServiceHib<T extends Rule,ID extends Number> extends GenericHibernateDAO<Rule, Long> implements RuleService {

    @Override
    public Rule loadRule(Long id_rule) throws NodeException {
        return getById(id_rule,false);
    }

    @Override
    @Transactional
    public Rule saveOrUpdateRule(Rule rule) {
        sessionFactory.getCurrentSession().saveOrUpdate(rule);
        return rule;
    }

    @Override
    @Transactional
    public boolean removeRule(Rule rule) {
        getSession().delete(rule);
        return true;
    }

    @Override
    @Transactional
    public boolean removeRule(Long id_rule) {
        deleteById(id_rule);
        return true;
    }
}
