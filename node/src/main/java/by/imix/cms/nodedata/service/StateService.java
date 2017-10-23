package by.imix.cms.nodedata.service;

import by.imix.cms.nodedata.state.State;

/**
 * Created by miha on 12.05.2015.
 */
public interface StateService<T extends State, ID extends Number> extends GenericDAO<T, ID>{
    T getByDesc(String description);
}
