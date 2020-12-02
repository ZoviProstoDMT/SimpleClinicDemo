-- creating the doctors table
create table DOCTORS
(
    id BIGINT identity
        constraint DOCTORS_PK
        primary key,
    FIRSTNAME varchar(20) not null,
    LASTNAME varchar(20) not null,
    MIDDLENAME varchar(20),
    SPECIALIZATION varchar(30) not null
);