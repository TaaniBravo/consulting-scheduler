# Software II - C195 PA

## Description

This application's purpose is to provide a scheduling to a consulting/client-based firm. It allows for the users to
create/update/delete customers and create/update/delete their appointments. It also allows users to be notified when
they have upcoming appointments.

## About

* Author: Taanileka Maama
* Email: tmaama@wgu.edu
* IntelliJ IDEA 2021.3.1
* Java Version: Java SE 17.0.1
* JavaFX SDK 19
* MySQL Version: 8.0.31
* mysql-connector-java-8.0.31

## Additional Report

The additional report that I decided to run was based on the Country and FirstLevelDivision. This report allows the
company to see which locations their business is coming from so that they can understand where they do well and where
they need to improve.

## Getting Started

### Step 0 - Using the WGU VM
With the WGU VM you should be able to just the run the app and skip the rest of these steps. If you are unable to login then make sure that `src/main/resources/db/config.properties` has the correct username and password.

### Step 1 - Installing dependencies

To get started first you will need to get the MySQL database and the tables created. <br/>
For more information on downloading MySQL please [click here](https://dev.mysql.com/downloads/installer/) <br/>
Or use a package manager if preferred like below:

```shell
# I used Chocolately to install on Windows
choco install mysql
# Or for Mac, Homebrew is a great option
brew install mysql
```

### Step 2 - Creating the Database

Once you have MySQL installed and running. Run this command in your terminal (doesn't work for Powershell) to get the
databases loaded into the file along with some test data you can play with.

```shell
# Using Bash
cd /path/to/project
# Use your username and password if applicable
mysql -u <username> -p <password> < ./src/main/resources/sql/createDatabase.sql
# If you need to drop the database to start from scratch use this command:
mysql -u <username> -p <password> < ./src/main/resources/sql/dropDatabase.sql

# Using Powershell
mysql -u <username> -p -e "source C:\path\to\project\src\main\resources\sql\createDatabaseAndSeed.sql"

# Alternatively you can run the SQL files individual in this order.
mysql -u <username> -p <password> < ./src/main/resources/sql/createDatabase.sql
mysql -u <username> -p <password> < ./src/main/resources/sql/createTriggers.sql
mysql -u <username> -p <password> < ./src/main/resources/sql/seed.sql
```

### Step 3 - Creating a config.properties file

Now that you have the MySQL running and seeded with the database Client_Scheduler you need to create a config.properties
file in `src/main/resources/db`

* There is a config.example.properties file that you can base your config.properties off of.
* Just make sure to change the username and password to your MySQL username and password.

### Step 4 - Running the App

Now we can finally run the app! If you're using Intellij IDEA then there is a run config XML that should have loaded
into your configurations. If that's the case you just need to press the play button at the top-right of the Intellij
window with the configuration `Run App` selected. If you rather run the app through a jar file that's an option as well.

```shell
# Start by packaging the app
mvn clean package
# Then just run jar command
java -jar ./target/scheduler-1.0-SNAPSHOT-jar-with-dependencies.jar
```
