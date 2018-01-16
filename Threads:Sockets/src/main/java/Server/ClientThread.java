package Server;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


// @author Lasse
 
public class ClientThread extends Thread{

    Scanner reader;
    PrintWriter writer;
    Socket socket;
    TcpServer master;
    String role = "";
    

    public ClientThread(Socket socket, TcpServer masterServer) throws IOException
    {
        this.socket = socket;
        this.master = masterServer;
        reader = new Scanner(socket.getInputStream());
        writer = new PrintWriter(socket.getOutputStream(), true);
         
    }
    
    public void sendMessage(String msg)
    {
        writer.println(msg);
    }

    @Override
    public void run(){
       System.out.println("we here man");
       this.master.addClientHandler(this);
       sendMessage("---- Commands: ---- \n turnstile (Sets role to turnstile) \n monitor (Sets role to monitor \n count (promts the server to count up - requires turnstile role) \n showcount (shows the current count - requires monitor or turnstile role) \n spectators (show amount of connected spectators - requires monitor role)");
        
       String message = reader.nextLine();
       
       while(!message.toUpperCase().equals("QUIT")){
           switch (message.toUpperCase()){
               case "TURNSTILE":
                   if (this.role == ""){
                       this.setRole("Turnstile");
                       sendMessage("Noted as a turnstile");
                   }else{
                       sendMessage("Role already set, you have role: "+this.getRole());
                   }
                   break;
               case "MONITOR":
                   if (this.role.equals("")){
                       this.setRole("Monitor");
                       sendMessage("Noted as a Monitor");
                   }else{
                       sendMessage("Role already set, you have role: "+this.getRole());
                   }
                   break;     
               case "COUNT":
                   if(this.getRole().equals("Turnstile")){
                       this.master.incr();
                       sendMessage("Counted +1 " + "(total count is: " + String.valueOf(this.master.getCount()+")"));
                   }else{
                       sendMessage("You dont have rights to acces the counter, you need to have turnstile role");
                   }
                   break;
               case "SHOWCOUNT":
                   if(this.getRole().equals("Turnstile") || this.getRole().equals("Monitor")){
                       sendMessage("Count is " +String.valueOf(this.master.getCount()));
                   }else{
                       sendMessage("You need either Turnstile og Monitor role to view the count");
                   }
                   break;
               case "SPECTATORS":
                   if(this.getRole().equals("Monitor")){
                       sendMessage("Amount of connected spectators: "+ this.master.spectators());
                   }else{
                        sendMessage("You dont have rights to acces the spectator list, you need to have monitor role");
                   }
                   
                   break;
               default:
                   sendMessage("Could not recognize command: "+message);
                   break;
           }
           message = reader.nextLine();
       }
       
         try
        {
            this.master.removeClientHandler(this);
            socket.close();
        } catch (IOException ex)
        {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       
    }

    public String getRole()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }
}
