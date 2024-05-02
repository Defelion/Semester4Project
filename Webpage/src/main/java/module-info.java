open module Webpage {
    requires CommonData;
    requires Common;
    requires spring.context;
    requires spring.web;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires java.sql;
    requires spring.core;
    requires spring.beans;

    uses dk.sdu.sem4.pro.commondata.services.IInsert;
    uses dk.sdu.sem4.pro.commondata.services.ISelect;
    uses dk.sdu.sem4.pro.common.services.IProduction;
    uses dk.sdu.sem4.pro.commondata.services.IDelete;
    uses dk.sdu.sem4.pro.commondata.services.IUpdate;
    uses dk.sdu.sem4.pro.commondata.services.IHash;
}