package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS public.users (id BIGSERIAL PRIMARY KEY, name VARCHAR(50) NOT NULL, lastname VARCHAR(50) NOT NULL, age SMALLINT NOT NULL);";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS public.users";
    private static final String CLEAN_TABLE = "TRUNCATE TABLE public.users";
    private static final String SAVE_USER = "INSERT INTO public.users (name, lastname, age) VALUES (?,?,?)";
    private static final String REMOVE_USER = "DELETE FROM public.users WHERE id = ?";
    private static final String GET_ALL_USERS = "SELECT id, name, lastname, age FROM public.users";
    private Connection conn;

    public UserDaoJDBCImpl() {
        conn = Util.getConnection();
    }

    public void createUsersTable() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(CREATE_TABLE);
            System.out.println("Create table!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(DROP_TABLE);
            System.out.println("Drop table!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement stmt = conn.prepareStatement(SAVE_USER, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, name);
            stmt.setString(2, lastName);
            stmt.setByte(3, age);
            stmt.executeUpdate();
            System.out.println("Save user!");
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void removeUserById(long id) {
        try (PreparedStatement stmt = conn.prepareStatement(REMOVE_USER, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
            System.out.println("Remove user!");
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<User> getAllUsers() {
        try (PreparedStatement stmt = conn.prepareStatement(GET_ALL_USERS)) {
            ResultSet rs = stmt.executeQuery();
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastname"));
                user.setAge(rs.getByte("age"));
                users.add(user);
                System.out.println("Loaded user! " + user.getId());
            }
            return users;

        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanUsersTable() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(CLEAN_TABLE);
            System.out.println("Clean table!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
