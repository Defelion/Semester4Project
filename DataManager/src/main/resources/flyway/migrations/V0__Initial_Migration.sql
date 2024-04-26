create table Batch (
    ID serial not null unique primary key,
    priority integer not null,
    description text default '',
    amount integer not null
);

create table LogLine (
    ID serial not null unique primary key,
    type VARCHAR not null,
    description text default '',
    dateTime timestamp,
    Batch_ID integer not null,
    foreign key (Batch_ID)
         references Batch(ID)
         on delete cascade
);

create table Component (
   ID serial not null unique primary key,
   name varchar not null,
   wishedAmount integer not null
);

create table Recipe (
    ID serial not null unique primary key,
    amount integer not null,
    timeEstimation float not null,
    Product_Component_ID integer not null,
    Material_Component_ID integer not null,
    foreign key (Product_Component_ID)
        references Component(ID)
        on delete cascade,
    foreign key (Material_Component_ID)
        references Component(ID)
        on delete cascade
);

create table Units (
    ID serial not null unique primary key,
    state varchar not null,
    type varchar not null
);

create table UnitInventory (
    ID serial not null unique primary key,
    amount integer not null,
    Units_ID integer not null,
    Component_ID integer not null,
    foreign key (Units_ID)
        references Units(ID)
        on delete cascade,
    foreign key (Component_ID)
        references Component(ID)
        on delete cascade
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
      AGV_ID integer not null,
      Component_ID integer not null,
      foreign key (AGV_ID)
          references AGV(ID)
          on delete cascade,
      foreign key (Component_ID)
          references Component(ID)
          on delete cascade
);

create table UserGroup (
    ID serial not null unique primary key,
    name varchar not null
);

create table Users (
    ID serial not null unique primary key,
    name varchar not null,
    password varchar not null,
    UserGroup_ID integer not null,
    foreign key (UserGroup_ID)
        references UserGroup(ID)
        on delete cascade
);