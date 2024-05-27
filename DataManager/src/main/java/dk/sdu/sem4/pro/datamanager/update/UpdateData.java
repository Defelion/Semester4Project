package dk.sdu.sem4.pro.datamanager.update;

import dk.sdu.sem4.pro.commondata.data.*;
import dk.sdu.sem4.pro.datamanager.connection.Conn;
import dk.sdu.sem4.pro.commondata.services.IUpdate;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;

public class UpdateData implements IUpdate {
    @Override
    public boolean updateBatch(Batch batch) throws IOException {
        boolean success = false;
        Conn conn = new Conn();
        try (Connection connection = conn.getConnection()) {
           var sql = "update batch " +
                    "set priority = ?, " +
                    "description = ?, " +
                    "amount = ? " +
                    "where id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, batch.getPriority());
            ps.setString(2, batch.getDescription());
            ps.setDouble(3, batch.getAmount());
            ps.setInt(4, batch.getId());
            ps.executeUpdate();
            success = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return success;
    }

    @Override
    public boolean updateLogline(Logline logline) throws IOException {
        boolean success = false;
        Conn conn = new Conn();
        try (Connection connection = conn.getConnection()) {
            var sql = "update logLine " +
                    "set type = ?, " +
                    "description = ?, " +
                    "datetime = ?, " +
                    "batch_id = ?" +
                    "where id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, logline.getType());
            ps.setString(2, logline.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(logline.getDate().toString()));
            ps.setInt(4, logline.getId());
            ps.executeUpdate();
            success = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return success;
    }

    @Override
    public boolean updateComponent(Component component) throws IOException {
        boolean success = false;
        Conn conn = new Conn();
        try (Connection connection = conn.getConnection()) {
            var sql = "update component " +
                    "set name = ?, " +
                    "wishedamount = ?" +
                    "where id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, component.getName());
            ps.setDouble(2, component.getWishedAmount());
            ps.setInt(3, component.getId());
            ps.executeUpdate();
            success = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return success;
    }

    @Override
    public boolean updateProduct(Recipe recipe) throws IOException {
        boolean success = false;
        Conn conn = new Conn();
        try (Connection connection = conn.getConnection()) {
            var sql = "update recipe " +
                    "set amount = ?, " +
                    "timeestimation = ?, " +
                    "material_component_id = ?" +
                    "where product_component_id = ?";
            for (Map.Entry<Component, Integer> material : recipe.getComponentMap().entrySet()) {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setDouble(1, material.getValue());
                ps.setDouble(2, recipe.getTimeEstimation());
                ps.setInt(3, material.getKey().getId());
                ps.setInt(4, recipe.getProduct().getId());
                ps.executeUpdate();
                ps.close();
            }
            success = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return success;
    }

    @Override
    public boolean updateUnit(Unit unit) throws IOException {
        boolean success = false;
        Conn conn = new Conn();
        try (Connection connection = conn.getConnection()) {
            var sql = "update units " +
                    "set state = ?, " +
                    "type = ?" +
                    "where id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, unit.getState());
            ps.setString(2, unit.getType());
            ps.setInt(3, unit.getId());
            ps.executeUpdate();
            success = updateUnitInventory(unit.getId(), unit.getInventory());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return success;
    }

    @Override
    public boolean updateAGV(AGV agv) throws IOException {
        boolean success = false;
        Conn conn = new Conn();
        System.out.println("updating agv");
        try (Connection connection = conn.getConnection()) {
            var sql = "update agv " +
                    "set state = ?, " +
                    "type = ?, " +
                    "chargevalue = ?, " +
                    "mincharge = ?, " +
                    "maxcharge = ?, " +
                    "checkdatetime = ?, " +
                    "changedatetime = ?" +
                    "where id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, agv.getState());
            ps.setString(2, agv.getType());
            ps.setDouble(3, agv.getChargeValue());
            ps.setDouble(4, agv.getMinCharge());
            ps.setDouble(5, agv.getMaxCharge());
            ps.setTimestamp(6, Timestamp.valueOf(agv.getCheckDateTime().toString()));
            ps.setTimestamp(7, Timestamp.valueOf(agv.getChangedDateTime().toString()));
            ps.setInt(8, agv.getId());
            ps.executeUpdate();
            success = updateAGVInventory(agv.getId(), agv.getInventory());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return success;
    }

    private boolean updateAGVInventory (int agvID, Inventory inventory) throws IOException {
        boolean success = false;
        Conn conn = new Conn();
        try (Connection connection = conn.getConnection()) {
            var sql = "update agvinventory " +
                    "set amount = ?, " +
                    "component_id = ?" +
                    "where agv_id = ?";
            for (Map.Entry<Component, Integer> component : inventory.getComponentList().entrySet()) {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, component.getValue());
                ps.setInt(2, component.getKey().getId());
                ps.setInt(3, agvID);
                ps.executeUpdate();
            }
            success = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return success;
    }

    private boolean updateUnitInventory (int unitID, Inventory inventory) throws IOException {
        boolean success = false;
        Conn conn = new Conn();
        try (Connection connection = conn.getConnection()) {
            var sql = "update unitinventory " +
                    "set amount = ?, " +
                    "component_id = ?" +
                    "where units_id = ?";
            for (Map.Entry<Component, Integer> component : inventory.getComponentList().entrySet()) {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, component.getValue());
                ps.setInt(2, component.getKey().getId());
                ps.setInt(3, unitID);
                ps.executeUpdate();
            }
            success = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return success;
    }

    @Override
    public boolean updateUser(User user) throws IOException {
        boolean success = false;
        Conn conn = new Conn();
        try (Connection connection = conn.getConnection()) {
            var sql = "update users " +
                    "set name = ?, " +
                    "password = ?, " +
                    "usergroup_id = ?" +
                    "where id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user.getName());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getUserGroup().getId());
            ps.setInt(4, user.getId());
            ps.executeUpdate();
            success = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return success;
    }

    @Override
    public boolean updateUserGroup(UserGroup userGroup) throws IOException {
        boolean success = false;
        Conn conn = new Conn();
        try (Connection connection = conn.getConnection()) {
            var sql = "update usergroup " +
                    "set name = ?" +
                    "where id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, userGroup.getName());
            ps.setInt(2, userGroup.getId());
            ps.executeUpdate();
            success = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return success;
    }
}
