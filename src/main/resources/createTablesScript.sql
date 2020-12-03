/*CREATE TABLE doctors (
                         id BIGINT IDENTITY PRIMARY KEY,
                         firstname VARCHAR(255) NOT NULL,
                         lastname VARCHAR(255) NOT NULL,
                         middlename VARCHAR(255) NOT NULL,
                         specialization VARCHAR(255)
);*/

CREATE TABLE patients (
                          id BIGINT IDENTITY PRIMARY KEY,
                          firstname VARCHAR(255) NOT NULL,
                          lastname VARCHAR(255) NOT NULL,
                          middlename VARCHAR(255) NOT NULL,
                          phone VARCHAR(255)
);

CREATE TABLE prescriptions (
                          id BIGINT IDENTITY PRIMARY KEY,
                          description VARCHAR(512),
                          doctorID BIGINT NOT NULL,
                          patientID BIGINT NOT NULL,
                          startDate DATE NOT NULL,
                          duration INTEGER NOT NULL,
                          priority INTEGER,
                          FOREIGN KEY (doctorID) REFERENCES doctors(id),
                          FOREIGN KEY (patientID) REFERENCES patients(id)
);