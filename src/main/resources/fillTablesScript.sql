/*INSERT INTO DOCTORS(firstname, lastname, middleName, specialization) VALUES
('Иван', 'Иванов', 'Иванович', 'Терапевт'),
('Пётр', 'Петров', 'Петрович', 'Офтальмолог'),
('Алексей', 'Алексеев', 'Алексеевич', 'Лор'),
('Дмитрий', 'Дмитриев', 'Дмитриевич', 'Психотерапевт');*/

/*INSERT INTO PATIENTS (firstname, lastname, middleName, phone) VALUES
('James', 'Franko', 'M.', '55502952'),
('Marco', 'Polo', 'K.', '55577604'),
('Tomas', 'Anderson','Jr.', '55514968');*/

INSERT INTO PRESCRIPTIONS (description, doctorID, patientID, startDate, duration, priority)
VALUES
('drug Depilix use 2 tablets per day, 10 days', 1, 2, DATE '2019-05-17', 10, 0),
('drug Sparcion use 1 tablet after dinner, 8 days', 1, 0, DATE '2019-05-14', 8, 1),
('drug Giogin use 4 tablets every 6 hours, 12 days', 2, 2, DATE '2019-05-20', 12, 2);