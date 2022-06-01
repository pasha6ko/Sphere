import  org.json.simple.*;
import org.json.simple.parser.JSONParser;

import org.json.simple.parser.ParseException;

import javax.imageio.IIOException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
//Класс храняший данные и считывающий JSON файл
public class JsonController {
    String filename; // Путь к файлу
    List<Book> books; // Все книги из файла
    List<Account> accounts; // все аккаунты

    public JsonController(String filename) throws IOException,ParseException{

        this.filename = filename;
        this.books = SetMarket();
        this.accounts = SetAccount();
    }
    public JSONObject GetMarket() //Получает все книги из класса
    {
        JSONArray AllBooks = new JSONArray();
        for(Book i : books)
        {
            AllBooks.add(i.GetJsonData());
        }
        JSONObject res = new JSONObject();
        res.put("products",AllBooks);
        return res;
    }
    public JSONObject GetAccounts()//Получение всех аккаунтов из класса
    {
        JSONArray AllAccounts = new JSONArray();
        for(Account i : accounts)
        {
            AllAccounts.add(i.GetJsonData());
        }
        JSONObject res = new JSONObject();
        res.put("accounts",AllAccounts);
        return res;
    }
    public JSONObject GetJsonFile() throws FileNotFoundException ,ParseException,IOException{ // Возвращает все данные из файла
        JSONParser parser = new JSONParser();
        FileReader reader = new FileReader(new File(filename));
        JSONObject data = (JSONObject) parser.parse(reader);
        return data;
    }
    public List<Book> SetMarket () throws ParseException, IOException //Преобразовывает обектов из файлв в книги
    {
        JSONArray ListOfBooks = (JSONArray) GetJsonFile().get("books");
        List<Book> MarketArray = new ArrayList<>();
        for (int i = 0; i <ListOfBooks.toArray().length ; i++)
        {
            Book b = new Book((JSONObject) ListOfBooks.toArray()[i]);
            if(b.amount>0)
            {
                     MarketArray.add(b);
            }

        }

        JSONObject products = new JSONObject();
        products.put("products",MarketArray);
        return MarketArray;
    }
    public List<Account> SetAccount() throws ParseException, IOException //Преобразовывает обектов из файлв в книги
    {
        JSONArray ListOfAccounts = (JSONArray) GetJsonFile().get("accounts");
        List<Account> CheckedAccounts = new ArrayList<>();
        for (int i = 0; i <ListOfAccounts.toArray().length ; i++)
        {
            Account account = new Account((JSONObject) ListOfAccounts.toArray()[i]);//Для возможных изменений
            CheckedAccounts.add(account);
        }
        JSONObject accounts = new JSONObject();
        accounts.put("accounts",CheckedAccounts);
        return CheckedAccounts;
    }
    public Boolean CheckCountOfBooks(Integer id,Integer count) throws ParseException, IOException //проверяет количество книг
    {

        for (Book i: books)
        {
            if(i.id == id && i.amount>=count)
            {
                return true;
            }
        }

        return false;
    }

}
