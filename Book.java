
import org.json.simple.*;


public class Book {
    public Book( JSONObject obj)
    {
        System.out.println(obj.toString());
        this.id = Integer.parseInt( obj.get("id").toString());
        this.name = (String) obj.get("name");
        this.author = (String)obj.get("author");
        this.price = Float.parseFloat(obj.get("price").toString());
        this.amount =  Integer.parseInt( obj.get("amount").toString());
    }
    public JSONObject GetJsonData()
    {
        JSONObject JSBook = new JSONObject();
        JSBook.put("id" , id);
        JSBook.put("name" , name);
        JSBook.put("author" , author);
        JSBook.put("price" , price);
        JSBook.put("amount" , amount);
        return JSBook;
    }

    int id;
    String name;
    String author;
    float price;
    int amount;

}
