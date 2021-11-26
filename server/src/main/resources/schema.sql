create table if not exists user (
    id int not null auto_increment,
    name varchar(15) not null default 'Breeder',
    uid varchar(255) unique not null,
    issuer varchar(255) not null,
    email varchar(255),
    deleted tinyint(1) not null default 0,
    primary key (id)
);

create table if not exists user_details (
    user_id int not null,
    version bigint not null default 0,
    level tinyint(1) not null default 0, check (level between 0 and 8),
    coins int not null default 0, check (coins >= 0),
    points int not null default 0, check (points >= 0),
    last_purchase_timestamp timestamp,
    last_capture_timestamp timestamp,
    primary key (user_id),
    foreign key (user_id) REFERENCES user(id)
);

create table if not exists item (
    id bigint not null auto_increment,
    user_id int not null,
    version bigint not null default 0,
    code enum('NET', 'LOVE', 'HUNGER', 'THIRST') not null,
    quality tinyint(1) not null, check (quality between 1 and 10),
    life int not null default 500, check (life between 0 and 500),
    pen_activation_time datetime,
    primary key (id),
    foreign key (user_id) REFERENCES user(id)
);

create table if not exists shop (
    id int not null auto_increment,
    code enum('NET', 'LOVE', 'HUNGER', 'THIRST') not null,
    quality tinyint(1) not null, check (quality between 1 and 10),
    coins int not null, check (coins >= 0),
    primary key (id),
    unique key code_quality (code, quality)
);

create table if not exists color (
    id int not null auto_increment,
    code varchar(7) not null unique,
    name varchar(100) not null unique,
    generation tinyint(1) not null, check (generation between 1 and 8),
    parent_code varchar(8) unique,
    primary key (id)
);

create table if not exists gene (
    id int not null auto_increment,
    code varchar(20) not null unique,
    special tinyint(1) not null,
    primary key (id)
);

create table if not exists creature_info (
    id bigint not null auto_increment,
    sex enum('M', 'F') not null,
    color_one_id int not null,
    color_two_id int,
    gene_one_id int,
    gene_two_id int,
    primary key (id),
    foreign key (color_one_id) REFERENCES color(id),
    foreign key (color_two_id) REFERENCES color(id),
    foreign key (gene_one_id) REFERENCES gene(id),
    foreign key (gene_two_id) REFERENCES gene(id)
);

create table if not exists creature (
    id bigint not null auto_increment,
    user_id int not null,
    original_user_id int not null,
    version bigint not null default 0,
    info_id bigint not null,
    generation tinyint(1) not null default 1, check (generation between 1 and 8),
    parent_one_info_id bigint,
    parent_two_info_id bigint,
    name varchar(15) not null default 'Llama',
    create_date date not null,
    wild boolean not null,
    pen_activation_time datetime,
    pregnant boolean not null default 0,
    breeding_count tinyint(1) not null default 0, check (breeding_count between 0 and 10),
    pregnancy_male_info_id bigint,
    pregnancy_start_time datetime,
    pregnancy_end_time datetime,
    energy_update_time datetime not null,
    energy int not null default 1000, check (energy between 0 and 1000),
    love tinyint(1) not null default 0, check (love between 0 and 100),
    thirst tinyint(1) not null default 0, check (thirst between 0 and 100),
    hunger tinyint(1) not null default 0, check (hunger between 0 and 100),
    maturity int not null default 0, check (maturity between 0 and 10000),
    primary key (id),
    foreign key (user_id) REFERENCES user(id),
    foreign key (original_user_id) REFERENCES user(id),
    foreign key (info_id) REFERENCES creature_info(id),
    foreign key (parent_one_info_id) REFERENCES creature_info(id),
    foreign key (parent_two_info_id) REFERENCES creature_info(id),
    foreign key (pregnancy_male_info_id) REFERENCES creature_info(id)
);

create table if not exists capture (
    id bigint not null auto_increment,
    user_id int not null,
    creature_info_id bigint,
    version bigint not null default 0,
    quality tinyint(1) not null default 0, check (quality between 0 and 3),
    start_time datetime not null,
    end_time datetime not null,
    primary key (id),
    foreign key (user_id) REFERENCES user(id),
    foreign key (creature_info_id) REFERENCES creature_info(id)
);

create table if not exists pen (
    id int not null auto_increment,
    user_id int not null,
    version bigint not null default 0,
    size int not null default 3, check (size between 3 and 20),
    primary key (id),
    foreign key (user_id) REFERENCES user(id)
);

create table if not exists pen_item (
    id bigint not null auto_increment,
    pen_id int not null,
    item_id bigint not null unique,
    primary key (id),
    foreign key (pen_id) REFERENCES pen(id),
    foreign key (item_id) REFERENCES item(id)
);

create table if not exists pen_creature (
    id bigint not null auto_increment,
    pen_id int not null,
    creature_id bigint not null unique,
    primary key (id),
    foreign key (pen_id) REFERENCES pen(id),
    foreign key (creature_id) REFERENCES creature(id)
);

create table if not exists collection (
    id int not null auto_increment,
    user_id int not null,
    color_id int not null,
    version bigint not null default 0,
    christmas tinyint(1) not null default 0,
    primary key (id),
    foreign key (user_id) REFERENCES user(id),
    foreign key (color_id) REFERENCES color(id),
    unique key user_color (user_id, color_id)
);
