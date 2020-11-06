import files.Payload;
import files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class BasicsWithExternalFile {
    public static void main(String[] args) throws IOException {

        RestAssured.baseURI = "https://rahulshettyacademy.com";

        RestAssured.useRelaxedHTTPSValidation();
        /*
        requirement 1 : add a place


        content of json file convert to string
        1. content convert to Byte -> 2. Byte convert to String

          .body(new String(Files.readAllBytes(Paths.get("C:\\Projects\\ideaProjects_workspace\\Addplace.json"))))
        */

//[ADD]------------------------------------------------------------------------------------------

        String response =
                //request----------
                given().log().all().queryParam("key", "qaclick123")
                        .header("Content-Type", "application/json")
                        .body(new String(Files.readAllBytes(Paths.get("C:\\Projects\\ideaProjects_workspace\\Addplace.json"))))
                        //resource, method body----------
                        .when().post("/maps/api/place/add/json")

                        //response---------
                        .then().assertThat().statusCode(200)//response status code validation
                        // log().all().
                        //here use just assertion without extract, use body method
                        .body("scope", equalTo("APP"))//response body details validation
                        .header("Server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();//output validation
        //it extract the all response
        //response variable has the entire body

        System.out.println(response);
        /*

        requirement 2 : add a place,[ADD]
                        update the place with new address,[PUT]
                        get place to validate new address is present in the response[GET]
        */

        //if you want to extract and do validation, you have to write your customize code
        // JsonPath jsonPath= new JsonPath(response); //take string input -> convert it into json -> pass the json

        JsonPath js2 = ReUsableMethods.stringToJson(response);//here we use common method

        String placeID = js2.getString("place_id");//create path
        // eg/ location.lat => parent.child
        //if only one value eg/ place_id
        System.out.println("place id : " + placeID);

    }
}
