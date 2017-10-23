package by.imix.cms.nodedata.service.hib;

import by.imix.cms.nodedata.state.State;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import by.imix.cms.nodedata.service.StateService;

/**
 * Created by miha on 12.05.2015.
 */
@Service("stateservice")
public final class StateServiceHib<T extends State,ID extends Number> extends GenericHibernateDAO<T,ID> implements StateService<T,ID> {

    public T getByDesc(String description) {
        Criteria cr = getSession().createCriteria(getPersistentClass()).add(Restrictions.eq("simpleDescription", description));
        return  (T) cr.uniqueResult();
    }
}
