package edu.fhsu.summer.csci441.group1.ZoomBuddy.data;

import edu.fhsu.summer.csci441.group1.ZoomBuddy.model.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessagesRepository extends CrudRepository<Message, Integer> {

}
