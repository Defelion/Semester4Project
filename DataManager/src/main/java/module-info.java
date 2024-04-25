import dk.sdu.sem4.pro.commondata.services.IDelete;
import dk.sdu.sem4.pro.commondata.services.IInsert;
import dk.sdu.sem4.pro.commondata.services.ISelect;
import dk.sdu.sem4.pro.commondata.services.IUpdate;
import dk.sdu.sem4.pro.commondata.services.IHash;
import dk.sdu.sem4.pro.datamanager.delete.DeleteData;
import dk.sdu.sem4.pro.datamanager.insert.InsertData;
import dk.sdu.sem4.pro.datamanager.select.SelectData;
import dk.sdu.sem4.pro.datamanager.update.UpdateData;
import dk.sdu.sem4.pro.datamanager.hash.Hashing;

module DataManager {
    requires CommonData;
    requires flyway.core;
    requires java.sql;
    requires java.desktop;
    requires jhash;
    provides IUpdate with UpdateData;
    provides IDelete with DeleteData;
    provides IInsert with InsertData;
    provides ISelect with SelectData;
    provides IHash with Hashing;
}