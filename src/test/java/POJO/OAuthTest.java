package POJO;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;


public class OAuthTest {

    public static void main(String[] args) throws InterruptedException {
        // TODO Auto-generated method stub

        /*

        1 go to below account
        https://accounts.google.com/o/oauth2/v2/auth/oauthchooseaccount?scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&auth_url=https%3A%2F%2Faccounts.google.com%2Fo%2Foauth2%2Fv2%2Fauth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https%3A%2F%2Frahulshettyacademy.com%2FgetCourse.php&state=verifyfjdss&flowName=GeneralOAuthFlow
        2 click gmail account
        3 get the code from the url

        WebDriverManager.chromedriver().setup();
        WebDriver driver= new ChromeDriver();
        driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php&state=verifyfjdss");
        driver.findElement(By.cssSelector("input[type='email']")).sendKeys("fdfd");
        driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
        Thread.sleep(3000);
        driver.findElement(By.cssSelector("input[type='password']")).sendKeys("fxfe");
        driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
        Thread.sleep(4000);
        String url=driver.getCurrentUrl();
        String partialcode=url.split("code=")[1];
        String code=partialcode.split("&scope")[0];
        System.out.println(code);
*/
       String code="4%2F5wEbjyzu5fFE9tLB8Qx_29fOQu8Cfg1hDfqb-uj2RkxG4mRb76llxckKl6h9I4R-e5x9Rf_HCa3H_U4adFuKHyU";
        //   tagname[attribute='value']

        String accessTokenResponse=	given().urlEncodingEnabled(false)
                .queryParams("code",code)
                .queryParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .queryParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
                .queryParams("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
                .queryParams("grant_type","authorization_code")
                .when().log().all()
                .post("https://www.googleapis.com/oauth2/v4/token").asString();
        JsonPath js=new JsonPath(accessTokenResponse);
        String accessToken=js.getString("access_token");

        GetCourse gc=
                given().queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON)
                                                    //expecting default format as json
                .when()
                .get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);


        System.out.println(gc);

        System.out.println("linkedIn : "+gc.getLinkedIn());
        System.out.println("instructor : "+gc.getInstructor());

        //get the value using indexes

        //requirement: print course title of 1st index of given Api.
        System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
                                                    //get(1)-get the value of 1st index from the array.not 0th.

        //get the value using dynamic array

        //requirement: print the price of course title "SoapUI Webservices testing", under api
        List<Api> apiCourses=gc.getCourses().getApi();
        for(int i=0;i<apiCourses.size();i++)
        {
            if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
            {
                System.out.println(apiCourses.get(i).getPrice());
            }
        }

        //Get the course names of WebAutomation

        //requirement: print all course titles under course title of "webAutomation"


        ArrayList<String> a= new ArrayList<String>();//actual arraylist
        String[] courseTitles= { "Selenium Webdriver Java","Cypress","Protractor"};//expected array

        List<WebAutomation> w=gc.getCourses().getWebAutomation();
        //come to courses , then come to webAutomation, this web Automation is a list

        for(int j=0;j<w.size();j++)
        {
            a.add(w.get(j).getCourseTitle());//add values to array "a"
        }

        List<String> expectedList=	Arrays.asList(courseTitles);//convert "courseTitles" to arraylist

        Assert.assertTrue(a.equals(expectedList));

        System.out.println("actual list : "+a.toString());
        System.out.println("expected list : "+expectedList.toString());

        //System.out.println(response);

    }
}
