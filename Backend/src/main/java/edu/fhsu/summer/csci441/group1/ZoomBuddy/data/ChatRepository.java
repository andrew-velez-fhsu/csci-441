package edu.fhsu.summer.csci441.group1.ZoomBuddy.data;

import edu.fhsu.summer.csci441.group1.ZoomBuddy.model.Chat;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ChatRepository extends CrudRepository<Chat, Integer> {

    @Query("select c From Chat c Where sender = :sender and recipient = :recipient")
    List<Chat> findByParticipants(@Param("recipient") User recipient, @Param("sender") Authentication sender);

    @Query("SELECT c From Chat c WHERE sender = :user or recipient = :user")
    List<Chat> findByUser(@Param("user") User user);
}
