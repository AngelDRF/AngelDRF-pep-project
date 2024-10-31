package DAO;

import java.sql.*;
import java.util.ArrayList;
import Util.ConnectionUtil;
import Model.Account;

public class AccountDAO {
    public Account getAccountById(int id){
        Connection c = ConnectionUtil.getConnection();
        try{
            String sql = "Select * From account Where account_id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int resId = rs.getInt("account_id");
                String resUserna = rs.getString("username");
                String resPass = rs.getString("password");
                Account account = new Account(resId, resUserna, resPass);
                return account;
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account addAccount(Account account){
        Connection c = ConnectionUtil.getConnection();
        try{
            String sql = "Insert Into account Values(default, ?, ?)";
            PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            if(rs.next()){
                int accId = (int) rs.getLong(1);
                return new Account(accId, account.getUsername(), account.getPassword());
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<Account> getAllAccounts(){
        Connection c = ConnectionUtil.getConnection();
        ArrayList<Account> acc = new ArrayList<>();
        try{
            String sql = "Select * From account";
            PreparedStatement ps = c.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"), 
                rs.getString("password"));
                acc.add(account);
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return acc;
    }

    public Account getAccount(String username, String password){
        Connection c = ConnectionUtil.getConnection();
        try{
            String sql = "Select * From account Where username = ? And password = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("account_id");
                String resUserna = rs.getString("username");
                String resPass = rs.getString("password");
                return new Account(id, resUserna, resPass);
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    
}
