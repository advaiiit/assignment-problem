package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        // Database Connection

        String dbDriver = "org.postgresql.Driver";
        String dbName = "jdbc:postgresql://localhost:5432/TestDB";
        String username = "postgres";
        String password = "changeme";

        ResultSet rs = null;

        int[][] dataMatrix = new int[10][10];

        // Building HashMaps to generate output from optimal assignments
        HashMap<Integer, String> indexToResource = new HashMap<>();
        HashMap<Integer, String> indexToJob = new HashMap<>();

        try {
            Class.forName(dbDriver);
            Connection conn = DriverManager.getConnection(dbName, username, password);

            System.out.println("Connection Established");

            Statement statement = conn.createStatement();

            String query = "Select resource_id, job_id, cost from public.bb limit 100";

            rs = statement.executeQuery(query);

            // Converting table to 2D array
            int rows = 0;
            while (rs.next()) {
                int[] row = new int[10];
                indexToResource.put(rows, rs.getString("resource_id"));
                for (int i = 0; i < 10; i++) {
                    indexToJob.put(i, rs.getString("job_id"));
                    row[i] = rs.getInt("cost");
                    rs.next();
                }
                dataMatrix[rows] = row;
                rows++;
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        // Printing matrix
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(dataMatrix[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println();

        // Printing HashMaps
        for (Map.Entry m : indexToResource.entrySet()) {
            System.out.println(m.getKey() + " -> " + m.getValue());
        }

        System.out.println();

        for (Map.Entry m : indexToJob.entrySet()) {
            System.out.println(m.getKey() + " -> " + m.getValue());
        }

        System.out.println();

        HungarianAlgorithm hungarianAlgorithm = new HungarianAlgorithm(dataMatrix);
        int[][] optimalAssignment = hungarianAlgorithm.findOptimalAssignment();

        HashMap<String, String> assignmentOutput = new HashMap<>();

        // Printing output from Hungarian Algorithm
        if (optimalAssignment.length > 0) {
            for (int i = 0; i < 10; i++) {
                assignmentOutput.put(indexToResource.get(optimalAssignment[i][0]), indexToJob.get(optimalAssignment[i][1]));
                System.out.print("Row: " + optimalAssignment[i][0] + "-> Col: " + optimalAssignment[i][1] +
                        " (" + dataMatrix[optimalAssignment[i][0]][optimalAssignment[i][1]] + ")");
                System.out.println();
            }
        }

        System.out.println();

        // Printing final output
        for (Map.Entry m : assignmentOutput.entrySet()) {
            System.out.println(m.getKey() + " -> " + m.getValue());
        }
     }
}