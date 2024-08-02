CREATE DATABASE IF NOT EXISTS elite_real_estate_pos;

USE elite_real_estate_pos;

CREATE TABLE Admin (
                       Adm_id VARCHAR(255) PRIMARY KEY,
                       Name VARCHAR(255) NOT NULL,
                       Address VARCHAR(255) NOT NULL,
                       Mobile VARCHAR(255) NOT NULL,
                       Password VARCHAR(255) NOT NULL,
                       Email VARCHAR(255) NOT NULL
);

CREATE TABLE Appointment (
                             App_id VARCHAR(255) PRIMARY KEY,
                             Adm_id VARCHAR(255) NOT NULL,
                             Cus_name VARCHAR(255) NOT NULL,
                             Cus_mobile VARCHAR(255) NOT NULL,
                             Date_time VARCHAR(255) NOT NULL,
                             FOREIGN KEY (Adm_id) REFERENCES Admin (Adm_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Customer (
                          Cus_id VARCHAR(255) PRIMARY KEY,
                          App_id VARCHAR(255) NOT NULL,
                          Name VARCHAR(255) NOT NULL,
                          Address VARCHAR(255) NOT NULL,
                          Mobile VARCHAR(255) NOT NULL,
                          Email VARCHAR(255) NOT NULL,
                          FOREIGN KEY (App_id) REFERENCES Appointment (App_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Supplier (
                          Sup_id VARCHAR(255) PRIMARY KEY,
                          Adm_id VARCHAR(255) NOT NULL,
                          Name VARCHAR(255) NOT NULL,
                          Address VARCHAR(255) NOT NULL,
                          Mobile VARCHAR(255) NOT NULL,
                          Email VARCHAR(255) NOT NULL,
                          FOREIGN KEY (Adm_id) REFERENCES Admin (Adm_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Property (
                          Pro_id VARCHAR(255) PRIMARY KEY,
                          Sup_id VARCHAR(255) NOT NULL,
                          Type VARCHAR(255) NOT NULL,
                          Address VARCHAR(255) NOT NULL,
                          Price VARCHAR(255) NOT NULL,
                          Perches VARCHAR(255) NOT NULL,
                          FOREIGN KEY (Sup_id) REFERENCES Supplier (Sup_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Payment_details (
                                 Pay_id VARCHAR(255) PRIMARY KEY,
                                 Pro_id VARCHAR(255) NOT NULL,
                                 Cus_id VARCHAR(255) NOT NULL,
                                 Pro_price VARCHAR(255) NOT NULL,
                                 Cus_name VARCHAR(255) NOT NULL,
                                 Method VARCHAR(255) NOT NULL,
                                 FOREIGN KEY (Pro_id) REFERENCES Property (Pro_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                 FOREIGN KEY (Cus_id) REFERENCES Customer (Cus_id) ON DELETE CASCADE ON UPDATE CASCADE
);
