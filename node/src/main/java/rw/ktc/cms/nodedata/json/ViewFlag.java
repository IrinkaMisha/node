package rw.ktc.cms.nodedata.json;

/**
 * Created by dima on 22.07.2015.
 */
public class ViewFlag {
    public interface NodeBriefly {}
    public interface NodeHistory extends NodeBriefly {}
    public interface NodeWithProperty extends NodeHistory {}
    public interface NodeFull extends NodeWithProperty {}

    public interface UserBriefly extends NodeBriefly{}
    public interface UserWithRoles extends UserBriefly,NodeWithProperty {}
    public interface UserFull extends UserWithRoles, NodeFull {}

    public interface RoleFull extends NodeFull, UserFull{}

}
