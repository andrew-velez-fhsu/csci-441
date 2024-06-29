package edu.fhsu.summer.csci441.group1.ZoomBuddy.data;

import edu.fhsu.summer.csci441.group1.ZoomBuddy.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsersRepository extends CrudRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.uid = :uid")
    User findByFirebaseUid(@Param("uid") String uid);
}
