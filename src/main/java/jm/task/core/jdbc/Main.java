package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        UserService service = new UserServiceImpl();

        service.createUsersTable();
        service.saveUser("User1", "LastName1", (byte) 20);
        service.saveUser("User2", "LastName2", (byte) 19);
        service.saveUser("User3", "LastName3", (byte) 21);
        service.saveUser("User4", "LastName4", (byte) 18);
        service.getAllUsers().forEach(System.out::println);

        service.cleanUsersTable();
        service.getAllUsers().forEach(System.out::println);

        service.dropUsersTable();
    }
}
