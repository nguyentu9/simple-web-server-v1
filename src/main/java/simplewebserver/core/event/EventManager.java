package simplewebserver.core.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * // Usage
 * public class LoggingEventListener implements EventListener {
 *     @Override
 *     public void handle(Event event) {
 *         System.out.println("Event received: " + event.getType() + ", data: " + event.getData());
 *     }
 * }
 * <p>
 * EventManager eventManager = new EventManager();
 * eventManager.register("userLoggedIn", new LoggingEventListener());
 * <p>
 * // Dispatch an event
 * Map<String, Object> data = new HashMap<>();
 * data.put("userId", 123);
 * Event event = new Event("userLoggedIn", data);
 * eventManager.dispatch(event);
 * */
public class EventManager {
    private Map<String, List<EventListener>> listeners = new HashMap<>();

    public void register(String eventType, EventListener listener) {
        listeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
    }

    public void dispatch(Event event) {
        List<EventListener> eventListeners = listeners.get(event.getType());
        if (eventListeners != null) {
            for (EventListener listener : eventListeners) {
                listener.handle(event);
            }
        }
    }
}
