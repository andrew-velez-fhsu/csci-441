# Packages to install

- Java 17 (ensure JAVA_HOME is set). Can use any flavor, but I used [Microsoft's Distribution](https://learn.microsoft.com/en-us/java/openjdk/download)
- [Postgresql](https://www.postgresql.org/download)
- [Azure Data Studio](https://learn.microsoft.com/en-us/azure-data-studio/download-azure-data-studio?view=sql-server-ver16&tabs=win-install%2Cwin-user-install%2Credhat-install%2Cwindows-uninstall%2Credhat-uninstall#download-azure-data-studio)
- [Postman](https://www.postman.com/downloads/)

Prerequisites

- Create a Postgres database by running the /Database/Schema.sql script
- Set the web user password that was created in the step above:
  `ALTER USER web WITH PASSWORD '{Your secure password}'`

# Environment Variables

Environment variables are used for accessing secrets without commiting them to source control. You will need to create the following environment variables to make the backend work:

- APILAYER_KEY : Used to get IP -> Geocode location information, in case the user refuses to share their actual location
- AZURE_MAPS_KEY : Used to get Address -> Geocode location information
- GOOGLE_APPLICATION_CREDENTIALS : Used to manage accounts in Firebase
- WEB_PASSWORD : Used to store/retrieve the password of the databse user account

Before running, you can add the environment variables using the following commands. Note, setting enivronment variables this way is temporary, and lasts only as long as the shell stays open.
PowerShell: `$env:WEB_PASSWORD="{Your secure password}"`
Mac/Linux: `export WEB_PASSWORD="{Your secure password}"`
Alternatively, you can store them in your system for permanent storage.

To Run: `./mvnw spring-boot:run`

The step to create a new entity are:

1. Create SQL Table Pets - in data studio
2. Create Java Model Pet class
3. Create Java Repository PetsRepository interface
4. Create Controller PetController class
