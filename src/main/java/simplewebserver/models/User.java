package simplewebserver.models;

import java.util.HashMap;
import java.util.Map;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private Map<String, String> links = new HashMap<>();


    public User() {
    }

    public User(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;

        links.put("self", "/users/" + id);
        links.put("friends", "/users/" + id + "/friends");
    }

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
