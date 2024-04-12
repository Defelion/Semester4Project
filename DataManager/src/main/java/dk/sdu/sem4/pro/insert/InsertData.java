package dk.sdu.sem4.pro.insert;

import dk.sdu.sem4.pro.data.*;
import dk.sdu.sem4.pro.services.IInsert;

public class InsertData implements IInsert {
    /**
     * @param batch
     * @return ID
     */
    @Override
    public int addBatch(Batch batch) {
        return 0;
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
