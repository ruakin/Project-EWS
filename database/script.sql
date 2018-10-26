/*SQLITE3-specific*/
PRAGMA foreign_keys = ON;
/*end SQLITE3-specific*/

/****************/
/*drop everthing*/
/****************/
drop table measurement;
drop table message;
drop table hospitalization;
drop table patient;
drop table room;
drop table station;
drop table employee;

/***************/
/*create tables*/
/***************/
CREATE TABLE message (
  messageid INTEGER primary key autoincrement,
  content varchar(256) not null
);

CREATE TABLE employee(
  employeeid integer primary key autoincrement,
  name varchar(50) not null,
  surname varchar(50) not null,
  email varchar(150) unique not null,
  type varchar(50) not null,
  active tinyint(3) not null
);

CREATE TABLE patient(
  patientid integer primary key autoincrement,
  name varchar(50),
  surname varchar(50),
  birthday varchar(25)
);

CREATE TABLE room(
  roomid integer primary key autoincrement,
  stationid integer(10) not null,
  beds smallint(5) check (beds >= 0 and beds is not null),
  name varchar(10),
  foreign key (stationid) references station(stationid)
);

CREATE TABLE station(
  stationid integer primary key autoincrement,
  nurseid integer(10) not null,
  name varchar(50) not null unique,
  foreign key (nurseid) references employee(employeeid)
);

CREATE TABLE hospitalization(
  hospid integer primary key autoincrement,
  patientid integer(10) not null,
  roomid integer(10) not null,
  doctorid integer(10) not null,
  start varchar(25) not null,
  end varchar(25),
  maxtemp real(10) not null,
  mintemp real(10) not null,
  maxsystolic real(10) not null,
  mindiastolic real(10) not null,
  maxbreathrate real(10) not null,
  minbreathrate real(10) not null,
  foreign key (patientid) references patient(patientid),
  foreign key (roomid) references room(roomid),
  foreign key (doctorid) references employee(employeeid)
);

CREATE TABLE measurement(
  measureid integer primary key autoincrement,
  hospid integer(10) not null,
  messageid integer(10),
  temp real(10) not null,
  breathrate real(10) not null,
  systolic real(10) not null,
  diastolic real(10) not null,
  time varchar(25) not null,
  foreign key (hospid) references hospitalization(hospid),
  foreign key (messageid) references message(messageid)
);
/*****************/
/*create triggers*/
/*****************/


/*****************/
/*insert employee*/
/*****************/
insert into employee(name, surname, email, type, active)
values ('Default', 'Doctor', 'doctor@durban-it.de', 'doctor', 1);
insert into employee(name, surname, email, type, active)
values ('Normal', 'Nurse', 'nurse@durban-it.de', 'nurse', 1);

/****************/
/*insert station*/
/****************/
insert into station(nurseid, name)
select employeeid, 'Abteilung A'
from employee
where email = 'hilberts505@gmail.com' and type = 'nurse';
insert into station(nurseid, name)
select employeeid, 'Abteilung B'
from employee
where email = 'hilberts505@gmail.com' and type = 'nurse';
insert into station(nurseid, name)
select employeeid, 'Abteilung C'
from employee
where email = 'hilberts505@gmail.com' and type = 'nurse';
insert into station(nurseid, name)
select employeeid, 'Abteilung D'
from employee
where email = 'hilberts505@gmail.com' and type = 'nurse';
insert into station(nurseid, name)
select employeeid, 'Abteilung E'
from employee
where email = 'hilberts505@gmail.com' and type = 'nurse';

