module ServiceApplication {
    requires Common;
    requires java.datatransfer;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    opens dk.sdu.sem4.pro.serviceapplication to javafx.graphics;
    uses dk.sdu.sem4.pro.common.services.IProduction;
    exports dk.sdu.sem4.pro.serviceapplication;
}