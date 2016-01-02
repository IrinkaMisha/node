package rw.ktc.cms.nodedata.state;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: miha
 * Date: 01.11.13
 * Time: 13:54
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "state")
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="classname",discriminatorType = DiscriminatorType.STRING, length=30)
public abstract class StateDefault implements State {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(name = "descr")
    private String description;  //описание состояния

    public StateDefault() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        StateDefault stD=(StateDefault) super.clone();
        stD.description = description;
        return stD;
    }
}
