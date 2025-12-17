DROP DATABASE IF EXISTS pet_adoption;
CREATE DATABASE pet_adoption; 
USE pet_adoption; 

-- 1. Users Table 
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    fullname VARCHAR(100), 
    password VARCHAR(255) NOT NULL,
    role ENUM ('Admin','ShelterWorker','Adopter') NOT NULL,
    contact VARCHAR(20)
);

-- 2. Shelters Table 
CREATE TABLE shelters (
    shelter_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    location VARCHAR(100),
    contact VARCHAR(20)
);

-- 3. Pets Table 
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

-- 4. Adoption Applications
CREATE TABLE adoption_applications (
    app_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    pet_id INT,
    status ENUM ('Pending', 'Approved' , 'Rejected' ) DEFAULT 'Pending',
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (pet_id) REFERENCES pets(pet_id)
);

-- 5. Rehome Requests 
CREATE TABLE rehome_requests (
    req_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    pet_name VARCHAR(50),
    pet_type VARCHAR(20),
    pet_breed VARCHAR(50),
    age INT,
    reason TEXT,
    status ENUM('Pending', 'Approved' , 'Rejected' ) DEFAULT 'Pending' ,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);


INSERT INTO shelters (shelter_id, name, location, contact) VALUES 
(1, 'Happy Paws', 'Colombo', '011223344');

INSERT INTO users (username, fullname, password, role, contact) VALUES 
('admin', 'System Administrator', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 'Admin', '0771112222'),
('worker1', 'John Doe', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 'ShelterWorker', '0773334444'),
('adopter1', 'Jane Smith', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 'Adopter', '0775556666');

INSERT INTO pets (name, type, breed, age, shelter_id, status) VALUES 
('Buddy', 'Dog', 'Golden Retriever', 2, 1, 'Available'),
('Luna', 'Cat', 'Siamese', 1, 1, 'Available'),
('Max', 'Dog', 'German Shepherd', 3, 1, 'Available'),
('Bella', 'Dog', 'Labrador', 4, 1, 'Available'),
('Charlie', 'Cat', 'Persian', 2, 1, 'Available');