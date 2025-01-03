package dk.sdu.sem4.pro.datamanager.insert;


import dk.sdu.sem4.pro.commondata.data.*;
import dk.sdu.sem4.pro.datamanager.connection.Conn;
import dk.sdu.sem4.pro.datamanager.hash.Hashing;
import dk.sdu.sem4.pro.commondata.services.IInsert;
import dk.sdu.sem4.pro.datamanager.select.SelectBatch;
import dk.sdu.sem4.pro.datamanager.select.SelectData;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.*;

public class InsertData implements IInsert {
    private int insertIntoDBSecure (String table, Map<String,Object> attributes) throws SQLException, IOException {
        Conn conn = new Conn();
        int id = 0;
        try(var connection = conn.getConnection()) {
            var TableSQL = "insert into " + table + " (";
            var ValuesSQL = "values (";
            boolean notfirst = false;
            for(Map.Entry<String,Object> attribute : attributes.entrySet()) {
                if(notfirst) TableSQL += ", ";
                TableSQL += attribute.getKey();
                if(notfirst) ValuesSQL += ", ";
                ValuesSQL += "?";
                notfirst = true;
            }
            TableSQL += ") ";
            ValuesSQL += ")";
            var sql = TableSQL+ValuesSQL;
            var insertSQL = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int i = 0;
            for(Map.Entry<String,Object> attribute : attributes.entrySet()) {
                i++;
                insertSQL.setObject(i, attribute.getValue());
            }
            if(insertSQL.executeUpdate() > 0) {
                var generatedKeys = insertSQL.getGeneratedKeys();
                if(generatedKeys.next()) { id = generatedKeys.getInt(1); }
            }
            insertSQL.close();
        } catch (SQLException e) {
            id = -2;
            System.out.println("insertIntoDBSecure: " + e.getMessage());
        }
        return id;
    }

    /**
     * @param batch
     * @return ID
     */
    @Override
    public int addBatch(Batch batch) {
        int id = 0;
        try {
            SelectData selectData = new SelectData();
            System.out.println("addBatch: "+batch);
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("priority", batch.getPriority());
            attributes.put("description", batch.getDescription());
            attributes.put("amount", batch.getAmount());
            attributes.put("component_id", batch.getProduct().getProduct().getId());
            id = insertIntoDBSecure("batch", attributes);
            //if(batch.getProduct() != null) addProduct(batch.getProduct());
            if(!batch.getLog().isEmpty()){
                for(Logline logline : batch.getLog()) {
                    addLogline(id, logline);
                }
            }
        }
        catch (IOException | SQLException e){
            id = -1;
            System.out.println("addBatch: " + e.getMessage());
        }
        return id;
    }

    /**
     * @param batchID
     * @param logline
     * @return ID
     */
    @Override
    public int addLogline(int batchID, Logline logline) {
        int id = 0;
        try {
            SelectData selectData = new SelectData();

            Batch batch = selectData.getBatch(batchID);
            if(batch.getProduct() != null) {
                Map<String, Object> attributes = new HashMap<>();
                attributes.put("type", logline.getType());
                attributes.put("description", logline.getDescription());
                attributes.put("dateTime", Timestamp.from(Instant.now()));
                attributes.put("Batch_ID", logline.getBatchID());
                //System.out.println("addLogline: " + attributes);
                id = insertIntoDBSecure("logline", attributes);
            }
        }
        catch (IOException | SQLException e){
            id = -1;
            System.out.println("addLogline: " + e.getMessage());
        }
        return id;
    }

    /**
     * @param component
     * @return ID
     */
    @Override
    public int addComponent(Component component) {
        int id = 0;
        System.out.println("Component to be inserted: "+component.getName());
        try {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("name", component.getName());
            attributes.put("wishedAmount", component.getWishedAmount());
            id = insertIntoDBSecure("component", attributes);
        }
        catch (IOException | SQLException e){
            id = -1;
            System.out.println("addComponent: " + e.getMessage());
        }
        return id;
    }

    /**
     * @param recipe
     * @return Id List
     */
    @Override
    public List<Integer> addProduct(Recipe recipe) {
        List<Integer> ids = new ArrayList<>();
        try {
            for(Map.Entry<Component, Integer> component : recipe.getComponentMap().entrySet()){
                if(component.getKey().getId() != 0) {
                    Map<String, Object> attributes = new HashMap<>();
                    attributes.put("amount", component.getValue());
                    attributes.put("timeestimation", recipe.getTimeEstimation());
                    attributes.put("product_component_ID", recipe.getProduct().getId());
                    attributes.put("material_component_ID", component.getKey().getId());
                    ids.add(insertIntoDBSecure("recipe", attributes));
                }
            }
        }
        catch (IOException | SQLException e){
            ids.add(-1);
            System.out.println("addProduct: " + e.getMessage());
        }
        return ids;
    }

