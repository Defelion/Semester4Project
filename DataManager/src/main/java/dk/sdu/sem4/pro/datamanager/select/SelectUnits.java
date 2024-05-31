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
            var sql = "select c.id, c.name, c.wishedamount, a.id, a.amount, u.id, u.amount from agvinventory a " +
                    "right join component c on c.id = a.component_id " +
                    "left join unitinventory u on c.id = u.component_id " +
                    "order by c.id";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            int componentcount = 0;
            Component selectedComponent = new Component(0);
            int agvid = 0;
            int unitid = 0;
            while (rs.next()) {
                if(selectedComponent.getId() != rs.getInt("component_id")) {
                    if (componentcount != 0) selectedInventory.addComponent(selectedComponent, componentcount);
                    componentcount = 0;
                    agvid = 0;
                    unitid = 0;
                    selectedComponent.setId(rs.getInt("c.id"));
                    selectedComponent.setName(rs.getString("c.name"));
                    selectedComponent.setWishedAmount(rs.getInt("c.wishedamount"));
                }
                if(agvid != rs.getInt("a.id")) {
                    agvid = rs.getInt("a.id");
                    componentcount += rs.getInt("a.amount");
                }
                if(unitid != rs.getInt("u.id")) {
                    unitid = rs.getInt("u.id");
                    componentcount += rs.getInt("u.amount");
                }
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedInventory;
    }

    public Inventory getInventoryByUnitAndComponent(Unit unit, Component component) throws IOException {
        Inventory selectedInventory = new Inventory();
        Conn conn = new Conn();
        try (Connection connection = conn.getConnection()) {
            var sql = "select c.id, c.name, c.wishedamount, i.id, i.trayid, u.type, u.id from component c " +
                    "left join unitinventory i on c.id = i.component_id " +
                    "right join units u on u.id = i.units_id " +
                    "where c.name = ? and u.id = ? " +
                    "order by c.id;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, component.getName());
            ps.setInt(2, unit.getId());
            ResultSet rs = ps.executeQuery();
            Component selectedComponent = new Component(0);
            while (rs.next()) {
                selectedInventory.setId(rs.getInt("i.id"));
                selectedComponent.setId(rs.getInt("c.id"));
                selectedComponent.setName(rs.getString("c.name"));
                selectedComponent.setWishedAmount(rs.getInt("c.wishedamount"));
                selectedInventory.addComponent(selectedComponent, rs.getInt("i.trayid"));
                break;
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedInventory;
    }

    public Inventory getInventoryByComponent(Component component) throws IOException {
        Inventory selectedInventory = new Inventory();
        Conn conn = new Conn();
        try (Connection connection = conn.getConnection()) {
            var sql = "select c.id, c.name, c.wishedamount, a.id, a.amount, u.id, u.amount from agvinventory a " +
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
            int agvid = 0;
            int unitid = 0;
            while (rs.next()) {
                if(selectedComponent.getId() != rs.getInt("component_id")) {
                    if (componentcount != 0) selectedInventory.addComponent(selectedComponent, componentcount);
                    componentcount = 0;
                    agvid = 0;
                    unitid = 0;
                    selectedComponent.setId(rs.getInt("c.id"));
                    selectedComponent.setName(rs.getString("c.name"));
                    selectedComponent.setWishedAmount(rs.getInt("c.wishedamount"));
                }
                if(agvid != rs.getInt("a.id")) {
                    agvid = rs.getInt("a.id");
                    componentcount += rs.getInt("a.amount");
                }
                if(unitid != rs.getInt("u.id")) {
                    unitid = rs.getInt("u.id");
                    componentcount += rs.getInt("u.amount");
                }
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedInventory;
    }

    public Inventory getInventoryByUnit(Unit unit) throws IOException {
        Inventory selectedInventory = new Inventory();
        Conn conn = new Conn();
        System.out.println("getInventoryByUnit input: " + unit.getId());
        try (Connection connection = conn.getConnection()) {
            var sql = "select c.id, c.name, c.wishedamount, i.amount, u.id, u.type, i.trayid " +
                    "from units u " +
                    "right join unitinventory i on u.id = i.units_id" +
                    "right join component c on c.id = i.component_id";
            if(unit.getId() == 0) sql += "where u.type = '?'";
            else sql += "where u.id = '?'";
            sql += " order by c.id";
            PreparedStatement ps = connection.prepareStatement(sql);
            if(unit.getId() == 0) { ps.setString(1, unit.getType()); }
            else ps.setInt(1, unit.getId());
            ResultSet rs = ps.executeQuery();
            Component selectedComponent = new Component(0);
            while (rs.next()) {
                if(selectedComponent.getId() != rs.getInt("c.id")) {
                    selectedComponent.setId(rs.getInt("c.id"));
                    selectedComponent.setName(rs.getString("c.name"));
                    selectedComponent.setWishedAmount(rs.getInt("c.wishedamount"));
                }
                selectedInventory.addComponent(selectedComponent, rs.getInt("i.trayid"));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedInventory;
    }

    public Inventory getInventoryByAGV(AGV agv) throws IOException {
        Inventory selectedInventory = new Inventory();
        Conn conn = new Conn();
        try (Connection connection = conn.getConnection()) {
            var sql = "select c.id, c.name, c.wishedamount, i.amount, u.id, u.type " +
                    "from agv u " +
                    "right join agvinventory i on u.id = i.agv_id " +
                    "right join component c on c.id = i.component_id ";
            if(agv.getId() != 0) sql += "where u.id = '?'";
            PreparedStatement ps = connection.prepareStatement(sql);
            if(agv.getId() != 0) ps.setInt(1, agv.getId());
            ResultSet rs = ps.executeQuery();
            Component selectedComponent = new Component(0);
            while (rs.next()) {
                if(selectedComponent.getId() != rs.getInt("c.id")) {
                    selectedComponent.setId(rs.getInt("c.id"));
                    selectedComponent.setName(rs.getString("c.name"));
                    selectedComponent.setWishedAmount(rs.getInt("c.wishedamount"));
                }
                selectedInventory.addComponent(selectedComponent, rs.getInt("i.amount"));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedInventory;
    }

    public Unit getUnit(int unitID) throws IOException {
        Unit selectedUnit = new Unit();
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
            }
            ps.close();
            rs.close();
            selectedUnit.setInventory(getInventoryByUnit(selectedUnit));;
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
            int rows = 0;
            while (rs.next()) {
                rows++;
                selectedUnits.add(new Unit(
                        rs.getInt("id"),
                        rs.getString("state"),
                        rs.getString("type")
                ));
            }
            rs.close();
            ps.close();
            if(rows > 0) {
                for (Unit unit : selectedUnits) {
                    unit.setInventory(getInventoryByUnit(unit));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedUnits;
    }

    public AGV getAGV(int unitID) throws IOException {
        AGV selectedAGV = new AGV();
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
                        getInventoryByAGV(new AGV(rs.getInt("id")))
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedAGV;
    }

    public List<AGV> getAllAGVs() throws IOException {
        List<AGV> selectedAGVs = new ArrayList<>();
        //System.out.println("Getting all AGVs");
        Conn conn = new Conn();
        try (Connection connection = conn.getConnection()) {
            var sql = "select * from agv";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            System.out.println("ResultSet: "+rs.getFetchSize());
            int rows = 0;
            while (rs.next()) {
                rows += 1;
                System.out.println("Rows: "+rows);
                AGV selectedAGV = new AGV();
                selectedAGV.setId(rs.getInt("id"));
                selectedAGV.setState(rs.getString("state"));
                selectedAGV.setType(rs.getString("type"));
                selectedAGV.setChargeValue(rs.getInt("chargevalue"));
                selectedAGV.setMinCharge(rs.getDouble("mincharge"));
                selectedAGV.setMaxCharge(rs.getDouble("maxcharge"));
                selectedAGV.setChangedDateTime(rs.getDate("changeddatetime"));
                selectedAGV.setCheckDateTime(rs.getDate("checkdattime"));
                System.out.println("id: "+selectedAGV.getId());
                System.out.println("state: "+selectedAGV.getState());
                System.out.println("type: "+selectedAGV.getType());
                System.out.println("chargevalue: "+selectedAGV.getChargeValue());
                System.out.println("mincharge: "+selectedAGV.getMinCharge());
                System.out.println("maxcharge: "+selectedAGV.getMaxCharge());
                System.out.println("changeddatetime: "+selectedAGV.getChangedDateTime());
                System.out.println("checkdattime: "+selectedAGV.getCheckDateTime());
                selectedAGVs.add(selectedAGV);
            }
            rs.close();
            ps.close();
            System.out.println("Amount of AGV"+selectedAGVs.size());
            for (AGV agv : selectedAGVs) {
                System.out.println("AGV: "+agv.getId());
                agv.setInventory(getInventoryByAGV(agv));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return selectedAGVs;
    }
}
