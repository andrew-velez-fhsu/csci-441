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
    uid VARCHAR PRIMARY KEY,
    displayname VARCHAR(255) NOT NULL,
    firstname VARCHAR(127) NOT NULL,
    lastname VARCHAR(127) NOT NULL,
    email VARCHAR(320) NOT NULL UNIQUE,
    address1 VARCHAR(255),
    address2 VARCHAR(255),
    city VARCHAR(100), 
    state varchar(100), 
    postalcode varchar(20),
    profileurl varchar
);
-- DROP TABLE public.users CASCADE

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
-- DROP TABLE public.pets CASCADE


CREATE TABLE IF NOT EXISTS public.chats(
    id SERIAL PRIMARY KEY,
    subject VARCHAR,
    senderUid VARCHAR(50) NOT NULL,
    recipientUid VARCHAR(50) NOT NULL,
    messages VARCHAR,
    date Date,
    CONSTRAINT FK_Sender    FOREIGN KEY (senderUid) REFERENCES users(uid),
    CONSTRAINT FK_Recipient    FOREIGN KEY (recipientUid) REFERENCES users(uid)
);


-- DROP TABLE public.chats CASCADE

CREATE TABLE IF NOT EXISTS public.messages (
    id SERIAL PRIMARY KEY,
    chatId INTEGER NOT NULL,
    sendBy VARCHAR(50) NOT NULL,
    body VARCHAR ,
    status VARCHAR(255),
    timestamp DATE,
    CONSTRAINT FK_Chat    FOREIGN KEY (chatId) REFERENCES chats(id)
)

-- DROP TABLE public.messages CASCADE




CREATE TABLE IF NOT EXISTS public.photos(
    id SERIAL PRIMARY KEY,
    petId INTEGER NOT null,
    url VARCHAR,
    CONSTRAINT FK_Pet    FOREIGN KEY (petId) REFERENCES pets(id)
)

-- DROP TABLE public.PHOTOS CASCADE

-- This will allow the user to do all actions on all tables
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public to web;
GRANT USAGE ON SEQUENCE pets_id_seq TO web;
GRANT USAGE ON SEQUENCE messages_id_seq TO web;
GRANT USAGE ON SEQUENCE chats_id_seq TO web;
GRANT USAGE ON SEQUENCE photos_id_seq TO web;

-- ADD Location awareness to USERS
BEGIN TRANSACTION;
CREATE EXTENSION IF NOT EXISTS postgis;
ALTER TABLE public.users
ADD COLUMN longitude DOUBLE PRECISION,
ADD COLUMN latitude DOUBLE PRECISION,
ADD COLUMN location GEOGRAPHY(POINT);
COMMIT

-- Add foreign key reference to pets
BEGIN TRANSACTION;
ALTER TABLE public.pets
ADD CONSTRAINT FK_Pets_Users FOREIGN KEY (uid) REFERENCES users(uid);
COMMIT

-- Remove ID key for users
-- BEGIN TRANSACTION;
-- ALTER TABLE public.users
-- DROP COLUMN id;
-- ALTER TABLE public.users
-- ADD CONSTRAINT PK_Users PRIMARY KEY (uid);

-- Drop existing UNIQUE key for users. Recreate foreign keys based on new primary key index
ALTER TABLE public.pets
DROP CONSTRAINT FK_Pets_Users;

ALTER TABLE public.messages
DROP CONSTRAINT FK_Recipient;

ALTER TABLE public.messages
DROP CONSTRAINT FK_Sender;

-- ALTER TABLE public.users
-- DROP CONSTRAINT users_uid_key;

ALTER TABLE public.messages
ADD CONSTRAINT FK_Recipient FOREIGN KEY (recipientUid) REFERENCES users(uid);

ALTER TABLE public.messages
ADD CONSTRAINT FK_Sender FOREIGN KEY (senderUid) REFERENCES users(uid);

ALTER TABLE public.pets
ADD CONSTRAINT FK_Pets_Users FOREIGN KEY (uid) REFERENCES users(uid);
COMMIT

BEGIN TRANSACTION;
ALTER TABLE public.pets
ADD COLUMN profileUrl varchar;

COMMIT;


BEGIN TRANSACTION;
ALTER TABLE public.messages
DROP COLUMN sendBy;

ALTER TABLE public.messages
ADD COLUMN senderUid VARCHAR NOT NULL CONSTRAINT FK_Sender   REFERENCES users(uid);

ALTER TABLE public.chats
DROP COLUMN messages;
COMMIT;

BEGIN TRANSACTION;
GRANT USAGE ON SEQUENCE chats_id_seq TO web;
GRANT USAGE ON SEQUENCE messages_id_seq TO web;
COMMIT;