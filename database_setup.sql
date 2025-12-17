DROP DATABASE IF EXISTS pet_adoption;
CREATE DATABASE pet_adoption;
USE pet_adoption;


CREATE TABLE users (
                       user_id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       role ENUM ('Admin','ShelterWorker','Adopter') NOT NULL,
                       contact VARCHAR(20)
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
                                 age INT,
                                 reason TEXT,
                                 status ENUM('Pending', 'Approved' , 'Rejected' ) DEFAULT 'Pending' ,
                                 FOREIGN KEY (user_id) REFERENCES users(user_id)
);



-- 1. Create the Shelter (ID 1)
INSERT INTO shelters (shelter_id, name, location, contact) VALUES
    (1, 'Happy Paws', 'Gale', '011223344');

-- 2. Create Default Users (Password is '123' hashed)
INSERT INTO users (username, password, role, contact) VALUES
                                                          ('admin', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 'Admin', '077111222'),
                                                          ('worker1', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 'ShelterWorker', '077333444'),
                                                          ('adopter1', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 'Adopter', '077555666');

-- 3. Create Initial Available Pets
INSERT INTO pets (name, type, breed, age, shelter_id, status) VALUES
                                                                  ('Buddy', 'Dog', 'Golden Retriever', 2, 1, 'Available'),
                                                                  ('Luna', 'Cat', 'Siamese', 1, 1, 'Available'),
                                                                  ('Max', 'Dog', 'German Shepherd', 3, 1, 'Available');