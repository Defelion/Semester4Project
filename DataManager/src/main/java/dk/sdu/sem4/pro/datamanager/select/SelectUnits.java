package dk.sdu.sem4.pro.datamanager.select;

import dk.sdu.sem4.pro.datamanager.connection.Conn;
import dk.sdu.sem4.pro.commondata.data.AGV;
import dk.sdu.sem4.pro.commondata.data.Component;
import dk.sdu.sem4.pro.commondata.data.Inventory;
import dk.sdu.sem4.pro.commondata.data.Unit;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SelectUnits {
    public Inventory getInventory() throws IOException {
        Inventory selectedInventory = new Inventory();
        Conn conn = new Conn();
        try (Connection connection = conn.getConnection()) {
            var sql = "select c.id, c.name, a.amount, u.amount from agvinventory a " +
                    "right join component c on c.id = a.component_id " +
                    "left join unitinventory u on c.id = u.component_id " +
                    "order by c.id";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            int componentcount = 0;
            Component component = new Component(0);
            while (rs.next()) {
                if(component.getId() != rs.getInt("component_id")) {
                    if (componentcount != 0) selectedInventory.addComponent(component, componentcount);
                    component.setId(rs.getInt("c.id"));
                    component.setName(rs.getString("c.name"));
                }
                componentcount += rs.getInt("a.amount");
                componentcount += rs.getInt("u.amount");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedInventory;
    }

    public Inventory getInventoryByComponent(Component component) throws IOException {
        Inventory selectedInventory = new Inventory();
        Conn conn = new Conn();
        try (Connection connection = conn.getConnection()) {
            var sql = "select c.id, c.name, a.amount, u.amount from agvinventory a " +
                    "right join component c on c.id = a.component_id " +
                    "left join unitinventory u on c.id = u.component_id ";
            if(component.getId() != 0) {
                sql += " where c.id = ?";
            }
            else if(component.getName() != null) {
                sql += " where c.name = ?";
            }
            sql += "order by c.id ";
            PreparedStatement ps = connection.prepareStatement(sql);
            if(component.getId() != 0) {
                ps.setInt(1, component.getId());
            }
            else if(component.getName() != null) {
                ps.setString(1, component.getName());
            }
            ResultSet rs = ps.executeQuery();
            int componentcount = 0;
            Component selectedComponent = new Component(0);
            while (rs.next()) {
                if(selectedComponent.getId() != rs.getInt("component_id")) {
                    if (componentcount != 0) selectedInventory.addComponent(selectedComponent, componentcount);
                    selectedComponent.setId(rs.getInt("c.id"));
                    selectedComponent.setName(rs.getString("c.name"));
                }
                componentcount += rs.getInt("a.amount");
                componentcount += rs.getInt("u.amount");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedInventory;
    }

    public Inventory getInventoryByUnit(Unit unit, Boolean isUnit) throws IOException {
        Inventory selectedInventory = new Inventory();
        Conn conn = new Conn();
        try (Connection connection = conn.getConnection()) {
            var sql = getSQLString(unit, isUnit);
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            int componentcount = 0;
            Component component = new Component(0);
            while (rs.next()) {
                if(component.getId() != rs.getInt("component_id")) {
                    if (componentcount != 0) selectedInventory.addComponent(component, componentcount);
                    component.setId(rs.getInt("c.id"));
                    component.setName(rs.getString("c.name"));
                }
                componentcount += rs.getInt("i.amount");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedInventory;
    }

    private String getSQLString (Unit unit, Boolean isUnit) throws IOException {
        var sql = "select c.id, c.name, i.amount ";
        if(unit.getId() == 0){
            if(isUnit) {
                sql += "from units u ";
                sql += "right join unitinventory i on u.id = i.units_id";
                sql += "right join component c on c.id = i.component_id";
                sql += "where u.type == " + unit.getType();
            }
            else {
                sql += "from agv u ";
                sql += "right join agvinventory i on u.id = i.units_id";
                sql += "right join component c on c.id = i.component_id";
            }
        }
        else if (isUnit) {
            sql += "from units u ";
            sql += "right join unitinventory i on u.id = i.units_id";
            sql += "right join component c on c.id = i.component_id";
            sql += "where u.id == " + unit.getId();
        }
        else {
            sql += "from agv u ";
            sql += "right join agvinventory i on u.id = i.units_id";
            sql += "right join component c on c.id = i.component_id";
            sql += "where u.id == " + unit.getId();
        }
        return sql;
    }

    public Unit getUnit(int unitID) throws IOException {
        Unit selectedUnit = null;
        Conn conn = new Conn();
        try (Connection con = conn.getConnection()) {
            var sql = "select * " +
                    "from units u " +
                    "where id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, unitID);
            ResultSet rs = ps.executeQuery();
            selectedUnit = new Unit();
            while (rs.next()) {
                selectedUnit.setId(rs.getInt("id"));
                selectedUnit.setType(rs.getString("type"));
                selectedUnit.setState(rs.getString("state"));
                selectedUnit.setInventory(getInventoryByUnit(new Unit(unitID), true));;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedUnit;
    }

    public List<Unit> getAllUnits(String type) throws IOException {
        List<Unit> selectedUnits = new ArrayList<>();
        Conn conn = new Conn();
        try (Connection connection = conn.getConnection()) {
            var sql = "select * from units where type = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                selectedUnits.add(new Unit(
                        rs.getInt("id"),
                        rs.getString("type"),
                        rs.getString("state"),
                        getInventoryByUnit(new Unit(rs.getInt("id")), true)
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedUnits;
    }

    public AGV getAGV(int unitID) throws IOException {
        AGV selectedAGV = null;
        Conn conn = new Conn();
        try (Connection connection = conn.getConnection()) {
            var sql = "select * from agv where id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, unitID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                selectedAGV = new AGV(
                        rs.getInt("id"),
                        rs.getString("type"),
                        rs.getString("state"),
                        rs.getInt("chargevalue"),
                        rs.getDate("changeddatetime"),
                        rs.getDate("checkdattime"),
                        rs.getDouble("mincharge"),
                        rs.getDouble("maxcharge"),
                        getInventoryByUnit(new Unit(rs.getInt("id")), false)
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedAGV;
    }

    public List<AGV> getAllAGVs() throws IOException {
        List<AGV> selectedAGVs = new ArrayList<>();
        Conn conn = new Conn();
        try (Connection connection = conn.getConnection()) {
            var sql = "select * from units";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                selectedAGVs.add(new AGV(
                        rs.getInt("id"),
                        rs.getString("type"),
                        rs.getString("state"),
                        rs.getInt("chargevalue"),
                        rs.getDate("changeddatetime"),
                        rs.getDate("checkdattime"),
                        rs.getDouble("mincharge"),
                        rs.getDouble("maxcharge"),
                        getInventoryByUnit(new Unit(rs.getInt("id")), false)
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedAGVs;
    }
}
