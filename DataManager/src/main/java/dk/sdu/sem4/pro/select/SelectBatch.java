package dk.sdu.sem4.pro.select;

import dk.sdu.sem4.pro.connection.Conn;
import dk.sdu.sem4.pro.data.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SelectBatch {
    public Logline getLogLine (int logLineID) throws IOException {
        Logline logLine = null;
        Conn conn = new Conn();
        try (Connection connection = conn.getConnection()) {
            var sql = "SELECT * FROM logline WHERE ID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, logLineID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                logLine = new Logline(
                        rs.getInt("id"),
                        rs.getString("description"),
                        rs.getDate("datetime"),
                        rs.getString("type"),
                        rs.getInt("batch_id")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return logLine;
    }

    public List<Logline> getLogLines () throws IOException {
        List<Logline> logLines = null;
        Conn conn = new Conn();
        try (Connection connection = conn.getConnection()) {
            var sql = "SELECT * FROM logline order by datetime desc";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                logLines.add(new Logline(
                        rs.getInt("id"),
                        rs.getString("description"),
                        rs.getDate("datetime"),
                        rs.getString("type"),
                        rs.getInt("batch_id")
                    )
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return logLines;
    }

    public List<Logline> getBatchLog (int batchID) throws IOException {
        List<Logline> logLines = null;
        Conn conn = new Conn();
        try (Connection connection = conn.getConnection()) {
            var sql = "SELECT * FROM logline where batch_id = ? order by datetime desc";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, batchID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                logLines.add(new Logline(
                                rs.getInt("id"),
                                rs.getString("description"),
                                rs.getDate("datetime"),
                                rs.getString("type"),
                                rs.getInt("batch_id")
                        )
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return logLines;
    }

    public Batch getBatch (int batchID) throws IOException {
        Batch batch = null;
        Conn conn = new Conn();
        try (Connection connection = conn.getConnection()) {
            var sql = "SELECT * FROM batch where id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, batchID);
            ResultSet rs = ps.executeQuery();
            SelectComponent selectComponent = new SelectComponent();
            while (rs.next()) {
                batch = new Batch(
                        rs.getInt("id"),
                        selectComponent.getRecipe(new Recipe(rs.getInt("id"))),
                        rs.getInt("amount"),
                        rs.getString("description"),
                        rs.getInt("priority"),
                        getBatchLog(rs.getInt("id"))
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return batch;
    }

    public Batch getBatchWithHigestPriority () throws IOException {
        Batch batch = null;
        Conn conn = new Conn();
        try (Connection connection = conn.getConnection()) {
            var sql = "SELECT * FROM batch order by priority desc limit 1";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            SelectComponent selectComponent = new SelectComponent();
            while (rs.next()) {
                batch = new Batch(
                        rs.getInt("id"),
                        selectComponent.getRecipe(new Recipe(rs.getInt("id"))),
                        rs.getInt("amount"),
                        rs.getString("description"),
                        rs.getInt("priority"),
                        getBatchLog(rs.getInt("id"))
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return batch;
    }

    public List<Batch> getAllBatch () throws IOException {
        List<Batch> batchs = null;
        Conn conn = new Conn();
        try (Connection connection = conn.getConnection()) {
            var sql = "SELECT * FROM batch";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            SelectComponent selectComponent = new SelectComponent();
            while (rs.next()) {
                batchs.add(new Batch(
                        rs.getInt("id"),
                        selectComponent.getRecipe(new Recipe(rs.getInt("id"))),
                        rs.getInt("amount"),
                        rs.getString("description"),
                        rs.getInt("priority"),
                        getBatchLog(rs.getInt("id"))
                    )
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return batchs;
    }

    public List<Batch> getAllBatchbyProduct (Component product) throws IOException {
        List<Batch> batchs = null;
        Conn conn = new Conn();
        try (Connection connection = conn.getConnection()) {
            var sql = "SELECT * FROM batch ";
            if(product.getId() > 0) {
                sql += " WHERE product_id = ?";
            }
            else if (product.getName() != null) {
                sql += " WHERE product_name = ?";
            }
            PreparedStatement ps = connection.prepareStatement(sql);
            if(product.getId() > 0) {
                ps.setInt(1, product.getId());
            }
            else if (product.getName() != null) {
                ps.setString(1, product.getName());
            }
            ResultSet rs = ps.executeQuery();
            SelectComponent selectComponent = new SelectComponent();
            while (rs.next()) {
                batchs.add(new Batch(
                                rs.getInt("id"),
                                selectComponent.getRecipe(new Recipe(rs.getInt("id"))),
                                rs.getInt("amount"),
                                rs.getString("description"),
                                rs.getInt("priority"),
                                getBatchLog(rs.getInt("id"))
                        )
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return batchs;
    }
}
