package com.nickolas.medicalassistanceapp.dao;

import com.nickolas.medicalassistanceapp.db.Database;
import com.nickolas.medicalassistanceapp.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
    public boolean exists(String username) {
        try (Connection conn = Database.getConnection()) {

            PreparedStatement ps = conn.prepareStatement(
                    "SELECT id FROM users WHERE username=?"
            );

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public void register(User user) {
        try (Connection conn = Database.getConnection()) {

            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO users(username, password, role) VALUES (?, ?, ?)"
            );

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User login(String username, String password) {
        try (Connection conn = Database.getConnection()) {

            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM users WHERE username=? AND password=?"
            );

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