/*************/
/*insert room*/
/*************/
/*insert Abteilung A* - 53 beds*/
insert into room(stationid, beds, name)
select stationid, 1, 'A101'
from station
where name = 'Abteilung A';
insert into room(stationid, beds, name)
select stationid, 2, 'A102'
from station
where name = 'Abteilung A';
insert into room(stationid, beds, name)
select stationid, 3, 'A103'
from station
where name = 'Abteilung A';
insert into room(stationid, beds, name)
select stationid, 4, 'A104'
from station
where name = 'Abteilung A';
insert into room(stationid, beds, name)
select stationid, 2, 'A105'
from station
where name = 'Abteilung A';
insert into room(stationid, beds, name)
select stationid, 3, 'A106'
from station
where name = 'Abteilung A';
insert into room(stationid, beds, name)
select stationid, 1, 'A107'
from station
where name = 'Abteilung A';
insert into room(stationid, beds, name)
select stationid, 2, 'A108'
from station
where name = 'Abteilung A';
insert into room(stationid, beds, name)
select stationid, 5, 'A109'
from station
where name = 'Abteilung A';
insert into room(stationid, beds, name)
select stationid, 4, 'A110'
from station
where name = 'Abteilung A';
insert into room(stationid, beds, name)
select stationid, 2, 'A111'
from station
where name = 'Abteilung A';
insert into room(stationid, beds, name)
select stationid, 3, 'A112'
from station
where name = 'Abteilung A';
insert into room(stationid, beds, name)
select stationid, 1, 'A113'
from station
where name = 'Abteilung A';
insert into room(stationid, beds, name)
select stationid, 3, 'A114'
from station
where name = 'Abteilung A';
insert into room(stationid, beds, name)
select stationid, 3, 'A115'
from station
where name = 'Abteilung A';
insert into room(stationid, beds, name)
select stationid, 4, 'A116'
from station
where name = 'Abteilung A';
insert into room(stationid, beds, name)
select stationid, 2, 'A117'
from station
where name = 'Abteilung A';
insert into room(stationid, beds, name)
select stationid, 5, 'A118'
from station
where name = 'Abteilung A';
insert into room(stationid, beds, name)
select stationid, 2, 'A119'
from station
where name = 'Abteilung A';
insert into room(stationid, beds, name)
select stationid, 1, 'A120'
from station
where name = 'Abteilung A';

/*insert Abteilung B - 49 beds*/
insert into room(stationid, beds, name)
select stationid, 1, 'B101'
from station
where name = 'Abteilung B';
insert into room(stationid, beds, name)
select stationid, 4, 'B102'
from station
where name = 'Abteilung B';
insert into room(stationid, beds, name)
select stationid, 2, 'B103'
from station
where name = 'Abteilung B';
insert into room(stationid, beds, name)
select stationid, 1, 'B104'
from station
where name = 'Abteilung B';
insert into room(stationid, beds, name)
select stationid, 3, 'B105'
from station
where name = 'Abteilung B';
insert into room(stationid, beds, name)
select stationid, 5, 'B106'
from station
where name = 'Abteilung B';
insert into room(stationid, beds, name)
select stationid, 1, 'B107'
from station
where name = 'Abteilung B';
insert into room(stationid, beds, name)
select stationid, 1, 'B108'
from station
where name = 'Abteilung B';
insert into room(stationid, beds, name)
select stationid, 2, 'B109'
from station
where name = 'Abteilung B';
insert into room(stationid, beds, name)
select stationid, 2, 'B110'
from station
where name = 'Abteilung B';
insert into room(stationid, beds, name)
select stationid, 4, 'B111'
from station
where name = 'Abteilung B';
insert into room(stationid, beds, name)
select stationid, 2, 'B112'
from station
where name = 'Abteilung B';
insert into room(stationid, beds, name)
select stationid, 3, 'B113'
from station
where name = 'Abteilung B';
insert into room(stationid, beds, name)
select stationid, 1, 'B114'
from station
where name = 'Abteilung B';
insert into room(stationid, beds, name)
select stationid, 5, 'B115'
from station
where name = 'Abteilung B';
insert into room(stationid, beds, name)
select stationid, 4, 'B116'
from station
where name = 'Abteilung B';
insert into room(stationid, beds, name)
select stationid, 2, 'B117'
from station
where name = 'Abteilung B';
insert into room(stationid, beds, name)
select stationid, 2, 'B118'
from station
where name = 'Abteilung B';
insert into room(stationid, beds, name)
select stationid, 3, 'B119'
from station
where name = 'Abteilung B';
insert into room(stationid, beds, name)
select stationid, 1, 'B120'
from station
where name = 'Abteilung B';

