package OAuth;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.path.json.JsonPath;
import net.bytebuddy.asm.Advice;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static io.restassured.RestAssured.*;

public class oAuthTest {

    public static void main(String[] args) throws InterruptedException {

/*=========================================AUTHORIZATION CODE GRANT TYPE==============================================*/


String code="4%2F0AfDhmrjL57Quk-MTuaU-hgos45-KTfFW9_x-Jffy8mv8hssQYOQi4bzCxtYM2rij92wa_g";
// code keeps on changing and can be generated every time using
        //https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php

        /*get access token request*/
        String accessTokenResponse0 =
                given()
                        .urlEncodingEnabled(false)//not encode any special characters
                        .queryParams("code", code)
                        .queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                        .queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                        .queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
                        .queryParams("grant_type", "authorization_code")
                        .when().log().all()
                        .post("https://www.googleapis.com/oauth2/v4/token")//.then().extract().response();
                        .asString();

        System.out.println(accessTokenResponse0);
        JsonPath jp = new JsonPath(accessTokenResponse0);
        String access_token = jp.getString("access_token");

        System.out.println(access_token);


        String response = given().queryParam("access_token", access_token).
                when().get("https://rahulshettyacademy.com/getCourse.php").asString();

        System.out.println(response);


/*=========================================CLIENT CREDENTIALS GRANT TYPE==============================================*/
        /*get access token request*/
        String accessTokenResponse2 =
                given()
                        .urlEncodingEnabled(false)//not encode any special characters
                        .queryParams("code", code)
                        .queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                        .queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                        .queryParams("grant_type", "authorization_code")
                        .when().log().all()
                        .post("https://www.googleapis.com/auth/userinfo.email")//.then().extract().response();//scope is here
                        .asString();

        System.out.println(accessTokenResponse0);
        JsonPath jp2 = new JsonPath(accessTokenResponse2);
        String access_token2 = jp2.getString("access_token");

        System.out.println(access_token2);


        String response2 = given().queryParam("access_token", access_token2).
                when().get("https://rahulshettyacademy.com/getCourse.php").asString();

        System.out.println(response2);
    }
}