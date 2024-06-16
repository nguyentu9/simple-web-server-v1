package simplewebserver.filters;

import simplewebserver.core.FilterChain;
import simplewebserver.core.HttpServletRequest;
import simplewebserver.core.HttpServletResponse;

/**
 * Implement OAuth2 authentication for your web server.
 * */
public class OAuth2Filter implements Filter {
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws Exception {
        String token = request.getHeader("Authorization");
        if (isValidToken(token)) {
            chain.doFilter(request, response);
        } else {
            response.sendStatus(403);
            response.sendBody("Unauthorized");
        }
    }

    private boolean isValidToken(String token) {
        // Validate the token with the OAuth2 provider
        return token != null && token.startsWith("Bearer ");
    }
}

