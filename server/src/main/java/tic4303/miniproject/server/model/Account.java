package tic4303.miniproject.server.model;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Account {

    private String id;
    private String name;
    private String email;
    private String password;
    private String role;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    public static Account create(SqlRowSet rs) {
        Account account = new Account();
        account.setId(rs.getString("id"));
        account.setEmail(rs.getString("email"));
        account.setName(rs.getString("name"));
        account.setPassword(rs.getString("password"));
        account.setRole(rs.getString("role"));
        return account;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("id", id)
            .add("email", email)
            .add("name", name)
            .add("password", password)
            .add("role", role)
            .build();           
    }
    
}