import { ref, uploadBytes, getDownloadURL } from "firebase/storage";
import { ulid } from "ulid";
import { storage } from "../firebase";
import { createContext, useContext } from "react";

const StorageContext = createContext();

export const StorageContextProvider = ({ children }) => {
  const uploadFile = async (file, type = "image") => {
    const fileExtension = file.name.split(".").pop();
    const fileName = `${type}/${ulid()}.${fileExtension}`;
    const storageRef = ref(storage, fileName);
    await uploadBytes(storageRef, file);
    const fileUrl = await getDownloadURL(storageRef);
    return fileUrl;
  };

  return (
    <StorageContext.Provider value={{ uploadFile }}>
      {children}
    </StorageContext.Provider>
  );
};

export const Storage = () => {
  return useContext(StorageContext);
};
