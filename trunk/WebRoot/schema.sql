
use trackmytasks;

drop table if exists users;
create table users(
user_id integer auto_increment primary key,
user_name varchar(255) not null,
password varchar(255) not null,
email varchar(255) not null,
creation_date datetime,
creation_person_id integer,
lastupdate_date datetime,
lastupdate_perdon_id integer) ENGINE=INNODB;

create table project(
project_id integer auto_increment primary key,
project_name varchar(255) not null,
description text,
creation_date datetime,
creation_person_id integer,
lastupdate_date datetime,
lastupdate_perdon_id integer,
FOREIGN KEY(creation_person_id) REFERENCES users(user_id) ON UPDATE CASCADE
)ENGINE=INNODB;

create table projectusers(
project_id integer not null,
user_id integer not null,
creation_date datetime,
creation_person_id integer,
lastupdate_date datetime,
lastupdate_perdon_id integer,
primary key (project_id,user_id)
)ENGINE=INNODB;

create table tasklist(
tasklist_id integer auto_increment primary key,
tasklist_name varchar(255) not null,
project_id integer not null,
description text,
creation_date datetime,
creation_person_id integer,
lastupdate_date datetime,
lastupdate_perdon_id integer,
FOREIGN KEY(creation_person_id) REFERENCES users(user_id) ON UPDATE CASCADE,
FOREIGN KEY(project_id) REFERENCES project(project_id) ON UPDATE CASCADE
)ENGINE=INNODB;

create table tasks(
task_id integer auto_increment primary key,
task_name varchar(255) not null,
tasklist_id integer not null,
assigned_person integer not null,
description text,
start_datetime datetime,
expected_end_datetime datetime,
creation_date datetime,
creation_person_id integer,
lastupdate_date datetime,
lastupdate_perdon_id integer,
FOREIGN KEY(assigned_person) REFERENCES users(user_id) ON UPDATE CASCADE,
FOREIGN KEY(creation_person_id) REFERENCES users(user_id) ON UPDATE CASCADE,
FOREIGN KEY(tasklist_id) REFERENCES tasklist(tasklist_id) ON UPDATE CASCADE
)ENGINE=INNODB;