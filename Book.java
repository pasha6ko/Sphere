
import org.json.simple.*;


public class Book { // Хранит в себе все данные об ожной книге
    public Book( JSONObject obj)
    {
        this.id = Integer.parseInt( obj.get("id").toString());
        this.name = (String) obj.get("name");
        this.author = (String)obj.get("author");
        this.price = Float.parseFloat(obj.get("price").toString());
        this.amount =  Integer.parseInt( obj.get("amount").toString());
    }
    public JSONObject GetJsonData() //Возвращает все данные в виде json обекта
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
