package com.company.repository;

import com.company.Info;
import com.company.Manager;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class RepositoryDB {
    public static String url = "jdbc:postgresql://localhost:5432/test";
    public static String username = "postgres";
    public static String password = "root";

    public static void save(Info info){
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "INSERT INTO info (userid, name, quantity, unit, amount, date) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, info.getUserId().intValue());
            statement.setString(2, info.getName());
            statement.setDouble(3, info.getQuantity());
            if (info.getUnit() == null){
                statement.setNull(4, Types.VARCHAR);
            } else
                statement.setString(4, info.getUnit());
            statement.setDouble(5, info.getAmount());
            statement.setString(6, info.getStringDate());

            statement.executeUpdate();

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void saveAll(){
        for (Info info : Manager.infoList){
            save(info);
        }
    }

    public static List<Info> getInfoListById(Long userId) {
        List<Info> list = new LinkedList<>();
        int id = userId.intValue();

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
            String sql = "SELECT * FROM info WHERE userid = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            Info info;
            while (resultSet.next()){
                info = new Info();
                info.setUserId(userId);
                info.setName(resultSet.getString("name"));
                info.setQuantity(resultSet.getDouble("quantity"));
                info.setUnit(resultSet.getString("unit"));
                if (info.getUnit().equals("null"))
                    info.setUnit(null);
                info.setAmount(resultSet.getDouble(6));
                info.setStringDate(resultSet.getString(7));
                list.add(info);
            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /*CREATE TABLE info(
	id serial primary key,
	userid int,
	name varchar not null,
	quantity numeric not null,
	unit varchar,
	amount numeric,
	date varchar not null
    )*/

}
