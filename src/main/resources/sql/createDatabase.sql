CREATE DATABASE IF NOT EXISTS ConsultingScheduler;
USE ConsultingScheduler;

CREATE TABLE IF NOT EXISTS Countries (
    Country_ID INT(10) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    Country VARCHAR(50),
    Create_Date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Created_By VARCHAR(50),
    Last_Update TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    Last_Updated_By VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS FirstLevelDivisions (
    Division_ID INT(10) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    Division VARCHAR(50),
    Create_Date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Created_By VARCHAR(50),
    Last_Update TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    Last_Updated_By VARCHAR(50),
    Country_ID INT(10),
    FOREIGN KEY (Country_ID)
        REFERENCES Countries(Country_ID)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Customers (
    Customer_ID INT(10) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    Customer_Name VARCHAR(50),
    Address VARCHAR(100),
    Postal_Code VARCHAR(50),
    Phone VARCHAR(50),
    Create_Date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Created_By VARCHAR(50),
    Last_Update TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    Last_Updated_By VARCHAR(50),
    Division_ID INT(10),
    FOREIGN KEY (Division_ID)
        REFERENCES FirstLevelDivisions(Division_ID)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Users (
    User_ID INT(10) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    User_Name VARCHAR(50) NOT NULL,
    Password TEXT NOT NULL,
    Create_Date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Created_By VARCHAR(50) NOT NULL,
    Last_Update TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    Last_Updated_By VARCHAR(50),
    CONSTRAINT User_Name_Unique UNIQUE (User_Name)
);

CREATE TABLE IF NOT EXISTS Contacts (
    Contact_ID INT(10) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    Contact_Name VARCHAR(50) NOT NULL,
    Email VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS Appointments (
    Appointment_ID INT(10) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    Title VARCHAR(50),
    Description VARCHAR(50),
    Location VARCHAR(50),
    Type VARCHAR(50),
    Start DATETIME,
    End DATETIME,
    Create_Date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Created_By VARCHAR(50) NOT NULL,
    Last_Update TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    Last_Updated_By VARCHAR(50),
    Customer_ID INT (10) NOT NULL,
    User_ID INT(10) NOT NULL,
    Contact_ID INT(10) NOT NULL,
    FOREIGN KEY (Customer_ID)
        REFERENCES Customers(Customer_ID)
        ON DELETE CASCADE,
    FOREIGN KEY (User_ID)
        REFERENCES Users(User_ID)
        ON DELETE CASCADE,
    FOREIGN KEY (Contact_ID)
        REFERENCES Contacts(Contact_ID)
        ON DELETE CASCADE
);
