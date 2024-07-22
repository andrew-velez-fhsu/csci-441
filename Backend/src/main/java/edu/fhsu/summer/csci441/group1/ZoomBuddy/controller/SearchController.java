package edu.fhsu.summer.csci441.group1.ZoomBuddy.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.fhsu.summer.csci441.group1.ZoomBuddy.data.PetsRepository;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.data.UsersRepository;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.model.Pet;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.service.ApiLayerService;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.service.IpChecker;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;

@RestController
@CrossOrigin
public class SearchController {
    private final PetsRepository petsRepository;
    private final ApiLayerService apiLayerService;
    private final UsersRepository usersRepository;
    private static double MetersPerMile = 1609.34;

    public SearchController(PetsRepository petsRepository, ApiLayerService apiLayerService,
            UsersRepository usersRepository) {
        this.petsRepository = petsRepository;
        this.apiLayerService = apiLayerService;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/search")
    public Iterable<Pet> findPets(
            @RequestParam(value = "lat", required = false) Double latitude,
            @RequestParam(value = "lon", required = false) Double longitude,
            @RequestParam(value = "radius", required = false, defaultValue = "5.0") Double radiusInMiles,
            HttpServletRequest request) {

        if (latitude == null || longitude == null) {
            // case for development system. Might consider putting test location into
            // settings
            if (IpChecker.isLocalhost(request)) {
                latitude = 41.161030;
                longitude = -73.861191;
            } else {
                // Get the IP address from the request
                String ipAddress = request.getRemoteAddr();

                var location = apiLayerService.getLocationByIp(ipAddress);

                // Implement logic to convert IP address to geolocation if required
                // Use a third-party geolocation service to get lat and lon from IP
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        }

        var radiusInMeters = radiusInMiles * MetersPerMile;

        // get all users in search radius
        var users = usersRepository.GetUsersByDistanceFromLocation(radiusInMeters, latitude, longitude);

        // get all users' pets
        var foundPets = new ArrayList<Pet>();
        for (var user : users) {
            var userPets = petsRepository.findAllPetsByUser(user.getUid());
            for (var userPet : userPets)
                foundPets.add(userPet);
        }

        // TODO: redact all information except name, breed, first photo, and the first
        // 200 characters of the description

        // return found Pets
        return foundPets;
    }

}
