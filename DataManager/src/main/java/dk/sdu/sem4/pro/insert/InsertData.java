package dk.sdu.sem4.pro.insert;


import dk.sdu.sem4.pro.connection.Conn;
import dk.sdu.sem4.pro.data.*;
import dk.sdu.sem4.pro.data.Component;
import dk.sdu.sem4.pro.hash.Hashing;
import dk.sdu.sem4.pro.services.IInsert;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.*;

import static java.util.Objects.hash;

public class InsertData implements IInsert {
    private int insertIntoDBSecure (String table, Map<String,Object> attributes) throws SQLException, IOException {
        Conn conn = new Conn();
        int id = 0;
        try(var connection = conn.getConnection()) {
            var TableSQL = "insert into " + table + "(";
            var ValuesSQL = "values (";
            for(Map.Entry<String,Object> attribute : attributes.entrySet()) {
                TableSQL += attribute.getKey() + ", ";
                ValuesSQL += "?, ";
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
        } catch (SQLException e) {
            id = -2;
            throw new SQLException(e);
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
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("priority", batch.getPriority());
            attributes.put("description", batch.getDescription());
            attributes.put("amount", batch.getAmount());
            id = insertIntoDBSecure("Batch", attributes);
            if(batch.getProduct() != null) addProduct(batch.getProduct());
            if(!batch.getLog().isEmpty()){
                for(Logline logline : batch.getLog()) {
                    addLogline(id, logline);
                }
            }
        }
        catch (IOException | SQLException e){
            id = -1;
            throw new RuntimeException(e);
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
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("type", logline.getType());
            attributes.put("description", logline.getDescription());
            attributes.put("dateTime", logline.getDate());
            attributes.put("Batch_ID", batchID);
            id = insertIntoDBSecure("LogLine", attributes);
        }
        catch (IOException | SQLException e){
            id = -1;
            throw new RuntimeException(e);
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
        try {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("name", component.getName());
            attributes.put("wishedAmount", component.getWishedAmount());
            id = insertIntoDBSecure("Component", attributes);
        }
        catch (IOException | SQLException e){
            id = -1;
            throw new RuntimeException(e);
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
                    attributes.put("amount", Map.of("Integer", component.getValue()));
                    attributes.put("timeEstimation", recipe.getTimeEstimation());
                    attributes.put("Product_Component_ID", recipe.getProduct());
                    attributes.put("Material_Component_ID", component.getKey().getId());
                    ids.add(insertIntoDBSecure("Product", attributes));
                }
            }
        }
        catch (IOException | SQLException e){
            ids = null;
            throw new RuntimeException(e);
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
            id = insertIntoDBSecure("Units", attributes);
            if(unit.getInventory() != null) addUnitInvetory(id, unit.getInventory());
        }
        catch (IOException | SQLException e){
            id = -1;
            throw new RuntimeException(e);
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
                    ids.add(insertIntoDBSecure("UnitInventory", attributes));
                }
            }
        }
        catch (IOException | SQLException e){
            ids.add(-1);
            throw new RuntimeException(e);
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
            attributes.put("chargeValue", agv.getChargeValue());
            attributes.put("minCharge", agv.getMinCharge());
            attributes.put("maxCharge", agv.getMaxCharge());
            attributes.put("checkDateTime", agv.getCheckDateTime());
            attributes.put("changeDateTime", agv.getChangedDateTime());
            id = insertIntoDBSecure("AGV", attributes);
            if(agv.getInventory() != null) addAGVInvetory(id, agv.getInventory());
        }
        catch (IOException | SQLException e){
            id = -1;
            throw new RuntimeException(e);
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
                    ids.add(insertIntoDBSecure("AGVInventory", attributes));
                }
            }
        }
        catch (IOException | SQLException e){
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
        }
        return id;
    }
}
