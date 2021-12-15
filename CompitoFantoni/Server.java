import java.io.*;
import java.net.*;
import java.util.*;

public class Server{
    ServerSocket server =null;
    Socket client =null;
    String stringaRicevuta= null;
    String stringaModificata=null;
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;


    public Socket attendi(){
        try{
            System.out.println("server partito in esecuzione");
            server =new ServerSocket(6789);
            client = server.accept();
            server.close();
            inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            outVersoClient= new DataOutputStream(client.getOutputStream());
            outVersoClient.writeBytes("connessione riuscita"+'\n'); 

        }
        catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("errore durante l'istanza del server");
        }
        return client;
    }

    public void comunica(){
        try{
            int c=5;
            for(;;){
            outVersoClient.writeBytes("inserisci D per vedere la disponibilità o A per acquistare un biglietto"+'\n');
            
            stringaRicevuta= inDalClient.readLine();
            if(stringaRicevuta.equals("D")){
                outVersoClient.writeBytes("i biglietti disponibili sono: "+ c + '\n');
            }
            if(stringaRicevuta.equals("A") && c>0)
                {
                    outVersoClient.writeBytes("i biglietto acquistato" + '\n');
                    c--;
                }
            if(c==0){
            
            outVersoClient.writeBytes("i biglietti sono esauriti " + '\n');   
            server.close();
        }
    }
 } catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("errore durante la comunicazione col client");
            System.exit(1);
        }
    }
    public static void main( String[] args )
    {
        Server servente = new Server();
        servente.attendi();
        servente.comunica();
    }

}