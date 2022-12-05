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

        int[][] dataMatrix = new int[10][10];

        try {
            Class.forName(dbDriver);
            Connection conn = DriverManager.getConnection(dbName, username, password);

            System.out.println("Connection Established");

            Statement statement = conn.createStatement();

            String query = "Select resource_id, job_id, cost from public.bb limit 100";

            rs = statement.executeQuery(query);

            int rows = 0;

            while (rs.next()) {
                int[] row = new int[10];
                for (int i = 0; i < 10; i++) {
                    row[i] = rs.getInt("cost");
                    rs.next();
                }
                dataMatrix[rows] = row;
                rows++;
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(dataMatrix[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("\n");

        HungarianAlgorithm hungarianAlgorithm = new HungarianAlgorithm(dataMatrix);
        int[][] optimalAssignment = hungarianAlgorithm.findOptimalAssignment();

        if (optimalAssignment.length > 0) {
            for (int i = 0; i < optimalAssignment.length; i++) {
                System.out.print("Row: " + optimalAssignment[i][1] + "-> Col: " + optimalAssignment[i][0] +
                        " (" + dataMatrix[optimalAssignment[i][0]][optimalAssignment[i][1]] + ")");
                System.out.println();
            }
        }
    }
}