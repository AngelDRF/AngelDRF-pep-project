package Controller;
import Service.AccountService;
import Service.MessageService;
import Model.Message;
import Model.Account;
import java.util.ArrayList;
import java.util.Map;

import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    
    private MessageService messageService;
    private AccountService accountService;

    public SocialMediaController(){
        this.messageService = new MessageService();
        this.accountService = new AccountService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::addAccountHandler);
        app.post("/login", this::loginAccountHandler);
        app.get("/accounts/{account_id}", this::getAccountByIdHandler);
        app.get("/accounts", this::getAllAccountHandler);
        app.post("/messages", this::addMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByUserHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.delete("/messages/{message_id}",this::deleteMessageHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void addMessageHandler(Context ctx){
        Message message = ctx.bodyAsClass(Message.class);
        Message mes = messageService.addMessage(message);
        if(mes != null){
            ctx.json(mes);
        }
        else{
            ctx.status(400);
        }
    }

    private void getAllMessagesByUserHandler(Context ctx){
        String ids = ctx.pathParam("account_id");
        int id = Integer.parseInt(ids);
        ArrayList<Message> mesUser = messageService.getAllMessagesByUser(id);
        if(mesUser != null){
            ctx.json(mesUser);
        }
        else{
            ctx.status(200);
        }
    }

    private void deleteMessageHandler(Context ctx){
        String ids = ctx.pathParam("message_id");
        int id = Integer.parseInt(ids);
        Message delMes = messageService.getMessageById(id);
        if(delMes != null){
            messageService.deleteMessage(id);
            ctx.json(200);
            ctx.json(delMes);
            
        }
        else{
            ctx.json(200);
            ctx.json("");
        }
    }

    private void getAllMessagesHandler(Context ctx){
        ArrayList<Message> mes = messageService.getAllMessages();
        if(mes != null){
            ctx.json(mes);
        }
        else{
            ctx.status(200);
        }
    }

    private void updateMessageHandler(Context ctx){
        Map<String, String> mapMes = ctx.bodyAsClass(Map.class);
        String messageText = mapMes.get("message_text");
        String ids = ctx.pathParam("message_id");
        int id = Integer.parseInt(ids);
        Message message = messageService.updateMessage(id, messageText);
        if(message != null){
            ctx.json(message);
        }
        else{
            ctx.status(400);
        }

    }

    private void getMessageByIdHandler(Context ctx){
        String ids = ctx.pathParam("message_id");
        int id = Integer.parseInt(ids);
        Message message = messageService.getMessageById(id);
        if(message != null){
            ctx.json(message);
        }
        else{
            ctx.status(200);
            ctx.json("");
        }
    }

    private void addAccountHandler(Context ctx){
        Account account = ctx.bodyAsClass(Account.class);
        Account acc = accountService.addAccount(account);
        if(acc != null){
            ctx.json(acc);
        }
        else{
            ctx.status(400);
        }
    }

    private void getAllAccountHandler(Context ctx){
        ArrayList<Account> acc = accountService.getAllAccounts();
        if(acc != null){
            ctx.json(acc);
        }
        else{
            ctx.status(400);
        }
    }

    private void loginAccountHandler(Context ctx){
        Account account = ctx.bodyAsClass(Account.class);
        Account acc = accountService.getAccount(account.getUsername(), account.getPassword());
        if(acc != null){
            ctx.json(acc);
        }
        else{
            ctx.status(401);
        }
    }

    private void getAccountByIdHandler(Context ctx){
        String ids = ctx.pathParam("account_id");
        int id = Integer.parseInt(ids);
        Account account = accountService.getAccountById(id);
        if(account != null){
            ctx.json(account);
        }
        else{
            ctx.status(400);
        }
    }

}