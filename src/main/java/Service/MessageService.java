package Service;
import DAO.AccountDAO;
import DAO.MessageDao;
import Model.Account;
import Model.Message;
import java.util.ArrayList;
import java.util.Objects;


public class MessageService {
    private AccountDAO accountDAO;
    private MessageDao messageDAO;

    public MessageService(){
        this.accountDAO = new AccountDAO();
        this.messageDAO = new MessageDao();
    }

    public ArrayList<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int id){
        return messageDAO.getMessageById(id);
    }

    public ArrayList<Message> getAllMessagesByUser(int id){
        ArrayList<Message> mesUser = new ArrayList<>();
        ArrayList<Message> allMes = messageDAO.getAllMessages();

        for(Message mes : allMes){
            if(mes.getPosted_by() == id){
                mesUser.add(mes);
            }
        }
        return mesUser;
    }

    public Message addMessage(Message message){
        int accId = message.getPosted_by();
        Account acc = accountDAO.getAccountById(accId);
        if(message.getMessage_text().isBlank() || Objects.isNull(acc) || message.getMessage_text().length() >= 255){
            return null;
        }
        else{
            Message mess = messageDAO.addMessage(message);
            return mess;
        }
    }

    public Message deleteMessage(int id){
        Message mes = messageDAO.getMessageById(id);
        if(Objects.isNull(mes)){
            return null;
        }
        else{
            return messageDAO.deleteMessage(id);
        }
    }

    public Message updateMessage(int id, String messageText){
        Message mes = messageDAO.getMessageById(id);
        if(messageText.isBlank() || messageText.length() >= 255 || Objects.isNull(mes)){
            return null;
        }
        else{
            messageDAO.updateMessage(id, messageText);
        }
        return messageDAO.getMessageById(id);
    }
    
}
