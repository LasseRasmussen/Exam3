package Server;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


// @author Lasse
 
public class TcpServer {
    
    public static int PORT = 8081;
    public static String IP = "127.0.0.1";
    long count = 0;
    int specCount = 0;

    private final List<ClientThread> clientThreads = Collections.synchronizedList(new ArrayList());

    
    
    public static void main(String[] args) throws IOException
    {
        System.out.println(String.format("Server Startet, bound to: %s. Listening on: %d", IP, PORT));
        new TcpServer().listenForClients();
    }
    
     public void addClientHandler(ClientThread h)
    {
        clientThreads.add(h);
    }

    public void removeClientHandler(ClientThread h)
    {
        clientThreads.remove(h);
    }
    
    
        public void listenForClients() throws IOException
    {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(IP, PORT));
        while (true)
        {
            Socket socket = serverSocket.accept(); //Important Blocking call
            new ClientThread(socket, this).start();
        }
        
        
    }
        
      public synchronized void incr() {
    count++;
  }

public int spectators(){
    specCount = 0;
    for (ClientThread clientThread : clientThreads)
    {
        specCount++;
    }
        return specCount;   
}      

  public long getCount() {
    return count;
  }
   
}
