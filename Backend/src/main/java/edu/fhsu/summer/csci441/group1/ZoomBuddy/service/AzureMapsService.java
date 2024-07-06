package edu.fhsu.summer.csci441.group1.ZoomBuddy.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import edu.fhsu.summer.csci441.group1.ZoomBuddy.model.User;

@Service
public class AzureMapsService {
    @Value("${azure.maps.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public AzureMapsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GeocodeResponse getGeocoding(User user) {
        var params = buildQuery(user);
        String url = UriComponentsBuilder.fromHttpUrl("https://atlas.microsoft.com/geocode")
                .queryParam("subscription-key", apiKey)
                .queryParam("api-version", "2023-06-01")
                .queryParams(params)
                .toUriString();

        return restTemplate.getForObject(url, GeocodeResponse.class);
    }

    private MultiValueMap<String, String> buildQuery(User user) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        if (user.getAddress1() != null) {
            params.add("addressLine", user.getAddress1());
        }
        if (user.getCity() != null) {
            params.add("locality", user.getCity());
        }
        if (user.getState() != null) {
            params.add("adminDistrict", user.getState());
        }
        if (user.getPostalCode() != null) {
            params.add("postalCode", user.getPostalCode());
        }

        params.add("countryRegion", "US");

        return params;
    }
}
