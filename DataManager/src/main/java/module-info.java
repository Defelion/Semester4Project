import dk.sdu.sem4.pro.services.IDelete;
import dk.sdu.sem4.pro.services.IInsert;
import dk.sdu.sem4.pro.services.ISelect;
import dk.sdu.sem4.pro.services.IUpdate;

module DataManager {
    exports dk.sdu.sem4.pro.select;
    requires CommonData;
    requires flyway.core;
    requires java.sql;
    requires java.desktop;
    requires jhash;
    provides IUpdate with dk.sdu.sem4.pro.update.UpdateData;
    provides IDelete with dk.sdu.sem4.pro.delete.DeleteData;
    provides IInsert with dk.sdu.sem4.pro.insert.InsertData;
    provides ISelect with dk.sdu.sem4.pro.select.SelectData;
}