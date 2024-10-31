package Service;
import DAO.AccountDAO;
import Model.Account;
import java.util.ArrayList;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        this.accountDAO = new AccountDAO();
    }

    public Account getAccountById(int id){
        return accountDAO.getAccountById(id);
    }

    public Account getAccount(String username, String password){
        return accountDAO.getAccount(username, password);
    }

    public Account addAccount(Account account){
        if(account.getUsername().isBlank() || account.getPassword().length() < 4){
            return null;
        }
        else{
            return accountDAO.addAccount(account);
        }
    }

    public ArrayList<Account> getAllAccounts(){
        return accountDAO.getAllAccounts();
    }
    
}
