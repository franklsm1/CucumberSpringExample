# BDD Cucumber Java Spring Example
Java spring application using Cucumber for acceptance tests and a MySQL db for storing data

### Setup MySQL
1) install MySQL (on a mac) with: `brew install mysql`
2) start MySQL with: `mysql.server start`
3) access MySQL with: `mysql -u root`
4) inside of MySQL run the following two commands to create the databases that this example uses:
- `create database user_groups;`
- `create database user_groups_test;`
5) running the tests or starting the app will create the table structure for you, thank-you hibernate!

### Gradle Commands:
- Clean, build, run tests, and check code coverage: `gradle clean build`
- Start the application on *http://localhost:7777*: `gradle bootrun`

### Swagger Page:
- View the swagger UI when the app is running go to: *http://localhost:7777/swagger-ui.html*

### Testing in IntelliJ
1) Install `Lombok` and `Cucumber for Java` Intellij plugins through the preferences
2) Restart Intellij
3) Open the `src/test/resources` directory and right click on `users.feature` file then click run

### Example Cucumber Output from IntelliJ:
![Image of cucumber output](https://github.com/franklsm1/UserGroupsAPI/blob/master/public/CucumberOutput.png)
