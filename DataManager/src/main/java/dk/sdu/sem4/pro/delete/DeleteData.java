package dk.sdu.sem4.pro.delete;

import dk.sdu.sem4.pro.data.*;
import dk.sdu.sem4.pro.services.IDelete;

public class DeleteData implements IDelete {
    /**
     * @param batch
     * @return success
     */
    @Override
    public boolean deleteBatch(Batch batch) {
        return false;
    }

    /**
     * @param batchID
     * @param logline
     * @return success
     */
    @Override
    public boolean deleteLogline(int batchID, Logline logline) {
        return false;
    }

    /**
     * @param component
     * @return success
     */
    @Override
    public boolean deleteComponent(Component component) {
        return false;
    }

    /**
     * @param recipe
     * @return success
     */
    @Override
    public boolean deleteProduct(Recipe recipe) {
        return false;
    }

    /**
     * @param unit
     * @return success
     */
    @Override
    public boolean deleteUnit(Unit unit) {
        return false;
    }

    /**
     * @param agv
     * @return success
     */
    @Override
    public boolean deleteAGV(AGV agv) {
        return false;
    }

    /**
     * @param user
     * @return success
     */
    @Override
    public boolean deleteUser(User user) {
        return false;
    }
}
