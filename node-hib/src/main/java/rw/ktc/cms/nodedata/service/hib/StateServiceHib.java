package rw.ktc.cms.nodedata.service.hib;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import rw.ktc.cms.nodedata.service.StateService;
import rw.ktc.cms.nodedata.state.State;

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
