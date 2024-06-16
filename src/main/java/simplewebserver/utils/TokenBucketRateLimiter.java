package simplewebserver.utils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class TokenBucketRateLimiter {
    private final int maxTokens;
    private final int refillRate; // tokens per second
    private final ConcurrentHashMap<String, AtomicInteger> tokens;
    private final ConcurrentHashMap<String, AtomicLong> lastRefillTime;

    public TokenBucketRateLimiter(int maxTokens, int refillRate) {
        this.maxTokens = maxTokens;
        this.refillRate = refillRate;
        this.tokens = new ConcurrentHashMap<>();
        this.lastRefillTime = new ConcurrentHashMap<>();
    }

    public boolean isAllowed(String clientIp) {
        long currentTime = System.currentTimeMillis();
        tokens.putIfAbsent(clientIp, new AtomicInteger(maxTokens));
        lastRefillTime.putIfAbsent(clientIp, new AtomicLong(currentTime));

        refillTokens(clientIp, currentTime);

        if (tokens.get(clientIp).get() > 0) {
            tokens.get(clientIp).decrementAndGet();
            return true;
        } else {
            return false;
        }
    }

    private void refillTokens(String clientIp, long currentTime) {
        long lastRefill = lastRefillTime.get(clientIp).get();
        long timeElapsed = currentTime - lastRefill;

        int tokensToAdd = (int) (timeElapsed / 1000) * refillRate;
        if (tokensToAdd > 0) {
            tokens.get(clientIp).addAndGet(Math.min(tokensToAdd, maxTokens - tokens.get(clientIp).get()));
            lastRefillTime.get(clientIp).set(currentTime);
        }
    }
}
