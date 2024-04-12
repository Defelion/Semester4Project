package dk.sdu.sem4.pro.update;

import dk.sdu.sem4.pro.data.*;
import dk.sdu.sem4.pro.services.IUpdate;

public class UpdateData implements IUpdate {
    @Override
    public boolean updateBatch(Batch batch) {
        return false;
    }

    @Override
    public boolean updateLogline(int batchID, Logline logline) {
        return false;
    }

    @Override
    public boolean updateComponent(Component component) {
        return false;
    }

    @Override
    public boolean updateProduct(Recipe recipe) {
        return false;
    }

    @Override
    public boolean updateUnit(Unit unit) {
        return false;
    }

    @Override
    public boolean updateAGV(AGV agv) {
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        return false;
    }
}
