package by.imix.cms.nodedata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NodeStateReaderService {
    private static final Logger log = LoggerFactory.getLogger(NodeStateReaderService.class);

    private static NodeStateReader nodeStateReader;

    public static NodeStateReader getNodeStateReader() {
        if (nodeStateReader == null){
            log.warn("Не установлен NodeStateReader в NodeStateReaderService");
        }
        return nodeStateReader;
    }

    public static void setNodeStateReader(NodeStateReader nodeStateReader) {
        NodeStateReaderService.nodeStateReader = nodeStateReader;
    }
}