    /**
     * @param unit
     * @return ID
     */
    @Override
    public int addUnit(Unit unit) {
        int id = 0;
        try {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("state", unit.getState());
            attributes.put("type", unit.getType());
            id = insertIntoDBSecure("units", attributes);
            if(unit.getInventory() != null) addUnitInvetory(id, unit.getInventory());
        }
        catch (IOException | SQLException e){
            id = -1;
            System.out.println("addUnit: " + e.getMessage());
        }
        return id;
    }

    /**
     * @param unitID 
     * @param inventory
     * @return Id List
     */
    @Override
    public List<Integer> addUnitInvetory(int unitID, Inventory inventory) {
        List<Integer> ids = new ArrayList<>();
        try {
            for(Map.Entry<Component, Integer> component : inventory.getComponentList().entrySet()) {
                if(component.getKey().getId() != 0){
                    Map<String, Object> attributes = new HashMap<>();
                    attributes.put("amount", component.getValue());
                    attributes.put("Units_ID", unitID);
                    attributes.put("Component_ID", component.getKey().getId());
                    ids.add(insertIntoDBSecure("unitinventory", attributes));
                }
            }
        }
        catch (IOException | SQLException e){
            ids.add(-1);
            System.out.println("addUnitInvetory: " + e.getMessage());
        }
        return ids;
    }

    @Override
    public int addUnitComponentInvetory(int id, int trayid, Inventory inventory) {
        int ids = 0;
        try {
            for(Map.Entry<Component, Integer> component : inventory.getComponentList().entrySet()) {
                if(component.getKey().getId() != 0){
                    Map<String, Object> attributes = new HashMap<>();
                    attributes.put("amount", component.getValue());
                    attributes.put("trayid", trayid);
                    attributes.put("Units_ID", id);
                    attributes.put("Component_ID", component.getKey().getId());
                    ids = insertIntoDBSecure("unitinventory", attributes);
                }
            }
        }
        catch (IOException | SQLException e){
            ids = -1;
            System.out.println("addUnitComponentInvetory: " + e.getMessage());
        }
        return ids;
    }

    /**
     * @param agv
     * @return ID
     */
    @Override
    public int addAGV(AGV agv) {
        int id = 0;
        try {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("state", agv.getState());
            attributes.put("type", agv.getType());
            attributes.put("chargevalue", agv.getChargeValue());
            attributes.put("mincharge", agv.getMinCharge());
            attributes.put("maxcharge", agv.getMaxCharge());
            id = insertIntoDBSecure("agv", attributes);
            if(agv.getInventory() != null) addAGVInvetory(id, agv.getInventory());
        }
        catch (IOException | SQLException e){
            id = -1;
            System.out.println("addAGV: " + e.getMessage());
        }
        return id;
    }

    /**
     * @param agvID 
     * @param inventory
     * @return Id List
     */
    @Override
    public List<Integer> addAGVInvetory(int agvID, Inventory inventory) {
        List<Integer> ids = new ArrayList<>();
        try {
            for(Map.Entry<Component, Integer> component : inventory.getComponentList().entrySet()) {
                if(component.getKey().getId() != 0){
                    Map<String, Object> attributes = new HashMap<>();
                    attributes.put("amount", component.getValue());
                    attributes.put("Units_ID", agvID);
                    attributes.put("Component_ID", component.getKey().getId());
                    ids.add(insertIntoDBSecure("agvinventory", attributes));
                }
            }
        }
        catch (IOException | SQLException e){
            System.out.println("addAGVInvetory: " + e.getMessage());
        }
        return ids;
    }

    /**
     * @param userGroup 
     * @return ID
     */
    @Override
    public int addUserGroup(String userGroup) {
        int id = 0;
        try {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("name", userGroup);
            id = insertIntoDBSecure("UserGroup", attributes);
        }
        catch (IOException | SQLException e){
            id = -1;
            System.out.println("addUserGroup: " + e.getMessage());
        }
        return id;
    }

    /**
     * @param user
     * @return ID
     */
    @Override
    public int addUser(User user) {
        int id = 0;
        try {
        Map<String, Object> attributes = new HashMap<>();
            attributes.put("name", Map.of("String", user.getName()));
            Hashing hashing = new Hashing();
            attributes.put("password", Map.of("String", hashing.hash(user.getPassword())));
            attributes.put("UserGroup_ID", Map.of("Integer", user.getUserGroup()));
            id = insertIntoDBSecure("Component", attributes);
        } catch (IOException | SQLException e) {
            id = -1;
            System.out.println("addUser: " + e.getMessage());
        }
        return id;
    }
}
