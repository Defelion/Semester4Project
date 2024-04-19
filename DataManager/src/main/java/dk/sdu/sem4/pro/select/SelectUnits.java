package dk.sdu.sem4.pro.select;

import dk.sdu.sem4.pro.connection.Conn;
import dk.sdu.sem4.pro.data.Component;
import dk.sdu.sem4.pro.data.Inventory;
import dk.sdu.sem4.pro.data.Unit;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SelectUnits {
    public Inventory getAllInventory() throws IOException {
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

    public Inventory getInventoryByUnit(Unit unit) throws IOException {
        Inventory selectedInventory = new Inventory();
        Conn conn = new Conn();
        try (Connection connection = conn.getConnection()) {
            var sql = "select c.id, c.name, a.amount, i.amount ";
            switch (unit.getType()) {
                case "Warehouse":
                    sql += "from units u ";
                    sql += "right join unitinventory i on u.id = i.units_id";
                    sql += "right join component c on c.id = i.component_id";
                    sql += "where u.type == " + unit.getType();
                    break;

                case "Assembly":
                    sql += "from units u ";
                    sql += "right join unitinventory i on u.id = i.units_id";
                    sql += "right join component c on c.id = i.component_id";
                    sql += "where u.type == " + unit.getType();
                    break;

                case "AGV":
                    sql += "from agv a ";
                    sql += "right join agvinventory i on u.id = i.units_id";
                    sql += "right join component c on c.id = i.component_id";
                    break;
            }
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

    public Unit getUnit(int unitID) throws IOException {
        Unit selectedUnit = null;
        Conn conn = new Conn();
        try (Connection con = conn.getConnection()) {
            var sql = "select * from units where id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, unitID);
            ResultSet rs = ps.executeQuery();
            selectedUnit = new Unit();
            while (rs.next()) {
                selectedUnit.setId(rs.getInt("id"));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedUnit;
    }
}
