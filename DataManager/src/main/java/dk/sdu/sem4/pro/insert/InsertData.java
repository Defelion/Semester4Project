package dk.sdu.sem4.pro.insert;

import dk.sdu.sem4.pro.connection.Conn;
import dk.sdu.sem4.pro.data.*;
import dk.sdu.sem4.pro.services.IInsert;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertData implements IInsert {

    private Conn conn;
    /**
     * @param batch
     * @return ID
     */
    @Override
    public int addBatch(Batch batch) throws IOException {
        conn = new Conn();
        int id = 0;
        try(var connection = conn.getConnection()) {
            String sql = "insert into batch (priority, description, amount) values (batch.priority, batch.description, batch.amount)";
            var insert = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException e) {
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
        return 0;
    }

    /**
     * @param component
     * @return ID
     */
    @Override
    public int addComponent(Component component) {
        return 0;
    }

    /**
     * @param recipe
     * @return ID
     */
    @Override
    public int addProduct(Recipe recipe) {
        return 0;
    }

    /**
     * @param unit
     * @return ID
     */
    @Override
    public int addUnit(Unit unit) {
        return 0;
    }

    /**
     * @param agv
     * @return ID
     */
    @Override
    public int addAGV(AGV agv) {
        return 0;
    }

    /**
     * @param user
     * @return ID
     */
    @Override
    public int addUser(User user) {
        return 0;
    }
}
