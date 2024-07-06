CREATE DATABASE zoomybuddy;
-- NOTES ON NAMING CONVENTION
-- Name all tables in lower case
-- All columns should be lower case


-- Create a user account for the web site service
-- Best practice is to not log in with super user
CREATE USER web;




-- When defining tables, use `CREATE TABLE IF NOT EXISTS` in order to not throw errors if you unintentionally re-run the script
-- Don't DROP & CREATE - if changes are required to schema, create ALTER TABLE statements



/*
Table "users" contain the following columns:
- id : internal identifier, incrementing integer
- displayName : Usually firstName + " " + lastName. Required
- firstName : given name. Required
- lastName : family name. Required
- uid : identifier provided by Google Firebase for user. Required
- email : the user's email address. Required
- address1: the user's street address. Optional
- address2: additional address information. Optional
- city : the user's city of residence. Optional
- state : the user's state of residence. Optional
- postalCode : the user's zip or postal code. Optional
- profileUrl : a link to a profile picture for the user. Optional
*/
CREATE TABLE IF NOT EXISTS public.users(
    id SERIAL PRIMARY KEY,
    displayname VARCHAR(255) NOT NULL,
    firstname VARCHAR(127) NOT NULL,
    lastname VARCHAR(127) NOT NULL,
    uid VARCHAR(36) NOT NULL UNIQUE,
    email VARCHAR(320) NOT NULL UNIQUE,
    address1 VARCHAR(255),
    address2 VARCHAR(255),
    city VARCHAR(100), 
    state varchar(100), 
    postalcode varchar(20),
    profileurl varchar
);

-- need to create a pet table here
CREATE TABLE IF NOT EXISTS public.pets(
    id SERIAL PRIMARY KEY,
    uid VARCHAR(36),
    name VARCHAR(50),
    description TEXT,
    breed VARCHAR (80),
    birthday DATE,
    isGoodWithChildren boolean,
    isResourceProtective boolean
);

-- DROP TABLE public.pets

-- This will allow the user to do all actions on all tables
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public to web;
GRANT USAGE ON SEQUENCE users_id_seq TO web;
GRANT USAGE ON SEQUENCE pets_id_seq TO web;


-- ADD Location awareness to USERS
CREATE EXTENSION IF NOT EXISTS postgis;


ALTER TABLE public.users
ADD COLUMN longitude DOUBLE PRECISION,
ADD COLUMN latitude DOUBLE PRECISION,
ADD COLUMN location GEOGRAPHY(POINT);