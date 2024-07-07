package edu.fhsu.summer.csci441.group1.ZoomBuddy.data;


import edu.fhsu.summer.csci441.group1.ZoomBuddy.model.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MessagesRepository  extends CrudRepository<Message, Integer>{

    @Query("SELECT m FROM Message m WHERE m.recipientUid = :uid and m.status = 'unread' ")
    Iterable<Message> getAllUnreadMessages(@Param("uid") int uid);

    @Query("Select m From Message m WHERE m.threadId = :threadId")
    Iterable<Message> getMessagesByThreadId(@Param("threadId") int threadId);

}
