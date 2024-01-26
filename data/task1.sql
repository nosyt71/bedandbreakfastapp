-- Write your Task 1 answers in this file
drop database if exists bedandbreakfast;

create database bedandbreakfast;

use bedandbreakfast;

create table users (
    email varchar(128) not null,
    name varchar(128) not null,

    primary key(email)
);

create table bookings (
    booking_id char(8) not null,
    listing_id varchar(20) not null,
    duration int not null,
    email varchar(128) not null,

    primary key(booking_id),
    constraint foreign key(email) references users(email)
);

create table reviews (
    id int not null auto_increment,
    date date not null,
    listing_id varchar(20) not null,
    reviewer_name varchar(64) not null,
    comments text not null,

    primary key(id)
);

BULK INSERT users
FROM C:\Users\Huawei\paf_assessment_template\bedandbreakfastapp\data\task1.sql
WITH (firstrow = 2,
      fieldterminator = ',',
      rowterminator = '\n',
      ROWS_PER_BATCH = 10000,
      TABLOCK
      )