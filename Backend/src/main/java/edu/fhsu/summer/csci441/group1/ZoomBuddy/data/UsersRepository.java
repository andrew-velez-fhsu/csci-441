package edu.fhsu.summer.csci441.group1.ZoomBuddy.data;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.fhsu.summer.csci441.group1.ZoomBuddy.model.User;

public interface UsersRepository extends CrudRepository<User, String> {

    @Query(value = "SELECT * FROM users WHERE ST_DWithin(location, ST_MakePoint(:longitude, :latitude), :radius)", nativeQuery = true)
    public List<User> GetUsersByDistanceFromLocation(@Param("radius") Double radius, @Param("latitude") Double latitude,
            @Param("longitude") Double longitude);
}
