package edu.fhsu.summer.csci441.group1.ZoomBuddy.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.springframework.stereotype.Service;

@Service

//Create a service that interfaces with Firebase to manage users
public class FirebaseUserService {

    // Firebase create user
    public UserRecord createUser(String email, String password) throws FirebaseAuthException {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(email)
                .setPassword(password);

        return FirebaseAuth.getInstance().createUser(request);
    }

    // Firebase update user
    public void updateUser(String uid, String email, String password) throws FirebaseAuthException {
        UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(uid)
                .setEmail(email)
                .setPassword(password);

        FirebaseAuth.getInstance().updateUser(request);
    }

    public void deleteUser(String uid) {
        try {
            FirebaseAuth.getInstance().deleteUser(uid);
            System.out.println("Successfully deleted user with UID: " + uid);
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            System.err.println("Error deleting user: " + e.getMessage());
        }
    }
}
