import files.Payload;
import files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.*;
import org.junit.Assert;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Basics {
    public static void main(String[] args) {

        RestAssured.baseURI = "https://rahulshettyacademy.com";

        RestAssured.useRelaxedHTTPSValidation();
        /*
        requirement 1 : add a place
        */

//[ADD]------------------------------------------------------------------------------------------

        String response0 =
                //request----------
                given().log().all().queryParam("key", "qaclick123")
                        .header("Content-Type", "application/json")
                        .body(Payload.AddPlace())
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

        System.out.println(response0);
        /*

        requirement 2 : add a place,[ADD]
                        update the place with new address,[PUT]
                        get place to validate new address is present in the response[GET]
        */

        //if you want to extract and do validation, you have to write your customize code
        // JsonPath jsonPath= new JsonPath(response);
        // take string input -> convert it into json -> pass the json

        JsonPath js2 = ReUsableMethods.stringToJson(response0);//here we use common method

        String placeID = js2.getString("place_id");//create path
        // eg/ location.lat => parent.child
        //if only one value eg/ place_id
        System.out.println("place id : " + placeID);

//[UPDATE]-------------------------------------------------------------------------------------------
        String newAddress = "70BCD, Battaramulla, Sri Lanka";

        given().log().all().queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "\"place_id\":\"" + placeID + "\",\n" +
                        "\"address\":\"" + newAddress + "\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}")
                .when().put("/maps/api/place/update/json")
                .then().assertThat().log().all().statusCode(200) //if the status is successfuly updated,then you can get json response
                .body("msg", equalTo("Address successfully updated"));
//[GET]-----------------------------------------------------------------------------------------------
        /*
        given().log().all().queryParam("key","qaclick123")
                .queryParam("place_id",placeID)
        .when().get("/maps/api/place/get/json")
                .then().assertThat().log().all().statusCode(200)
                .body("address",equalTo(newAddress));

                or
        */

        String getPlaceResponse =
                given().log().all()
                .queryParam("key", "qaclick123")
                .queryParam("place_id", placeID)
                .when().get("/maps/api/place/get/json")
                .then().assertThat().log().all().statusCode(200).extract().response().asString();

        JsonPath js1 = ReUsableMethods.stringToJson(getPlaceResponse);
        //  JsonPath js=new JsonPath(getPlaceResponse);
        String actualAddress = js1.getString("address");
        System.out.println("address : " + actualAddress);

        Assert.assertEquals(actualAddress, newAddress);

    }
}
