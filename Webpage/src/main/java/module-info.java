import dk.sdu.sem4.pro.commondata.services.*;

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
    requires org.apache.tomcat.embed.core;
    requires thymeleaf;
    requires DataManager;
    requires OperationManager;
    uses IDelete;
    uses IHash;
    uses ISelect;
    uses IInsert;
    uses IUpdate;
    uses dk.sdu.sem4.pro.common.services.IProduction;
}