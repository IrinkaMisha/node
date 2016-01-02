package rw.ktc.cms.nodedata;

import java.util.List;
import java.util.Set;

/**
 * Created by miha on 27.04.2015.
 */
public interface NodeStateReader {
    Set<String> parseSetObjToString(Set<NodeState> nodeStates);
    Set<NodeState> parseStringToListObj(Set<String> listJson);
}
