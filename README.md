[![Heroku](https://heroku-badge.herokuapp.com/?app=bookworm-online-bookstore)](http://bookworm-online-bookstore.herokuapp.com)


# Bookworm-Online-Bookstore Project

- Java 8
- Spring Boot 2.3
- Spring Boot Data JPA
- Spring Security
- Joinfaces 4.3.0
- PrimeFaces 8
- PrimeFaces Extensions 8
- Lombok 1.18.12
- Azure Database for MySQL 5.7
- Mysql Connector 8.0.20

# Using Application
- Heroku Link: [Bookworm Online Bookstore](http://bookworm-online-bookstore.herokuapp.com)
Note: Database is no longer active due to high pricing so the heroku application is in maintenance mode.

- There are 2 user types and user panels according to these types. They are: user and admin.

# Using Database
```
spring.jpa.database-platform=org.hibernate.dialect.MySQL57InnoDBDialect
spring.datasource.url=AZURE_DATABASE_URL
spring.datasource.username=AZURE_DATABASE_USERNAME
spring.datasource.password=AZURE_DATABASE_PASSWORD
```
- Database settings comes pre-set in application.properties file.
- It is only necessary to fill in the login information to connect to the azure mysql database.

# Running App Locally
```
$ git clone https://github.com/HacettepeUniversityBBM384/bbm384-project-spring2020-baam.git
$ cd bbm384-project-spring2020-baam
$
$ mvn spring-boot:run
```

# Deploying App.war on Heroku

- Create Heroku account
- Install Heroku Cli
- Make the local Cli settings

```
$ heroku plugins:install java
$ mvn clean package
$
$ heroku war:deploy <path_to_war_file> --app <app_name>
```


