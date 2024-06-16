package simplewebserver.utils;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Improve rate limiting using a sliding window algorithm.
 */
public class SlidingWindowRateLimiter {
    private int limit;
    private long windowSizeInSeconds;

    private ConcurrentHashMap<String, ConcurrentLinkedQueue<Instant>> clientRequests = new ConcurrentHashMap<>();

    public SlidingWindowRateLimiter(int limit, long windowSizeInSeconds) {
        this.limit = limit;
        this.windowSizeInSeconds = windowSizeInSeconds;
    }

    public boolean isAllowed(String clientIp) {
        Instant now = Instant.now();
        clientRequests.putIfAbsent(clientIp, new ConcurrentLinkedQueue<>());

        ConcurrentLinkedQueue<Instant> requests = clientRequests.get(clientIp);
        while (!requests.isEmpty() && requests.peek().isBefore(now.minusSeconds(windowSizeInSeconds))) {
            requests.poll();
        }

        if (requests.size() < limit) {
            requests.add(now);
            return true;
        }

        return false;
    }

}
