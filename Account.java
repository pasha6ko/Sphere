import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Account { //Класс хранящий в себе все данные об одном аккаунте
    public  Account(JSONObject accountdata)
    {
        this.id = Integer.parseInt(accountdata.get("id").toString());
        this.nickname = accountdata.get("nickname").toString();
        this.balance = Integer.parseInt(accountdata.get("id").toString());
        this.have = (JSONArray) accountdata.get("books");

    }
    public JSONObject GetJsonData() //Возвращает все данные в виде json обекта
    {
        JSONObject JSBook = new JSONObject();
        JSBook.put("id" , id);
        JSBook.put("nickname" , nickname);
        JSBook.put("balance" , balance);
        JSBook.put("books",have);

        return JSBook;
    }
    int id;
    String nickname;
    float balance;

    JSONArray have;
}
