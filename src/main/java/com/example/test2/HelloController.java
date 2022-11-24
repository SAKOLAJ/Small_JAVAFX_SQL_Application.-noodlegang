package com.example.test2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.*;
import java.io.*;
import java.util.HashSet;

public class HelloController {
    @FXML
    private TextField searchEntry, whichthing;
    @FXML
    private ListView listview;
    private int[] row;


    @FXML
    public void onSaveToFile() {
        Object[] allItems = listview.getItems().toArray();
        String list = setVoid();
        for (int i=0; i<allItems.length; i++) {
            list = list + allItems[i].toString() + "; ";
        }
        try {
            FileWriter writer = new FileWriter("Data.txt");
            writer.write(list);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String setVoid() {
        return "";
    }

    public void Stop() {
        System.exit(0);
    }

    public void LoadInfo() {
        HashSet<String> items = new HashSet<>();
        String book = setVoid();
        try {
            FileReader reader = new FileReader("Data.txt");
            int count = reader.read();
            do {
                if (count == ';') {
                    items.add(book);
                    book = setVoid();
                } else {
                    book = book + (char)count;
                }
                count = reader.read();
            } while (!(count==-1));
        } catch (IOException e) {
            System.out.println("Things went wrong");
        }
        for (String LoadedTask:items) {
            listview.getItems().add(LoadedTask);
        }
    }
        //   Additional thanks to the @noodlegang fo the SQL query functionality creation. Check her GitHub as well
        //   https://github.com/noodlegang

    public void sendSQLquery(ActionEvent actionEvent) {
        //more sqlQueries will be added
        String sqlQuery = "SELECT * FROM " + searchEntry.getText();
        //!!! You need to change/ add your PC address to make application work properly
        String url = "jdbc:sqlserver://*YOUR PC/ SERVER address*:1433;DatabaseName=AdventureWorks2019;encrypt=true;trustServerCertificate=true;integratedSecurity=true;";

        try {
            try (Connection connection = DriverManager.getConnection(url);
                 PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                System.out.println("Connection good");

                ResultSet resultSet = preparedStatement.executeQuery();

                resultToListview(resultSet);
            } catch (SQLException ex) {
                System.out.println("Connection failed");
                ex.printStackTrace();
            }

        } catch (Exception e) {
            System.out.println("Error occurred");
            e.printStackTrace();
        }
    }

    private void resultToListview(ResultSet resultSet) {
        try {
            while(resultSet.next()) {
                listview.getItems().add(resultSet.getString(whichthing.getText()));
            }
        } catch (SQLException e) {
            System.out.println("You are a failure");
            e.printStackTrace();
        }
    }
}