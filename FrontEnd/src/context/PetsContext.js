import { createContext, useContext, useState } from "react";
import { UserAuth } from "./AuthContext";

const PetsContext = createContext();

export const PetsContextProvider = ({ children }) => {
  const [pets, setPets] = useState([]);
  const { bearerToken } = UserAuth();

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
    setPets(myPets);
    return myPets;
  };

  const addNewPet = async (uid) => {
    const pet = { uid };
    await fetch(`${process.env.REACT_APP_API}/pets`, {
      method: "POST",
      headers: {
        "Content-type": "application/json",
        Authorization: `Bearer ${bearerToken}`,
      },
      body: JSON.stringify(pet),
    });
    return pet;
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

  const getPets = async (props) => {
    let pets = await fetch(`${process.env.REACT_APP_API}/search`, {
      method: "GET",
      headers: {
        "Content-type": "application/json",
      },
    }).then((data) => data.json());

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
        pets,
        getPetsByUser,
        addNewPet,
        updatePet,
        getPets,
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
