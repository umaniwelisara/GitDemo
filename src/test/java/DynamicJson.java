import files.Payload;
import files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class DynamicJson {


    @Test(dataProvider = "BooksData",enabled=true)//go and search for a data provider called BooksData
    public void addBook(String isbn,String aisle){

        RestAssured.baseURI = "http://216.10.245.166";

      //  RestAssured.useRelaxedHTTPSValidation();

        String addBookResponse=
        given().log().all().header("Content-Type","application/json")
                .body(Payload.AddBook(isbn,aisle))
        .when().post("/Library/Addbook.php")
        .then().assertThat().statusCode(200)
        .extract().response().asString();

        JsonPath js=ReUsableMethods.stringToJson(addBookResponse);
        String id = js.get("ID");
        System.out.println(id);

        //System.out.println("ID returned : " + Payload.DeleteBook(isbn, aisle));
/*
*                   or
*
* Response res=  given().body(Payload.AddBook(isbn,aisle)).when().post("/Library/Addbook.php").then().extract().response();
  System.out.println("Response starts " + res.asString() + " ends");
*
*  */

    }
   @Test(dataProvider="BooksData", enabled=true)
    public void deleteBook(String isbn, String aisle) {

        //RestAssured.baseURI = "https://rahulshettyacademy.com";
        RestAssured.baseURI = "http://216.10.245.166";
       RestAssured.useRelaxedHTTPSValidation();
/*
        Response res=  given().body(Payload.DeleteBook(isbn, aisle)).when().post("/Library/DeleteBook.php").then().extract().response();
        System.out.println("Response starts " + res.asString() + " ends");

                            or
*/
        String deleteBookResponse=
                given().log().all().header("Content-Type","application/json")
                        .body(Payload.DeleteBook(isbn,aisle))
                        .when().post("/Library/DeleteBook.php")
                        .then().assertThat().statusCode(200)
                        //.extract().response().asString();
                        .body("msg",equalTo("book is successfully deleted"))
                .extract().response().asString();
       System.out.println("Response starts " + deleteBookResponse+ " ends");

        JsonPath js=ReUsableMethods.stringToJson(deleteBookResponse);
        String deleteID = js.get("ID");
        System.out.println("delete ID : "+deleteID);
    }

   @DataProvider(name="BooksData")
    public Object[][] getData(){
       return new Object[][]{
                {"1540","ABrCoZF"},{"2540","B5ourZF"},{"4450","Do5utXF"}
        };
   }
}