/*insert Abteilung C - 55 beds*/
insert into room(stationid, beds, name)
select stationid, 2, 'C101'
from station
where name = 'Abteilung C';
insert into room(stationid, beds, name)
select stationid, 3, 'C102'
from station
where name = 'Abteilung C';
insert into room(stationid, beds, name)
select stationid, 4, 'C103'
from station
where name = 'Abteilung C';
insert into room(stationid, beds, name)
select stationid, 2, 'C104'
from station
where name = 'Abteilung C';
insert into room(stationid, beds, name)
select stationid, 5, 'C105'
from station
where name = 'Abteilung C';
insert into room(stationid, beds, name)
select stationid, 1, 'C106'
from station
where name = 'Abteilung C';
insert into room(stationid, beds, name)
select stationid, 2, 'C107'
from station
where name = 'Abteilung C';
insert into room(stationid, beds, name)
select stationid, 5, 'C108'
from station
where name = 'Abteilung C';
insert into room(stationid, beds, name)
select stationid, 3, 'C109'
from station
where name = 'Abteilung C';
insert into room(stationid, beds, name)
select stationid, 4, 'C110'
from station
where name = 'Abteilung C';
insert into room(stationid, beds, name)
select stationid, 2, 'C111'
from station
where name = 'Abteilung C';
insert into room(stationid, beds, name)
select stationid, 1, 'C112'
from station
where name = 'Abteilung C';
insert into room(stationid, beds, name)
select stationid, 1, 'C113'
from station
where name = 'Abteilung C';
insert into room(stationid, beds, name)
select stationid, 3, 'C114'
from station
where name = 'Abteilung C';
insert into room(stationid, beds, name)
select stationid, 5, 'C115'
from station
where name = 'Abteilung C';
insert into room(stationid, beds, name)
select stationid, 2, 'C116'
from station
where name = 'Abteilung C';
insert into room(stationid, beds, name)
select stationid, 4, 'C117'
from station
where name = 'Abteilung C';
insert into room(stationid, beds, name)
select stationid, 1, 'C118'
from station
where name = 'Abteilung C';
insert into room(stationid, beds, name)
select stationid, 2, 'C119'
from station
where name = 'Abteilung C';
insert into room(stationid, beds, name)
select stationid, 3, 'C120'
from station
where name = 'Abteilung C';

/*insert Abteilung D - 58 beds*/
insert into room(stationid, beds, name)
select stationid, 2, 'D101'
from station
where name = 'Abteilung D';
insert into room(stationid, beds, name)
select stationid, 5, 'D102'
from station
where name = 'Abteilung D';
insert into room(stationid, beds, name)
select stationid, 5, 'D103'
from station
where name = 'Abteilung D';
insert into room(stationid, beds, name)
select stationid, 3, 'D104'
from station
where name = 'Abteilung D';
insert into room(stationid, beds, name)
select stationid, 1, 'D105'
from station
where name = 'Abteilung D';
insert into room(stationid, beds, name)
select stationid, 2, 'D106'
from station
where name = 'Abteilung D';
insert into room(stationid, beds, name)
select stationid, 3, 'D107'
from station
where name = 'Abteilung D';
insert into room(stationid, beds, name)
select stationid, 4, 'D108'
from station
where name = 'Abteilung D';
insert into room(stationid, beds, name)
select stationid, 1, 'D109'
from station
where name = 'Abteilung D';
insert into room(stationid, beds, name)
select stationid, 3, 'D110'
from station
where name = 'Abteilung D';
insert into room(stationid, beds, name)
select stationid, 2, 'D111'
from station
where name = 'Abteilung D';
insert into room(stationid, beds, name)
select stationid, 5, 'D112'
from station
where name = 'Abteilung D';
insert into room(stationid, beds, name)
select stationid, 2, 'D113'
from station
where name = 'Abteilung D';
insert into room(stationid, beds, name)
select stationid, 4, 'D114'
from station
where name = 'Abteilung D';
insert into room(stationid, beds, name)
select stationid, 1, 'D115'
from station
where name = 'Abteilung D';
insert into room(stationid, beds, name)
select stationid, 4, 'D116'
from station
where name = 'Abteilung D';
insert into room(stationid, beds, name)
select stationid, 3, 'D117'
from station
where name = 'Abteilung D';
insert into room(stationid, beds, name)
select stationid, 1, 'D118'
from station
where name = 'Abteilung D';
insert into room(stationid, beds, name)
select stationid, 2, 'D119'
from station
where name = 'Abteilung D';
insert into room(stationid, beds, name)
select stationid, 5, 'D120'
from station
where name = 'Abteilung D';

