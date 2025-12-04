DROP DATABASE IF EXISTS pet_adoption;
CREATE DATABASE pet_adoption ; 
USE pet_adoption ; 

CREATE TABLE users (
	user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM ('Admin','ShelterWorker','Adopter') NOT NULL
);

CREATE TABLE shelters (
	shelter_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    location VARCHAR(100),
    contact VARCHAR(20)
);

CREATE TABLE pets (
	pet_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    type VARCHAR(20),
    breed VARCHAR(50),
    age INT,
    shelter_id INT,
    status ENUM('Available','Pending','Adopted') DEFAULT 'Available',
    FOREIGN KEY (shelter_id) REFERENCES shelters(shelter_id)
);

CREATE TABLE adoption_applications (
	app_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    pet_id INT,
    status ENUM ('Pending', 'Approved' , 'Rejected' ) DEFAULT 'Pending',
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (pet_id) REFERENCES pets(pet_id)
);

CREATE TABLE rehome_requests (
	req_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    pet_name VARCHAR(50),
    pet_type VARCHAR(20),
    pet_breed VARCHAR(50),
    reason TEXT,
    status ENUM('Pending', 'Approved' , 'Rejected' ) DEFAULT 'Pending' ,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Data Inserts 

INSERT INTO users (username, password, role) VALUES 
('admin', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 'Admin'),
('worker1', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 'ShelterWorker'),
('adopter1', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 'Adopter');

INSERT INTO shelters (name, location, contact) VALUES 
('Happy Paws', 'Colombo', '011223344');

INSERT INTO pets (name, type, age, shelter_id, status) VALUES 
('Buddy', 'Dog', 2, 1, 'Available');

    