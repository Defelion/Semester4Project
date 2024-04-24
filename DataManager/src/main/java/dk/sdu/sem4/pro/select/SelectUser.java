package dk.sdu.sem4.pro.select;

import dk.sdu.sem4.pro.connection.Conn;
import dk.sdu.sem4.pro.data.User;
import dk.sdu.sem4.pro.data.UserGroup;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SelectUser {
    public User getUser (User user) throws IOException {
        User selectUser = null;
        Conn conn = new Conn();

        try (Connection con = conn.getConnection()) {
            var SQL = "SELECT users.id, users.name, users.password, users.usergroup_id,  usergroup.name " +
                    "FROM users " +
                    "JOIN userGroup ON users.usergroup_id = userGroup.id ";
            if(user.getId() > 0)
                SQL += "WHERE users.id = ?";
            else if(user.getName() != null)
                SQL += "WHERE users.name = ?";
            var selectSQL = con.prepareStatement(SQL);
            if(user.getId() > 0)
                selectSQL.setInt(1, user.getId());
            else if(user.getName() != null)
                selectSQL.setString(1, user.getName());
            ResultSet rs = selectSQL.executeQuery();
            while (rs.next()) {
                selectUser = new User(
                        rs.getInt("users.id"),
                        rs.getString("users.name"),
                        rs.getString("users.password"),
                        new UserGroup(
                                rs.getInt("usergroup.id"),
                                rs.getString("usergroup.name")
                        )
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return selectUser;
    }

    public List<User> getAllUsers () throws IOException {
        List<User> selectUsers = new ArrayList<>();
        Conn conn = new Conn();

        try (Connection con = conn.getConnection()) {
            var SQL = "SELECT users.id, users.name, users.password, users.usergroup_id,  usergroup.name " +
                    "FROM users " +
                    "JOIN userGroup ON users.usergroup_id = userGroup.id ";
            var selectSQL = con.prepareStatement(SQL);
            ResultSet rs = selectSQL.executeQuery();
            while (rs.next()) {
                User user = new User(
                        rs.getInt("users.id"),
                        rs.getString("users.name"),
                        rs.getString("users.password"),
                        new UserGroup(
                                rs.getInt("users.usergroup_id"),
                                rs.getString("usergroup.name")
                        )
                );
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return selectUsers;
    }

    public UserGroup getUserGroup (UserGroup userGroup) throws IOException {
        UserGroup selectUserGroup = null;
        Conn conn = new Conn();

        try (Connection connection = conn.getConnection()) {
            var SQL = "SELECT * FROM usergroup ";
            if(userGroup.getId() > 0)
                SQL += "WHERE id = ?";
            else if(userGroup.getName() != null)
                SQL += "WHERE name = ?";
            var selectSQL = connection.prepareStatement(SQL);
            if(userGroup.getId() > 0)
                selectSQL.setInt(1, userGroup.getId());
            else if(userGroup.getName() != null)
                selectSQL.setString(1, userGroup.getName());
            ResultSet rs = selectSQL.executeQuery();
            while (rs.next()) {
                selectUserGroup = new UserGroup(
                        rs.getInt("id"),
                        rs.getString("name")
                );
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return selectUserGroup;
    }

    public List<UserGroup> getUserGroups () throws IOException {
        List<UserGroup> selectUserGroups = new ArrayList<>();
        Conn conn = new Conn();

        try (Connection connection = conn.getConnection()) {
            var SQL = "SELECT * FROM usergroup";
            var selectSQL = connection.prepareStatement(SQL);
            ResultSet rs = selectSQL.executeQuery();
            while (rs.next()) {
                selectUserGroups.add(new UserGroup(
                        rs.getInt("id"),
                        rs.getString("name")
                ));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return selectUserGroups;
    }
}
