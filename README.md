# ZoomyBuddy

CSCI 441 Summer 2024
Group 1

# About

ZoomyBuddy is a project for a dog owner friend-finding service. It allows dog owners to find other dog
owners in order to arrange play dates with their dogs.

## Prerequisites

ZoomyBuddy is written using Node 20. You will need to downloand and install it.
To run the initial a proof of concept, the backend to the application is a rudimentary Json Server data store. To run ZoomBuddy,
install the json-server NPM package globally and run it in a separate terminal.

- Install Json-server globally: `npm install json-server -g`
- Run Json-server `npx json-server --watch FrontEnd/data/zoomies.json --port 8000`

The Google Firebase API key needs to be either added to the .env file, or an environment variable needs to be set.
The variable name is `REACT_APP_FIREBASE_API_KEY`. In order to use the seeded data properly, you should set this to
the same API key as Andrew used in development. Otherwise, you can update the configs in the .env file and
delete all the records in the `data/zoomies.json` file (leave the pets and users nodes as empty arrays).

To run the front end, You will need to install the node packages by running the following:

### `cd FrontEnd`

### `npm install`

## Running Zoomy Buddy

In the FrontEnd directory, you can run:

### `npm start`

Runs the app in the development mode.
Open [http://localhost:3000](http://localhost:3000) to view it in your browser.

The page will reload when you make changes.
You may also see any lint errors in the console.

# How to use ZoomyBuddy

There are 2 main paths: searching and adding pets. Search works for users who are not signed in, although you cannot
view the details of the pets returned from the search. If you are signed in, you can search, view details, manage
your profile, and manage your pets:

| Feature        | Anonymous Users | Authenticated Users |
| -------------- | --------------- | ------------------- |
| Sign in        | Yes             | No                  |
| Sign out       | No              | Yes                 |
| Create account | Yes             | No                  |
| Search         | Yes             | Yes                 |
| View summary   | Yes             | Yes                 |
| View details   | No              | Yes                 |
| Manage profile | No              | Yes                 |
| Manage pets    | No              | Yes                 |

Users can have unlimited pets. You can upload a picture of both your user account and for the pet. Pet photos are
displayed in the search cards as well as the details page. The location for a pet is set by the location of the
user at the time of save. If the user location changes, the pet location does not update.

Searching is rudimentary due to the lack of a real backend. To productionalize the application, a search service
would need to be built, which would index the pets and provide GIS services to search based on location. At this
point, the search service returns all pets, and then searches the following fields using a case-insensitive search:

- Name
- Description
- Breed
- Location

If you search with no search terms, all the pets in the database are return.

Images are saved to Google Firebase's storage account. They are saved using the original resolution. A future
enhancement would be to create thumbnails server side, and serve different resolutions on the search card and
profile pages.

## Known Issues

- Firebase does not provide the user context when the page first loads. Therefore, refreshing the page after login
  makes it appeear that the user is not logged in. Switching pages provides Firebase time to load the user context
  from local storage.
- Searching a blank term from the home page does not return any pets. If you repeat the blank search on the search
  page, all results will return.
