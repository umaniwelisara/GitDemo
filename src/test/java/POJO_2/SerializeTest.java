package POJO_2;
import files.Payload;
import files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.List;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class SerializeTest {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        RestAssured.baseURI="https://rahulshettyacademy.com";

        AddPlace p =new AddPlace();
        p.setAccuracy(60);
        p.setAddress("28, side layout, cohen 09");
        p.setLanguage("English-UK");
        p.setPhone_number("(+96) 111 444 9202");
        p.setWebsite("https://rahulshettyacademy.com");
        p.setName("Florance Palace");

        List<String> myList =new ArrayList<String>();
        myList.add("shopping mall");
        myList.add("shoe corner");
        myList.add("root");

        p.setTypes(myList);

        Location l =new Location();
        l.setLat(-38.555494);
        l.setLng(33.499962);

        p.setLocation(l);

        Response res=given().log().all().queryParam("key", "qaclick123")
                .body(p)
                .when().post("/maps/api/place/add/json").
                        then().assertThat().statusCode(200).extract().response();

        String responseString=res.asString();
        System.out.println(responseString);


    }
}
