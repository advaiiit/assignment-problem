package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {

        String dbDriver = "org.postgresql.Driver";
        String dbName = "jdbc:postgresql://localhost:5432/TestDB";
        String username = "postgres";
        String password = "changeme";

        ResultSet rs = null;

        try {
            Class.forName(dbDriver);
            Connection conn = DriverManager.getConnection(dbName, username, password);

            System.out.println("Connection Established");

            Statement statement = conn.createStatement();

            String query = "Select resource_id, job_id, cost from public.bb";

            rs = statement.executeQuery(query);

            while (rs.next()) {
                System.out.print(rs.getString("resource_id"));
                System.out.print(rs.getString("job_id"));
                System.out.print(rs.getInt("cost"));
                System.out.println();
                rs.next();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}