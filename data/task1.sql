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

LOAD DATA LOCAL INFILE C:\Users\Huawei\paf_assessment_template\bedandbreakfastapp\data\task1.sql
INTO TABLE users
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
('date','listing_id','reviewer_name','comments);