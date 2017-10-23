package by.imix.cms.nodedata;


import by.imix.cms.nodedata.json.ViewFlag;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: miha
 * Date: 23.10.13
 * Time: 16:47
 * To change this template use File | Settings | File Templates.
 * Узел который позволяет хранить историю изменений этого узла
 */
@Entity
@Table(name = "history_node")
@AttributeOverrides({
        @AttributeOverride(name = "id_usercreator", column = @Column(name = "id_usercreator")),
        @AttributeOverride(name = "id_usercorrector", column = @Column(name = "id_usercorrector")),
        @AttributeOverride(name = "datecreate", column = @Column(name = "datecreate")),
        @AttributeOverride(name = "datecorrect", column = @Column(name = "datecorrect"))
})
public class HistoryNodeImpl extends NodeImpl implements HistoryNode, Serializable, Cloneable {
    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private HistoryNodeImpl hystPremParent;//это ссылка на первичный созданный объект а когда объект первый - то он сам бебе
    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private HistoryNodeImpl hystParent;//идентификатор предыдущего узла ( ссылка на предыдущий объект)
    //    @Column(name = "historical",columnDefinition = "SMALLINT default 0 not null")
    @Column(nullable = false)
    @JsonView(ViewFlag.NodeHistory.class)
    private Boolean historical = false; //0 - это последний экземпляр, 1 - историческая запись - т.е. является ли данный историческим
    @Column(nullable = false)
    @JsonView(ViewFlag.NodeHistory.class)
    private Boolean removed = false; //если true узел удален

    public HistoryNodeImpl() {
    }

    public HistoryNodeImpl(Node nodechanger) {
        super(nodechanger);
//        historical=false;
    }

    public HistoryNodeImpl(Node nodechanger, HistoryNode historyNode) {
        super(nodechanger);
        this.setHistorical(true);
        this.hystParent = (HistoryNodeImpl) historyNode.getHystParent();
        this.hystPremParent = (HistoryNodeImpl) historyNode.getHystPremParent();
        historical = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        HistoryNodeImpl that = (HistoryNodeImpl) o;

        if (hystPremParent != null ? !(hystPremParent.getId() == that.hystPremParent.getId()) : that.hystPremParent != null)
            return false;
        if (hystParent != null ? ! (hystParent.getId() == that.hystParent.getId()) : that.hystParent != null) return false;
        if (historical != null ? !historical.equals(that.historical) : that.historical != null) return false;
        return !(removed != null ? !removed.equals(that.removed) : that.removed != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (hystPremParent != null ? hystPremParent.hashCode() : 0);
        result = 31 * result + (hystParent != null ? hystParent.hashCode() : 0);
        result = 31 * result + (historical != null ? historical.hashCode() : 0);
        result = 31 * result + (removed != null ? removed.hashCode() : 0);
        return result;
    }

    @Override
    public HistoryNode getHystPremParent() {
        return hystPremParent;
    }

    @Override
    public void setHystPremParent(HistoryNode hystPremParent) {
        this.hystPremParent = (HistoryNodeImpl) hystPremParent;
    }

    @Override
    public HistoryNode getHystParent() {
        return hystParent;
    }

    @Override
    public void setHystParent(HistoryNode hystParent) {
        this.hystParent = (HistoryNodeImpl) hystParent;
    }

    public Boolean isHistorical() {
        return historical;
    }

    public void setHistorical(Boolean historical) {
        this.historical = (historical == null) ? false : historical;
    }

    public Boolean isRemoved() {
        return removed;
    }

    public void setRemoved(Boolean removed) {
        this.removed = (removed == null) ? false : removed;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        HistoryNodeImpl node = (HistoryNodeImpl) super.clone();
        node.hystPremParent = hystPremParent;
        node.hystParent = hystParent;
        node.historical = new Boolean(historical);
        return node;
    }

    @Override
    public String toString() {
        return "" + getId();
    }
}