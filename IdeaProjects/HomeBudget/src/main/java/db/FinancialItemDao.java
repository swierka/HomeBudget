package db;

import controller.AppController;
import exceptions.ElementNotFoundException;
import model.Expense;
import model.FinancialItem;
import model.Income;

import java.sql.*;

public class FinancialItemDao {
    private static final String URL = "jdbc:mysql://localhost:3306/household?autoReconnect=true&useSSL=false";
    private static final String USER = "root";
    private static final String PASS = "admin";
    private Connection connection;

    public FinancialItemDao() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            System.out.println("No driver found");
        } catch (SQLException e) {
            System.out.println("Could not establish the connection");
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveNewFinancialItem(FinancialItem financialItem) {
        final String sql = "insert into home_budget (type, description,amount,transactionDate) values (?,?,?,?);";

        try {
            PreparedStatement prepStm = connection.prepareStatement(sql);
            prepStm.setString(1, financialItem.getType());
            prepStm.setString(2, financialItem.getDescription());
            prepStm.setDouble(3, financialItem.getAmount());
            prepStm.setDate(4, Date.valueOf(financialItem.getDate()));
            prepStm.execute();
        } catch (SQLException e) {
            System.out.println("Could not save the record");
            e.printStackTrace();
        }
    }

/*    public FinancialItem getFinancialItem(Long id, String type) throws ElementNotFoundException {
        final String sql = "SELECT * FROM (SELECT *, @rownum:=@rownum + 1 AS rank FROM home_budget, (SELECT @rownum:=0) r where type=? ORDER BY id) as d WHERE d.rank = ?;";

        try {
            PreparedStatement prepStm = connection.prepareStatement(sql);
            prepStm.setString(1, type);
            prepStm.setLong(2, id);
            ResultSet resultSet = prepStm.executeQuery();
            if (resultSet.next()) {
                FinancialItem financialItem = new FinancialItem();
                financialItem.setId(id);
                financialItem.setType(type);
                financialItem.setDescription(resultSet.getString("description"));
                financialItem.setAmount(resultSet.getDouble("amount"));
                Date sqlDate = resultSet.getDate("transactionDate");
                financialItem.setDate(sqlDate.toLocalDate());
                return financialItem;
            }
        } catch (SQLException e) {
            System.out.println("Could not find a financial item");
            e.printStackTrace();
        }
        return null;
    }*/

    public FinancialItem getFinancialItem (Long rank, String type) throws ElementNotFoundException {
        final String sql = "SELECT * FROM (SELECT *, @rownum:=@rownum + 1 AS rank FROM home_budget, (SELECT @rownum:=0) r where type=? ORDER BY id) as d WHERE d.rank = ?;";

        try {
            PreparedStatement prepStm = connection.prepareStatement(sql);
            prepStm.setString(1, type);
            prepStm.setLong(2, rank);
            ResultSet resultSet = prepStm.executeQuery();
            if (resultSet.next()) {
                if(type== AppController.EXPENSE){
                    Expense expense = new Expense();
                    expense.setType(type);
                    expense.setId(resultSet.getLong("id"));
                    expense.setDescription(resultSet.getString("description"));
                    expense.setAmount(resultSet.getDouble("amount"));
                    Date sqlDate = resultSet.getDate("transactionDate");
                    expense.setDate(sqlDate.toLocalDate());
                    return expense;
                } else {
                    Income income = new Income();
                    income.setType(type);
                    income.setId(resultSet.getLong("id"));
                    income.setDescription(resultSet.getString("description"));
                    income.setAmount(resultSet.getDouble("amount"));
                    Date sqlDate = resultSet.getDate("transactionDate");
                    income.setDate(sqlDate.toLocalDate());
                    return income;
                }
            }
        } catch (SQLException e) {
            System.out.println("Could not find a financial item");
            e.printStackTrace();
        }
        return null;
    }

    public int countItems(String type){
        int count=0;
        String sql;
        if(type.equals(null)){
            sql = "select count(type) as howMany from home_budget;";}
        else {
            sql = "select count(type) as howMany from home_budget where type = ?;";
        }

        try {
            PreparedStatement prepStm = connection.prepareStatement(sql);
            prepStm.setString(1, type);
            ResultSet resultSet = prepStm.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt("howMany");
            }
        } catch (SQLException e) {
            System.out.println("Could not count items");
            e.printStackTrace();
        }
        return count;
    }

    public void updateFinancialItem(FinancialItem financialItem) {
        final String sql = "update home_budget set type=?, description=?, amount=?, transactionDate=? where id = ?;";

        try {
            PreparedStatement prepStm = connection.prepareStatement(sql);
            prepStm.setString(1, financialItem.getType());
            prepStm.setString(2, financialItem.getDescription());
            prepStm.setDouble(3, financialItem.getAmount());
            prepStm.setDate(4, Date.valueOf(financialItem.getDate()));
        } catch (SQLException e) {
            System.out.println("Could not update the record");
            e.printStackTrace();
        }
    }

    public void deleteItem (Long id){
        final String sql = "delete from home_budget where id=?";

        try {
            PreparedStatement prepStm = connection.prepareStatement(sql);
            prepStm.setLong(1, id);
            prepStm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Could not update the record");
            e.printStackTrace();
        }
        System.out.println("Pozycja o id: "+id+" została usunieta z bazy.");
    }
}

