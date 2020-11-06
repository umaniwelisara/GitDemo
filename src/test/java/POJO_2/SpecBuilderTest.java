package POJO_2;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;

public class SpecBuilderTest {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        RestAssured.baseURI="https://rahulshettyacademy.com";

        AddPlace p =new AddPlace();
        p.setAccuracy(610);
        p.setAddress("28B, Parkland layout, Kalon 09");
        p.setLanguage("English-UK");
        p.setPhone_number("(+96) 000 444 9202");
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

        //----------------------------------------------------------------------------utility class
//start with set
       RequestSpecification reqSpec= new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addQueryParam("key", "qaclick123")
                .setContentType(ContentType.JSON)
                .build();
//start with expect
        ResponseSpecification resSpec = new ResponseSpecBuilder()
               .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();
        //____________________________________________________________________________test case class
        RequestSpecification response=given().spec(reqSpec).body(p);

      /*
        Response res=given().log().all().queryParam("key", "qaclick123")
                .body(p)
                .when().post("/maps/api/place/add/json").
                        then().assertThat().statusCode(200).extract().response();
    */

        Response res=
                response
                .when().post("/maps/api/place/add/json")
                        .then()
                .spec(resSpec).extract().response();

        String responseString=res.asString();
        System.out.println("response : "+responseString);


    }
}
