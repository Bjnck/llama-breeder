create table if not exists user (
    id int unsigned not null auto_increment,
    name varchar(255),
    primary key (id)
);

create table if not exists user_registration (
    id bigint unsigned not null auto_increment,
    user_id int unsigned not null,
    registration_key varchar(255) not null unique,
    primary key (id),
    foreign key (user_id) REFERENCES user(id)
);

create table if not exists user_details (
    user_id int unsigned not null,
    version int unsigned not null default 0,
    level tinyint(1) unsigned not null default 0, check (level between  0 and 10),
    coins int unsigned not null default 0,
    last_purchase_timestamp timestamp,
    primary key (user_id),
    foreign key (user_id) REFERENCES user(id)
);

create table if not exists item (
    id bigint unsigned not null auto_increment,
    user_id int unsigned not null,
    version int unsigned not null default 0,
    code enum('NEST', 'LOVE', 'HUNGER', 'THIRST') not null,
    quality tinyint(1) unsigned not null default 10, check (quality between  1 and 10),
    life int unsigned not null default 100, check (life between  1 and 100),
    primary key (id),
    foreign key (user_id) REFERENCES user(id)
);

create table if not exists shop (
    id int unsigned not null auto_increment,
    code enum('NEST', 'LOVE', 'HUNGER', 'THIRST') not null,
    quality tinyint(1) unsigned not null, check (quality between  1 and 10),
    coins int unsigned not null,
    primary key (id),
    unique key code_quality (code,quality)
);
