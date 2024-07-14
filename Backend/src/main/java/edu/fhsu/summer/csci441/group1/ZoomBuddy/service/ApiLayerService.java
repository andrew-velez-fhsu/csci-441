package edu.fhsu.summer.csci441.group1.ZoomBuddy.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import edu.fhsu.summer.csci441.group1.ZoomBuddy.entities.GetLocationByIpResponse;

@Service
public class ApiLayerService {
    @Value("${apilayer.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public ApiLayerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GetLocationByIpResponse getLocationByIp(String ipAddress) {

        var requestUrl = String.format("https://api.apilayer.com/ip_to_location/%s", ipAddress);
        String url = UriComponentsBuilder.fromHttpUrl(requestUrl)
                .queryParam("apikey", apiKey)
                .toUriString();

        return restTemplate.getForObject(url, GetLocationByIpResponse.class);
    }

}
