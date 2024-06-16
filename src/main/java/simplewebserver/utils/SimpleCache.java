package simplewebserver.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Implement caching mechanisms to improve performance.
 * */
public class SimpleCache {
    private final Map<String, String> cache = new HashMap<>();

    public void put(String key, String value) {
        cache.put(key, value);
    }

    public String get(String key) {
        return cache.get(key);
    }
}
