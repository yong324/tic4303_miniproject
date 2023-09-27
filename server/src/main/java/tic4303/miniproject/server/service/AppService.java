package tic4303.miniproject.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import lombok.RequiredArgsConstructor;
import tic4303.miniproject.server.model.Interest;
import tic4303.miniproject.server.repository.MySqlRepository;

@Service
@EnableMethodSecurity
@RequiredArgsConstructor
public class AppService {
    
    @Autowired
    private MySqlRepository mySqlRepo;

    public void submitForm(Interest interest) {
        mySqlRepo.submitForm(interest);
    }

    public String getInterests() {

        List<Interest> interests = this.mySqlRepo.getInterests();

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();

        for (Interest i: interests)
            arrBuilder.add(i.toJson());

        JsonArray jsonArrAccounts = arrBuilder.build();

        String jsonArrAccountsStr = jsonArrAccounts.toString();

        return jsonArrAccountsStr;
    }

}
