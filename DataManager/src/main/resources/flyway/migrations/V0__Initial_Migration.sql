create table Component (
    ID serial not null unique primary key,
    name varchar not null,
    wishedAmount integer not null
);

create table LogLine (
    ID serial not null unique primary key,
    type VARCHAR not null,
    description text default '',
    dateTime timestamp,
    Component_ID integer not null references Component
);

create table Recipe (
    ID serial not null unique primary key,
    amount integer not null,
    timeEstimation float not null,
    Product_Component_ID integer not null references Component,
    Material_Component_ID integer not null references Component
);

create table Units (
    ID serial not null unique primary key,
    state varchar not null,
    type varchar not null
);

create table UnitInventory (
    ID serial not null unique primary key,
    amount integer not null,
    Units_ID integer not null references Units,
    Component_ID integer not null references Component
);

create table AGV (
    ID serial not null unique primary key,
    state varchar not null,
    type varchar not null,
    chargeValue float not null,
    minCharge float not null,
    maxCharge float not null,
    checkDateTime timestamp,
    changeDateTime timestamp
);

create table AGVInventory (
      ID serial not null unique primary key,
      amount integer not null,
      AGV_ID integer not null references AGV,
      Component_ID integer not null references Component
);

create table UserGroup (
    ID serial not null unique primary key,
    name varchar not null
);

create table User (
    ID serial not null unique primary key,
    name varchar not null,
    password varchar not null,
    UserGroup_ID integer not null references UserGroup
)