package DAO;
import java.sql.*;
import java.util.ArrayList;
import Util.ConnectionUtil;
import Model.Message;

public class MessageDao {
    public Message getMessageById(int id){
        Connection c = ConnectionUtil.getConnection();
        try{
            String sql = "Select * From message Where message_id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), 
                rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                return message;
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message addMessage(Message message){
        Connection c = ConnectionUtil.getConnection();
        try{
            String sql = "Insert Into message Values(default, ?, ?, ?)";
            PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                int mesId = (int) rs.getLong(1);
                Message mes = new Message(mesId, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
                return mes;
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message deleteMessage(int id){
        Connection c = ConnectionUtil.getConnection();
        try{
            String sql = "Delete From message Where message_id = ?";
            PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, id);

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                return new Message(rs.getInt("message_id"), rs.getInt("posted_by"), 
                rs.getString("message_text"), rs.getLong("time_posted_epoch"));
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message updateMessage(int id, String messageText){
        Connection c = ConnectionUtil.getConnection();
        try{
            String sql = "Update message Set message_text = ? Where message_id = ?";
            PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, messageText);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<Message> getAllMessages(){
        Connection c = ConnectionUtil.getConnection();
        ArrayList<Message> mes = new ArrayList<>();
        try{
            String sql = "Select * From message";
            PreparedStatement ps = c.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), 
                rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                mes.add(message);
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return mes;
    }
    
}
