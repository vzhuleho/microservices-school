-- liquibase formatted sql

-- changeset andrei:1

create table ADDRESS (
    ID bigint not null auto_increment,
    CITY varchar(255) not null default 'N/A',
    STREET varchar(255) not null default 'N/A',
    HOUSE varchar(255) not null default 'N/A',
    APARTMENT varchar(255) not null default 'N/A',
    ZIP_CODE varchar(255) not null default 'N/A',
    PHONE_NUMBER varchar(255) not null default 'N/A',
    EMAIL varchar(255) not null default 'N/A',
    primary key (ID)
);

create table USER (
    ID bigint not null auto_increment,
    USER_TYPE varchar(255) not null,
    NAME varchar(255) not null unique,
    STATUS varchar(255) not null default 'INACTIVE',
    ADDRESS_ID bigint not null,
    primary key (id),
    foreign key (ADDRESS_ID) references ADDRESS(ID)
);

create table PARENT (
    ID bigint not null,
    primary key (ID),
    foreign key (ID) references USER(ID)
);

create table PUPIL (
    ID bigint not null,
    BIRTH_DATE date not null default sysdate(),
    GRADE int not null default 1,
    primary key (ID),
    foreign key (ID) references USER(ID)
);

create table FAMILY_RELATIONSHIP (
    PUPIL_ID bigint not null,
    PARENT_ID bigint not null,
    foreign key (PUPIL_ID) references PUPIL(ID),
    foreign key (PARENT_ID) references PARENT(ID)
);

create table PRINCIPAL (
    ID bigint not null,
    primary key (ID),
    foreign key (ID) references USER(ID)
);

create table TEACHER (
    ID bigint not null,
    PASSPORT_NUMBER varchar(255) not null default 'N/A',
    SALARY decimal not null default 0.0,
    BONUS decimal not null default 0.0,
    primary key (ID),
    foreign key (ID) references USER(ID)
);