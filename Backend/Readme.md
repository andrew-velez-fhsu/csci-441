# Packages to install

- Java 17 (ensure JAVA_HOME is set). Can use any flavor, but I used [Microsoft's Distribution](https://learn.microsoft.com/en-us/java/openjdk/download)
- [Postgresql](postgresql.org/download)
- [Azure Data Studio](https://learn.microsoft.com/en-us/azure-data-studio/download-azure-data-studio?view=sql-server-ver16&tabs=win-install%2Cwin-user-install%2Credhat-install%2Cwindows-uninstall%2Credhat-uninstall#download-azure-data-studio)


Prerequisites
- Create a Postgres database by running the /Database/Schema.sql script
- Set the web user password that was created in the step above:
  `ALTER USER web WITH PASSWORD '{Your secure password}`

Before running, you need to add the password above to your environment variables:
PowerShell: `$env:WEB_PASSWORD="{Your secure password}"`
Mac/Linux: `export WEB_PASSWORD="{Your secure password}"`


To Run: `./mvnw spring-boot:run`
