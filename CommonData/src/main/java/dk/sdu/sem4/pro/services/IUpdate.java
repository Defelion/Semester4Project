package dk.sdu.sem4.pro.services;

import dk.sdu.sem4.pro.data.*;

import java.io.IOException;

public interface IUpdate {
    public boolean updateBatch (Batch batch) throws IOException;
    public boolean updateLogline (Logline logline) throws IOException;
    public boolean updateComponent (Component component) throws IOException;
    public boolean updateProduct (Recipe recipe) throws IOException;
    public boolean updateUnit (Unit unit) throws IOException;
    public boolean updateAGV (AGV agv) throws IOException;
    public boolean updateUser (User user) throws IOException;
    public boolean updateUserGroup (UserGroup userGroup) throws IOException;
}
