import {
  browserLocalPersistence,
  browserSessionPersistence,
  createUserWithEmailAndPassword,
  getAuth,
  setPersistence,
  signInWithEmailAndPassword,
  signOut,
  updateProfile,
} from "firebase/auth";
import { createContext, useContext, useState } from "react";
import { auth } from "../firebase";

const UserContext = createContext();

export const AuthContextProvider = ({ children }) => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [userId, setUserId] = useState(null);
  const [bearerToken, setBearerToken] = useState("");

  const updateUser = async (record) => {
    await updateProfile(auth.currentUser, {
      displayName: `${record.firstName} ${record.lastName}`,
      firstName: record.firstName,
      lastName: record.lastName,
    });

    await fetch(`${process.env.REACT_APP_API}/users/${userId}`, {
      method: "PUT",
      headers: {
        "Content-type": "application/json",
        Authorization: `Bearer ${bearerToken}`,
      },
      body: JSON.stringify(record),
    });
  };

  const registerNewUser = async (email, password, firstName, lastName) => {
    await setPersistence(auth, browserLocalPersistence);
    let token = await createUserWithEmailAndPassword(auth, email, password);
    setBearerToken(token.user.accessToken);

    await updateProfile(auth.currentUser, {
      displayName: `${firstName} ${lastName}`,
      firstName: firstName,
      lastName: lastName,
    });

    //add user to local database
    const userRecord = {
      displayName: token.user.displayName,
      firstName: firstName,
      lastName: lastName,
      uid: token.user.uid,
      email: email,
    };
    await fetch(`${process.env.REACT_APP_API}/users`, {
      method: "POST",
      headers: {
        "Content-type": "application/json",
        Authorization: `Bearer ${token.user.accessToken}`,
      },
      body: JSON.stringify(userRecord),
    });
    setIsLoggedIn(true);
    setUserId(token.user.uid);
    return token.user;
  };

  const userIsLoggedIn = () => {
    const user = auth.currentUser;
    if (user) {
      setIsLoggedIn(true);
      setUserId(user.uid);
      setBearerToken(user.accessToken);
      return true;
    } else {
      setIsLoggedIn(false);
      return false;
    }
  };

  const getCurrentUserDisplayName = () => {
    const user = auth.currentUser;
    if (user) {
      return user.displayName;
    } else {
      return "";
    }
  };

  const getProfile = async () => {
    const token = getAuth();

    if (token?.currentUser?.uid) {
      try {
        const userId = token.currentUser.uid;
        const getUserUri = `${process.env.REACT_APP_API}/users/${userId}`;
        const userRecordResp = await fetch(getUserUri, {
          method: "GET",
          headers: {
            "Content-type": "application/json",
            Authorization: `Bearer ${bearerToken}`,
          },
        });
        const userRecord = userRecordResp.json();
        if (!userRecord) throw new Error("Unable to find user id");
        return userRecord;
      } catch (eX) {
        console.error(eX);
      }
    } else {
      return false;
    }
  };

  const deleteProfile = async () => {
    const token = getAuth();

    if (token?.currentUser?.uid) {
      try {
        const userId = token.currentUser.uid;
        const getUserUri = `${process.env.REACT_APP_API}/users/${userId}`;
        const response = await fetch(getUserUri, {
          method: "DELETE",
          headers: {
            "Content-type": "application/json",
            Authorization: `Bearer ${bearerToken}`,
          },
        });
        if (!response.ok)
          console.error("Unable to delete user.", response.statusText);
        logout();
      } catch (eX) {
        console.error(eX);
      }
    } else {
      return false;
    }
  };

  const signIn = async (email, password) => {
    await setPersistence(auth, browserSessionPersistence);
    let token = await signInWithEmailAndPassword(auth, email, password);
    setIsLoggedIn(true);
    setUserId(token.user.uid);
    setBearerToken(token.user.accessToken);
  };

  const logout = () => {
    setIsLoggedIn(false);
    return signOut(auth);
  };

  return (
    <UserContext.Provider
      value={{
        registerNewUser,
        signIn,
        logout,
        updateUser,
        getProfile,
        getCurrentUserDisplayName,
        isLoggedIn,
        userIsLoggedIn,
        userId,
        bearerToken,
        deleteProfile,
      }}
    >
      {children}
    </UserContext.Provider>
  );
};

export const UserAuth = () => {
  return useContext(UserContext);
};
