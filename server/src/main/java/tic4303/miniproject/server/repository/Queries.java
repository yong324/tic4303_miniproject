package tic4303.miniproject.server.repository;

public class Queries {
    
    public static String SQL_SUBMIT_FORM = "INSERT INTO interests (name, email, phone, country, gender, qualification) VALUES (?, ?, ?, ?, ?, ?)";

    public static final String SQL_SELECT_INTERESTS = "SELECT id, name, email, phone, country, gender, qualification FROM interests";

}