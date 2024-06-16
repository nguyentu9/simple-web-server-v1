package simplewebserver.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Implement rate limiting to control the number of requests a client can make.
 * */
public class RateLimiter {
    private Map<String, AtomicInteger> clientRequests = new HashMap<>();
    private int limit;
    private long window;

    public RateLimiter(int limit, long window) {
        this.limit = limit;
        this.window = window;
    }

    public synchronized boolean isAllowed(String clientIp) {
        AtomicInteger requests = clientRequests.get(clientIp);
        if(requests == null) {
            requests = new AtomicInteger(0);
            clientRequests.put(clientIp, requests);
            new Thread(() -> {
               try {
                   Thread.sleep(window);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               clientRequests.remove(clientIp);
            }).start();
        }
        return requests.incrementAndGet() <= limit;
    }




}
