import { ulid } from "ulid";

import { createContext, useContext, useState } from "react";

const PetsContext = createContext();

export const PetsContextProvider = ({ children }) => {
  const [pets, setPets] = useState([]);

  const getPetsByUser = async (uid) => {
    const allPets = await fetch(`${process.env.REACT_APP_API_URL}/pets`).then(
      (data) => data.json()
    );
    const myPets = allPets.filter((pet) => pet.uid === uid);
    setPets(myPets);
    return myPets;
  };

  const addNewPet = async (uid) => {
    const id = ulid();
    const pet = { uid, id };
    await fetch(`${process.env.REACT_APP_API_URL}/pets`, {
      method: "POST",
      headers: { "Content-type": "application/json" },
      body: JSON.stringify(pet),
    });
    return pet;
  };

  const updatePet = async (petProfile, userProfile) => {
    const record = { ...petProfile };
    if (userProfile) {
      record["location"] = `${userProfile.city}, ${userProfile.state}`;
    }

    await fetch(`${process.env.REACT_APP_API_URL}/pets/${petProfile.id}`, {
      method: "PUT",
      headers: { "Content-type": "application/json" },
      body: JSON.stringify(record),
    });
  };

  const getPets = async (props) => {
    let pets = await fetch(`${process.env.REACT_APP_API_URL}/pets`).then(
      (data) => data.json()
    );

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
    const pet = await fetch(`${process.env.REACT_APP_API_URL}/pets/${id}`).then(
      (data) => data.json()
    );

    return pet;
  };

  return (
    <PetsContext.Provider
      value={{ pets, getPetsByUser, addNewPet, updatePet, getPets, getPet }}
    >
      {children}
    </PetsContext.Provider>
  );
};

export const Pets = () => {
  return useContext(PetsContext);
};
