use Client_Scheduler;

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

INSERT INTO Appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Created_By, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES(1, 'title', 'description', 'location', 'Planning Session', '2020-05-28 13:00:00', '2020-05-28 14:00:00', 'script', 'script', 1, 1, 3);
INSERT INTO Appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Created_By, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES(2, 'title', 'description', 'location', 'De-Briefing', '2020-05-29 13:00:00', '2020-05-29 14:00:00', 'script', 'script', 2, 2, 2);

COMMIT;
