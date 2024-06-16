package simplewebserver.core.event;

import java.util.Map;

public class Event {
    private String type;
    private Map<String, Object> data;

    public Event(String type, Map<String, Object> data) {
        this.type = type;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public Map<String, Object> getData() {
        return data;
    }
}
