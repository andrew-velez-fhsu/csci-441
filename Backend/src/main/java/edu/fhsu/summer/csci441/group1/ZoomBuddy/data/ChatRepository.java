package edu.fhsu.summer.csci441.group1.ZoomBuddy.data;

import edu.fhsu.summer.csci441.group1.ZoomBuddy.model.Chat;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.model.Message;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ChatRepository extends CrudRepository<Chat, Integer> {

    List<Message> findByUsersId(String uid);

    @Query("select c From Chat c Where :user Member of c.users And :reqUser Member of c.users")
    Chat findChatByUsersId(@Param("recipient") User recipient, @Param("sender") Authentication sender);

}
