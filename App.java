import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.parsers.ParserConfigurationException;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import javax.xml.xpath.*;

public class App {

        public static void main(String[] arg) throws IOException,ParseException, ParserConfigurationException, XPathExpressionException , SAXException, TransformerConfigurationException, TransformerException {


        String filename;
        try{
             filename= arg[0]; //data.json
        }
        catch(ArrayIndexOutOfBoundsException e){
                filename = "data.json";
            }
        String Logfilename;
            try{
                Logfilename= arg[1];
            }
            catch(ArrayIndexOutOfBoundsException e){
                Logfilename = "log4j2.log";
            }
            
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
        server.start();


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

               t.sendResponseHeaders(200,0);
           }
           else
           {

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