/*insert Abteilung E - 61 beds*/
insert into room(stationid, beds, name)
select stationid, 5, 'E101'
from station
where name = 'Abteilung E';
insert into room(stationid, beds, name)
select stationid, 5, 'E102'
from station
where name = 'Abteilung E';
insert into room(stationid, beds, name)
select stationid, 2, 'E103'
from station
where name = 'Abteilung E';
insert into room(stationid, beds, name)
select stationid, 4, 'E104'
from station
where name = 'Abteilung E';
insert into room(stationid, beds, name)
select stationid, 1, 'E105'
from station
where name = 'Abteilung E';
insert into room(stationid, beds, name)
select stationid, 3, 'E106'
from station
where name = 'Abteilung E';
insert into room(stationid, beds, name)
select stationid, 5, 'E107'
from station
where name = 'Abteilung E';
insert into room(stationid, beds, name)
select stationid, 4, 'E108'
from station
where name = 'Abteilung E';
insert into room(stationid, beds, name)
select stationid, 2, 'E109'
from station
where name = 'Abteilung E';
insert into room(stationid, beds, name)
select stationid, 1, 'E110'
from station
where name = 'Abteilung E';
insert into room(stationid, beds, name)
select stationid, 1, 'E111'
from station
where name = 'Abteilung E';
insert into room(stationid, beds, name)
select stationid, 3, 'E112'
from station
where name = 'Abteilung E';
insert into room(stationid, beds, name)
select stationid, 4, 'E113'
from station
where name = 'Abteilung E';
insert into room(stationid, beds, name)
select stationid, 3, 'E114'
from station
where name = 'Abteilung E';
insert into room(stationid, beds, name)
select stationid, 3, 'E115'
from station
where name = 'Abteilung E';
insert into room(stationid, beds, name)
select stationid, 1, 'E116'
from station
where name = 'Abteilung E';
insert into room(stationid, beds, name)
select stationid, 4, 'E117'
from station
where name = 'Abteilung E';
insert into room(stationid, beds, name)
select stationid, 2, 'E118'
from station
where name = 'Abteilung E';
insert into room(stationid, beds, name)
select stationid, 3, 'E119'
from station
where name = 'Abteilung E';
insert into room(stationid, beds, name)
select stationid, 5, 'E120'
from station
where name = 'Abteilung E';

/*****************************************************************************/
/****************************spielwiese***************************************/
/*****************************************************************************/


/*insert into patient(name, surname, birthday) values ('0', '0', '0');*/
/*insert into hospitalization(patientid, roomid, doctorid, start, end, maxtemp, mintemp, maxsystolic, mindiastolic, maxbreathrate, minbreathrate)
values (<patientid>, <roomid>, <doctorid>, <start>, null, <maxtemp>, <mintemp>, <mintemp>, <maxsystolic>, <mindiastolic>, <maxbreathrate>, <minbreathrate>);
*/
