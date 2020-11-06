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
        /*get authorization code*/
/*
        //enter this url into browser https://accounts.google.com/o/oauth2/v2/auth/oauthchooseaccount?scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&auth_url=https%3A%2F%2Faccounts.google.com%2Fo%2Foauth2%2Fv2%2Fauth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https%3A%2F%2Frahulshettyacademy.com%2FgetCourse.php&flowName=GeneralOAuthFlow
        //hit enter
        //copy the current url - past under "url" here
         String url="https://rahulshettyacademy.com/getCourse.php?code=4%2F0AfDhmrhndmWEXlF-D75nPJuCUZhsrO00_IWWW_7hH_GE85v_QQxueZ6CYa6SBlfQHF0Lyg&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=2&prompt=consent#";
         String partialCode = url.split("code=")[1];//4%2F0AfDhmrhndmWEXlF-D75nPJuCUZhsrO00_IWWW_7hH_GE85v_QQxueZ6CYa6SBlfQHF0Lyg&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=2&prompt=consent#
        String code1= partialCode.split("&scope")[0];

        System.out.println(code1);
        */

String code="4%2F0AfDhmrjL57Quk-MTuaU-hgos45-KTfFW9_x-Jffy8mv8hssQYOQi4bzCxtYM2rij92wa_g";
// code keeps on changing and can be generated every time using
        //https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php

        /*get access token request*/
        String accessTokenResponse =
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

        System.out.println(accessTokenResponse);
        JsonPath jp = new JsonPath(accessTokenResponse);
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

        System.out.println(accessTokenResponse);
        JsonPath jp2 = new JsonPath(accessTokenResponse2);
        String access_token2 = jp2.getString("access_token");

        System.out.println(access_token2);


        String response2 = given().queryParam("access_token", access_token2).
                when().get("https://rahulshettyacademy.com/getCourse.php").asString();

        System.out.println(response2);
    }
}