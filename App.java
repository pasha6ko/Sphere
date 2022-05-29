import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;


public class App {

    private static final Logger logger = LogManager.getLogger(App.class);
        public static void main(String[] arg) throws IOException,ParseException{

        logger.info("Server Start");
        String filename = arg[0];
        JsonController json = new JsonController(filename);

        int port = 8080;
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(port),0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        server.createContext("/market",new Market(json));
        server.createContext("/market/deal", new Deal(json));
        server.createContext("/account",new ConnectToAccount(json));

        server.setExecutor(null);
        System.out.println("Starting");
        
        server.start();
        System.out.println(server.getAddress());
        System.out.println(server);

    }
}

class Market implements HttpHandler{
    JsonController json;
    public Market(JsonController json)
    {
        this.json = json;
    }
    public void handle(HttpExchange t) throws IOException{
        String response;

        response = json.GetMarket().toJSONString();

        System.out.println(response);
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

}

class ConnectToAccount implements HttpHandler{

    JsonController json;
    public ConnectToAccount(JsonController json)
    {
        this.json = json;
    }
    public void handle(HttpExchange t) throws IOException{
        String response = json.GetAccounts().toString();

        System.out.println(response);
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

}
class Deal implements HttpHandler {

    JsonController json;
    public Deal(JsonController json){
        this.json = json;
    }
   public void handle(HttpExchange t) throws IOException {
        InputStreamReader isr = new InputStreamReader(t.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String query = br.readLine();
        query = query.substring(2,query.length()-2);
        String part[] = query.split(",");
        Integer id =Integer.parseInt(query.split(",")[0].split(":")[1]);
        Integer count =Integer.parseInt(query.split(",")[1].split(":")[1]);
       try {
           if(json.CheckCountOfBooks(id,count))
           {
               System.out.println("Got it");
               t.sendResponseHeaders(200,0);
           }
           else
           {
               System.out.println("Did not find");
               t.sendResponseHeaders(400, 0);
           }
       } catch (ParseException e) {
           throw new RuntimeException(e);
       }
        OutputStream os = t.getResponseBody();
        os.write("".getBytes());
        os.close();

}
}



