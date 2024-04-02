# Template

This is an easy-to-use template for all of my backend services. 
It uses a parent pom which pulls packages that I made which configure a lot of basic things.

# These things include:
- Spring security with configs for keycloak. Keycloak server url needs to be added in application properties
- Swagger for easy viewing of open api definition
- Websocket config so you can make backends with 2 way communication for games etc
- Optional Mariadb and jpa dependencies. These can be included by creating a mariadb.marker file at the project root
  - If you activate the database dependency you need to set these config values:
    - **spring.datasource.url** or the env variable **database_url**
    - **spring.datasource.username** or the env **database_username**
    - **spring.datasource.password** or the env **database_password**
- GitHub actions workflows for testing upon merge request on the develop branch and build/publish pipelines for develop and on release.

# Usage

After you use this template on GitHub you will need to change a few things. These generally only include the name of your service.
To do this easily just use find and replace to replace "template" with your service name.
