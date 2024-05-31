package dk.sdu.sem4.pro.datamanager.select;

import dk.sdu.sem4.pro.commondata.data.Batch;
import dk.sdu.sem4.pro.commondata.data.Component;
import dk.sdu.sem4.pro.commondata.data.Logline;
import dk.sdu.sem4.pro.commondata.data.Recipe;
import dk.sdu.sem4.pro.datamanager.connection.Conn;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SelectBatch {
    public Logline getLogLine (int logLineID) throws IOException {
        Logline logLine = new Logline();
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
            ps.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return logLine;
    }

    public List<Logline> getLogLines () throws IOException {
        List<Logline> logLines = new ArrayList<>();
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
            ps.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return logLines;
    }

    public List<Logline> getBatchLog (int batchID) throws IOException {
        List<Logline> logLines = new ArrayList<>();
        //System.out.println("getBatchLog - batchID: " + batchID);
        Conn conn = new Conn();
        try (Connection connection = conn.getConnection()) {
            var sql = "SELECT * FROM logline where batch_id = ? order by datetime desc";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, batchID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Logline logline = new Logline(
                                rs.getInt("id"),
                                rs.getString("description"),
                                rs.getDate("datetime"),
                                rs.getString("type"),
                                rs.getInt("batch_id")
                        );
                //System.out.println("getBatchLog - loglines.type: " + logline.getType());
                logLines.add(logline);
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //System.out.println(logLines);
        return logLines;
    }

    public Batch getBatch (int batchID) throws IOException {
        Batch batch = new Batch();
        System.out.println("getBath - batchID = " + batchID);
        Conn conn = new Conn();
        try (Connection connection = conn.getConnection()) {
            var sql = "SELECT * FROM batch where id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, batchID);
            ResultSet rs = ps.executeQuery();
            int productID = 0;
            while (rs.next()) {
                batch = new Batch();
                batch.setId(rs.getInt("id"));
                batch.setPriority(rs.getInt("priority"));
                if(rs.getString("description") != "") batch.setDescription(rs.getString("description"));
                batch.setAmount(rs.getInt("amount"));
                productID = rs.getInt("component_id");
            }
            ps.close();
            rs.close();
            SelectData selectData = new SelectData();
            System.out.println("Batch ID: " + batch.getId());
            System.out.println("Bath Priority: " + batch.getPriority());
            System.out.println("getBatch - Recipe: " + productID);
            batch.setProduct(selectData.getProduct(productID));
            batch.setLog(getBatchLog(batch.getId()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return batch;
    }

    public Batch getBatchWithHigestPriority () throws IOException {
        Batch batch = new Batch();
        Conn conn = new Conn();
        try (Connection connection = conn.getConnection()) {
            var sql = "SELECT * FROM batch order by priority desc limit 1";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            int productID = 0;
            while (rs.next()) {
                batch = new Batch();
                batch.setId(rs.getInt("id"));
                batch.setPriority(rs.getInt("priority"));
                if(rs.getString("description") != "") batch.setDescription(rs.getString("description"));
                batch.setAmount(rs.getInt("amount"));
                productID = rs.getInt("component_id");
            }
            ps.close();
            rs.close();
            SelectData selectData = new SelectData();
            System.out.println("getBatchWithHigestPriority - Recipe: " + productID);
            batch.setProduct(selectData.getProduct(productID));
            batch.setLog(getBatchLog(batch.getId()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return batch;
    }

    public List<Batch> getAllBatch () throws IOException {
        List<Batch> batchs = new ArrayList<>();
        Conn conn = new Conn();
        try (Connection connection = conn.getConnection()) {
            var sql = "SELECT * FROM batch";
            //System.out.println("SQL Query: " + sql);
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                batchs.add(new Batch(
                        rs.getInt("id"),
                        new Recipe(rs.getInt("component_id")),
                        rs.getInt("amount"),
                        rs.getString("description"),
                        rs.getInt("priority")
                        )
                );
            }
            ps.close();
            rs.close();
            SelectComponent component = new SelectComponent();
            for (Batch batch : batchs) {
                batch.setProduct(component.getRecipe(batch.getProduct()));
                batch.setLog(getBatchLog(batch.getId()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return batchs;
    }

    public List<Batch> getAllBatchbyProduct (Component product) throws IOException {
        List<Batch> batchs = new ArrayList<>();
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
            while (rs.next()) {
                batchs.add(new Batch(
                                rs.getInt("id"),
                                new Recipe(rs.getInt("component_id")),
                                rs.getInt("amount"),
                                rs.getString("description"),
                                rs.getInt("priority")
                        )
                );
            }
            ps.close();
            rs.close();
            SelectComponent component = new SelectComponent();
            for (Batch batch : batchs) {
                batch.setProduct(component.getRecipe(batch.getProduct()));
                batch.setLog(getBatchLog(batch.getId()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return batchs;
    }
}
