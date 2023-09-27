package tic4303.miniproject.server.model;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Interest {

    private String id;
    private String name;
    private String email;
    private String phone;
    private String country;
    private String gender;
    private String qualification;

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
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getQualification() {
        return qualification;
    }
    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

        public static Interest create(SqlRowSet rs) {
        Interest interest = new Interest();
        interest.setId(rs.getString("id"));
        interest.setName(rs.getString("name"));
        interest.setEmail(rs.getString("email"));
        interest.setPhone(rs.getString("phone"));
        interest.setCountry(rs.getString("country"));
        interest.setGender(rs.getString("gender"));
        interest.setQualification(rs.getString("qualification"));
        return interest;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("id", id)
            .add("name", name)
            .add("email", email)
            .add("phone", phone)
            .add("country", country)
            .add("gender", gender)
            .add("qualification", qualification)
            .build();           
    }
    
}

