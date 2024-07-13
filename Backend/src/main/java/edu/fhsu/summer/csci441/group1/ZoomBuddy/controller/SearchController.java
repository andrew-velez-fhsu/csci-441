package edu.fhsu.summer.csci441.group1.ZoomBuddy.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.fhsu.summer.csci441.group1.ZoomBuddy.data.PetsRepository;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.model.Pet;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.service.ApiLayerService;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.service.IpChecker;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
public class SearchController {
    private final PetsRepository petsRepository;
    private final ApiLayerService apiLayerService;
    private static double MetersPerMile = 1609.34;

    public SearchController(PetsRepository petsRepository, ApiLayerService apiLayerService) {
        this.petsRepository = petsRepository;
        this.apiLayerService = apiLayerService;
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

        // stub, need a real search
        var foundPets = this.petsRepository.findAll();

        // TODO: redact all information except name, breed, first photo, and the first
        // 200 characters of the description
        /*
         * for (Pet pet : foundPets) {
         * if (pet == null) continue;
         * Class<?> clazz = pet.getClass();
         * Field[] fields = clazz.getDeclaredFields();
         * 
         * for (Field field : fields) {
         * field.setAccessible(true);
         * try {
         * if (!field.getName().equals("name") && !field.getName().equals("breed") &&
         * !field.getName().equals("description")) {
         * field.set(obj, null);
         * } else if (field.getName().equals("description")) {
         * String description = (String) field.get(obj);
         * if (description != null && description.length() > 200) {
         * field.set(obj, description.substring(0, 200));
         * }
         * }
         * } catch (IllegalAccessException e) {
         * e.printStackTrace();
         * }
         * }
         * }
         * 
         */

        // return found Pets
        return foundPets;
    }

}
