DROP DATABASE IF EXISTS Client_Scheduler;
CREATE DATABASE Client_Scheduler;
use Client_Scheduler;
CREATE TABLE IF NOT EXISTS Countries (
    Country_ID INT(10) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    Country VARCHAR(50),
    Create_Date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Created_By VARCHAR(50),
    Last_Update TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    Last_Updated_By VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS First_Level_Divisions (
    Division_ID INT(10) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    Division VARCHAR(50),
    Create_Date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Created_By VARCHAR(50),
    Last_Update TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
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
    Last_Update TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    Last_Updated_By VARCHAR(50),
    Division_ID INT(10),
    FOREIGN KEY (Division_ID)
        REFERENCES First_Level_Divisions(Division_ID)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Users (
    User_ID INT(10) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    User_Name VARCHAR(50) NOT NULL,
    Password TEXT NOT NULL,
    Create_Date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Created_By VARCHAR(50) NOT NULL,
    Last_Update TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
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
    Last_Update TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
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

CREATE TABLE IF NOT EXISTS Salts (
    Salt_ID INT(10) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    Salt BLOB NOT NULL,
    User_ID INT NOT NULL,
    FOREIGN KEY (User_ID)
        REFERENCES Users(User_ID)
        ON DELETE CASCADE,
    CONSTRAINT User_ID_Unique UNIQUE (User_ID)
);

DROP TRIGGER IF EXISTS before_appointment_insert;
DROP TRIGGER IF EXISTS before_appointment_update;

DELIMITER $$
CREATE TRIGGER before_appointment_insert
BEFORE INSERT ON Appointments
FOR EACH ROW
BEGIN
  IF EXISTS (SELECT * FROM Appointments
      WHERE Start <= new.End
      AND End >= new.Start) THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Overlaps with an existing appointment';
  END IF;
END $$

CREATE TRIGGER before_appointment_update
BEFORE UPDATE ON Appointments
FOR EACH ROW
BEGIN
  IF EXISTS (SELECT * FROM Appointments
      WHERE Start <= new.End
      AND End >= new.Start
      AND Appointment_ID != NEW.Appointment_ID) THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Overlaps with an existing appointment';
  END IF;
END $$
DELIMITER ;


START TRANSACTION;
DELETE FROM Appointments WHERE Appointment_ID < 10000;
DELETE FROM Customers WHERE Customer_ID < 10000;
DELETE FROM First_Level_Divisions WHERE Division_ID < 10000;
DELETE FROM Countries WHERE Country_ID < 10000;
DELETE FROM Users WHERE User_ID < 10000;
DELETE FROM Contacts WHERE Contact_ID < 10000;

INSERT INTO Users (User_ID, User_Name, Password, Created_By, Last_Updated_By) VALUES (1, 'test', '[114, -68, 50, 90, -16, -2]', 'script', 'script');
INSERT INTO Users (User_ID, User_Name, Password, Created_By, Last_Updated_By) VALUES (2, 'admin', '[-115, 2, -76, -98, -101, -16]', 'script', 'script');

INSERT INTO Salts (Salt_ID, Salt, User_ID) VALUES (1, x'D7FDA3E23581C0B8EBA68EBCACACEFF7', 1);
INSERT INTO Salts (Salt_ID, Salt, User_ID) VALUES (2, x'C0FC69D51A99740BDEE9E08454CDC594', 2);
-- contacts

INSERT INTO Contacts VALUES(1,	'Anika Costa', 'acoasta@company.com');
INSERT INTO Contacts VALUES(2,	'Daniel Garcia',	'dgarcia@company.com');
INSERT INTO Contacts VALUES(3,	'Li Lee',	'llee@company.com');

-- Countries

INSERT INTO Countries (Country, Created_By, Last_Updated_By) VALUES('U.S',	'script', 'script');
INSERT INTO Countries (Country, Created_By, Last_Updated_By) VALUES('UK', 'script', 'script');
INSERT INTO Countries (Country, Created_By, Last_Updated_By) VALUES('Canada',	'script', 'script');

-- First Level Divisions

INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Alabama', 1, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Alaska', 54, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Arizona', 02, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Arkansas', 03, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('California', 04, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Colorado', 05, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Connecticut', 06, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Delaware', 07, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('District of Columbia', 08, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Florida', 09, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Georgia', 10, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Hawaii', 52, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Idaho', 11, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Illinois', 12, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Indiana', 13, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Iowa', 14, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Kansas', 15, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Kentucky', 16, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Louisiana', 17, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Maine', 18, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Maryland', 19, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Massachusetts', 20, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Michigan', 21, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Minnesota', 22, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Mississippi', 23, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Missouri', 24, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Montana', 25, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Nebraska', 26, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Nevada', 27, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('New Hampshire', 28, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('New Jersey', 29, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('New Mexico', 30, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('New York', 31, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('North Carolina', 32, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('North Dakota', 33, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Ohio', 34, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Oklahoma', 35, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Oregon', 36, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Pennsylvania', 37, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Rhode Island', 38, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('South Carolina', 39, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('South Dakota', 40, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Tennessee', 41, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Texas', 42, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Utah', 43, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Vermont', 44, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Virginia', 45, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Washington', 46, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('West Virginia', 47, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Wisconsin', 48, 'script', 'script', 1 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Wyoming', 49, 'script', 'script', 1 );



INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Alberta', 61, 'script', 'script', 3 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('British Columbia', 62, 'script', 'script', 3 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Manitoba', 63, 'script', 'script', 3 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('New Brunswick', 64, 'script', 'script', 3 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Newfoundland and Labrador', 72, 'script', 'script', 3 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Northwest Territories', 60, 'script', 'script', 3 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Nova Scotia', 65, 'script', 'script', 3 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Nunavut', 70, 'script', 'script', 3 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Ontario', 67, 'script', 'script', 3 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Prince Edward Island', 66, 'script', 'script', 3 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Québec', 68, 'script', 'script', 3 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Saskatchewan', 69, 'script', 'script', 3 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Yukon', 71, 'script', 'script', 3 );

INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('England', 101, 'script', 'script', 2 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Wales', 102, 'script', 'script', 2 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Scotland',103, 'script', 'script', 2 );
INSERT INTO First_Level_Divisions(Division, Division_ID, Created_By, Last_Updated_By, COUNTRY_ID) VALUES('Northern Ireland', 104, 'script', 'script', 2 );

-- Customers

INSERT INTO Customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Created_By, Last_Updated_By, Division_ID) VALUES(1, 'Daddy Warbucks', '1919 Boardwalk', '01291', '869-908-1875', 'script', 'script', 29);
INSERT INTO Customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Created_By, Last_Updated_By, Division_ID) VALUES(2, 'Lady McAnderson', '2 Wonder Way', 'AF19B', '11-445-910-2135', 'script', 'script', 103);
INSERT INTO Customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Created_By, Last_Updated_By, Division_ID) VALUES(3, 'Dudley Do-Right', '48 Horse Manor ', '28198', '874-916-2671', 'script', 'script', 60);

-- Appointments

INSERT INTO Appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Created_By, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES(1, 'title', 'description', 'location', 'Planning Session', '2020-05-28 12:00:00', '2020-05-28 13:00:00', 'script', 'script', 1, 1, 3);
INSERT INTO Appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Created_By, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES(2, 'title', 'description', 'location', 'De-Briefing', '2020-05-29 12:00:00', '2020-05-29 13:00:00', 'script', 'script', 2, 2, 2);

COMMIT;
