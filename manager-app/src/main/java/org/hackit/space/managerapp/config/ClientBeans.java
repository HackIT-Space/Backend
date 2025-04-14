package org.hackit.space.managerapp.config;

import org.hackit.space.managerapp.client.HackathonsRestClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientBeans {

    @Bean
    public HackathonsRestClientImpl hackathonsRestClient(
            @Value("${hackit.service.hackathons.uri:http://localhost:8081}") String hackathonsServiceBaseUrl) {
        return new HackathonsRestClientImpl(RestClient.builder()
                .baseUrl(hackathonsServiceBaseUrl)
                .build());
    }
}
