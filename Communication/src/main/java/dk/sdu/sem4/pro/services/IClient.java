package dk.sdu.sem4.pro.services;
import org.json.JSONObject;
public interface IClient {
    public JSONObject receive();

    //Returnér en fejlkode baseret på int.
    public Integer send(JSONObject jsonObject);
}
