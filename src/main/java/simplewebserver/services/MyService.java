package simplewebserver.services;

import javax.inject.Singleton;

@Singleton
public class MyService {

    public String getGreeting() {
        return "Hello, Dependency Injection!";
    }
}
