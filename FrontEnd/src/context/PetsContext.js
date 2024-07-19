import { createContext, useContext, useEffect, useState } from "react";
import { UserAuth } from "./AuthContext";

const PetsContext = createContext();

export const PetsContextProvider = ({ children }) => {
  const { bearerToken } = UserAuth();
  const [location, setLocation] = useState({ lat: null, lon: null });
  const [error, setError] = useState(null);

  useEffect(() => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          setLocation({
            lat: position.coords.latitude,
            lon: position.coords.longitude,
          });
          setError(null);
        },
        (error) => {
          switch (error.code) {
            case error.PERMISSION_DENIED:
              setError("User denied the request for Geolocation.");
              break;
            case error.POSITION_UNAVAILABLE:
              setError("Location information is unavailable.");
              break;
            case error.TIMEOUT:
              setError("The request to get user location timed out.");
              break;
            case error.UNKNOWN_ERROR:
              setError("An unknown error occurred.");
              break;
            default:
              setError("An unknown error occurred.");
              break;
          }
        }
      );
    } else {
      setError("Geolocation is not supported by this browser.");
    }
  }, []);

  const getPetsByUser = async (uid) => {
    const myPets = await fetch(
      `${process.env.REACT_APP_API}/users/${uid}/pets`,
      {
        method: "GET",
        headers: {
          "Content-type": "application/json",
          Authorization: `Bearer ${bearerToken}`,
        },
      }
    ).then((data) => data.json());
    return myPets;
  };

  const addNewPet = async (uid) => {
    const pet = { uid };
    const newPet = await fetch(`${process.env.REACT_APP_API}/pets`, {
      method: "POST",
      headers: {
        "Content-type": "application/json",
        Authorization: `Bearer ${bearerToken}`,
      },
      body: JSON.stringify(pet),
    }).then((data) => data.json());

    return newPet;
  };

  const updatePet = async (petProfile) => {
    await fetch(`${process.env.REACT_APP_API}/pets/${petProfile.id}`, {
      method: "PUT",
      headers: {
        "Content-type": "application/json",
        Authorization: `Bearer ${bearerToken}`,
      },
      body: JSON.stringify(petProfile),
    });
  };

  const deletePet = async (petProfile) => {
    await fetch(`${process.env.REACT_APP_API}/pets/${petProfile.id}`, {
      method: "DELETE",
      headers: {
        "Content-type": "application/json",
        Authorization: `Bearer ${bearerToken}`,
      },
    });
  };

  const searchPets = async (props) => {
    let params = new URLSearchParams();
    if (location.lat && location.lon) {
      params.append("lat", location.lat);
      params.append("lon", location.lon);
    }
    if (props.radius) {
      params.append("radius", props.radius);
    }

    const queryParams = params.toString();

    let pets = await fetch(
      `${process.env.REACT_APP_API}/search?${queryParams}`,
      {
        method: "GET",
        headers: {
          "Content-type": "application/json",
        },
      }
    ).then((data) => data.json());

    if (props.search) {
      pets = pets.filter(
        (pet) =>
          pet.name.toLowerCase().includes(props.search.toLowerCase()) ||
          pet.description.toLowerCase().includes(props.search.toLowerCase()) ||
          pet.breed.toLowerCase().includes(props.search.toLowerCase()) ||
          pet.location?.toLowerCase().includes(props.search.toLowerCase())
      );
    }
    if (props.isGoodWithChildren !== undefined) {
      pets = pets.filter(
        (pet) => pet.isGoodWithChildren === props.isGoodWithChildren
      );
    }
    if (props.isResourceProtective !== undefined) {
      pets = pets.filter(
        (pet) => pet.isResourceProtective === props.isResourceProtective
      );
    }
    return pets;
  };

  const getPet = async (id) => {
    const pet = await fetch(`${process.env.REACT_APP_API}/pets/${id}`, {
      method: "GET",
      headers: {
        "Content-type": "application/json",
        Authorization: `Bearer ${bearerToken}`,
      },
    }).then((data) => data.json());

    return pet;
  };

  return (
    <PetsContext.Provider
      value={{
        getPetsByUser,
        addNewPet,
        updatePet,
        searchPets,
        getPet,
        deletePet,
      }}
    >
      {children}
    </PetsContext.Provider>
  );
};

export const Pets = () => {
  return useContext(PetsContext);
};
