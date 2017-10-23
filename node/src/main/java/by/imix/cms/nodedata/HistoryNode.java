package by.imix.cms.nodedata;

/**
 * Created by miha on 06.05.2015.
 * Интерфейс позволяющий добавить в ваши объекты логику работы с универсальными объектами типа HistoryNode.
 * Все остальные классы, которым необходима реализация истории наследуются от него.
 */
public interface HistoryNode extends Node, Cloneable{
    HistoryNode getHystPremParent();
    void setHystPremParent(HistoryNode hystPremParent);

    HistoryNode getHystParent();
    void setHystParent(HistoryNode hystParent);

    Boolean isHistorical();
    void setHistorical(Boolean hystory);

    Boolean isRemoved();
    void setRemoved(Boolean removed);


}
