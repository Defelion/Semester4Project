package dk.sdu.sem4.pro.delete;

import dk.sdu.sem4.pro.connection.Conn;
import dk.sdu.sem4.pro.data.*;
import dk.sdu.sem4.pro.services.IDelete;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteData implements IDelete {
    private boolean exitenceTest (String table, int id, Connection conn) throws SQLException {
        boolean exist = false;
        var sql = "select * from ? where id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, table);
        ps.setInt(2, id);
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            exist = true;
        }
        rs.close();
        ps.close();
        return exist;
    }
    /**
     * @param batchID
     * @return success
     */
    @Override
    public boolean deleteBatch(int batchID) throws IOException {
        boolean succes = false;
        Conn conn = new Conn();
        try(Connection connection = conn.getConnection()) {
            if(exitenceTest("batch", batchID, connection)) {
                var sql = "DELETE FROM batch WHERE id=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, batchID);
                ps.executeUpdate();
                succes = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return succes;
    }

    /**
     * @param loglineID
     * @return success
     */
    @Override
    public boolean deleteLogline(int loglineID) throws IOException {
        boolean succes = false;
        Conn conn = new Conn();
        try(Connection connection = conn.getConnection()) {
            if(exitenceTest("logline", loglineID, connection)) {
                var sql = "DELETE FROM logline WHERE id=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, loglineID);
                ps.executeUpdate();
                succes = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return succes;
    }

    /**
     * @param componentID
     * @return success
     */
    @Override
    public boolean deleteComponent(int componentID) throws IOException {
        boolean succes = false;
        Conn conn = new Conn();
        try(Connection connection = conn.getConnection()) {
            if(exitenceTest("component", componentID, connection)) {
                var sql = "DELETE FROM component WHERE id=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, componentID);
                ps.executeUpdate();
                succes = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return succes;
    }

    /**
     * @param recipeID
     * @return success
     */
    @Override
    public boolean deleteProduct(int recipeID) throws IOException {
        boolean succes = false;
        Conn conn = new Conn();
        try(Connection connection = conn.getConnection()) {
            if(exitenceTest("product", recipeID, connection)) {
                var sql = "DELETE FROM recipe WHERE id=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, recipeID);
                ps.executeUpdate();
                succes = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return succes;
    }

    /**
     * @param unitID
     * @return success
     */
    @Override
    public boolean deleteUnit(int unitID) throws IOException {
        boolean succes = false;
        Conn conn = new Conn();
        try(Connection connection = conn.getConnection()) {
            if(exitenceTest("unit", unitID, connection)) {
                var sql = "DELETE FROM units WHERE id=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, unitID);
                ps.executeUpdate();
                succes = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return succes;
    }

    @Override
    public boolean deleteUnitInventory(int inventoryID) throws IOException {
        boolean succes = false;
        Conn conn = new Conn();
        try(Connection connection = conn.getConnection()) {
            if(exitenceTest("inventory", inventoryID, connection)) {
                var sql = "DELETE FROM unitinventory WHERE id=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, inventoryID);
                ps.executeUpdate();
                succes = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return succes;
    }

    @Override
    public boolean deleteAGVInventory(int inventoryID) throws IOException {
        boolean succes = false;
        Conn conn = new Conn();
        try(Connection connection = conn.getConnection()) {
            if(exitenceTest("inventory", inventoryID, connection)) {
                var sql = "DELETE FROM agvinventory WHERE id=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, inventoryID);
                ps.executeUpdate();
                succes = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return succes;
    }

    /**
     * @param agvID
     * @return success
     */
    @Override
    public boolean deleteAGV(int agvID) throws IOException {
        boolean succes = false;
        Conn conn = new Conn();
        try(Connection connection = conn.getConnection()) {
            if(exitenceTest("agv", agvID, connection)) {
                var sql = "DELETE FROM agv WHERE id=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, agvID);
                ps.executeUpdate();
                succes = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return succes;
    }

    /**
     * @param userID
     * @return success
     */
    @Override
    public boolean deleteUser(int userID) throws IOException {
        boolean succes = false;
        Conn conn = new Conn();
        try(Connection connection = conn.getConnection()) {
            if(exitenceTest("user", userID, connection)) {
                var sql = "DELETE FROM users WHERE id=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, userID);
                ps.executeUpdate();
                succes = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return succes;
    }
}
