# Software II - C195 PA

## Description
This application's solves...

## About Me
* Author: Taanileka Maama
* Email: tmaama@wgu.edu
* IntelliJ IDEA 2021.3.1
* Java Version: Java SE 17.0.1
* JavaFX SDK 19
* MySQL Version: 8.0.31

## Getting Started
To get started first you will need to get the MySQL database and the tables created. <br/>
For more information on downloading MySQL please [click here](https://dev.mysql.com/downloads/installer/) <br/>
Or use a package manager if preferred like below:
```shell
# I used Chocolately to install on Windows
choco install mysql
# Or for Mac, Homebrew is a great option
brew install mysql
```

### Docker
TK

Once you have MySQL installed and running. Run this command in your terminal (doesn't work for Powershell) to get the databases loaded into the file.
```shell
# Using Bash
cd /path/to/project
# Use your username and password if applicable
mysql -u <username> -p <password> < ./src/main/resources/sql/createDatabase.sql
# If you need to drop the database to start from scratch use this command:
mysql -u <username> -p <password> < ./src/main/resources/sql/dropDatabase.sql

# Using Powershell
mysql -u root -p -e "source C:\path\to\project\src\main\resources\sql\createDatabase.sql"
```
