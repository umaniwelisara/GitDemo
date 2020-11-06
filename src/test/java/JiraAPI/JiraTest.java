package JiraAPI;

import files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import org.junit.Assert;

import java.io.File;

import static io.restassured.RestAssured.*;

public class JiraTest {

    public static void main(String[] args) {

        RestAssured.baseURI = "http://localhost:8080";
        /*login scenario*/
        SessionFilter session = new SessionFilter();

        /*handle HTTPS certification validation*/
        given().relaxedHTTPSValidation().log().all().header("Content-Type", "application/json")
                .body("{ \"username\": \"automation.mymail\", \"password\": \"Umani@123\" }")
                .log().all().filter(session)
                .when().post("/rest/auth/1/session")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();



        /*add new comment*/

        String expectedMessage = "comment out the expected message. working fine.";

        String addCommentRes = given().log().all().pathParam("id", "10100")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"body\":\"" + expectedMessage + "\" ,\n" +
                        "  \"visibility\":{\n" +
                        "      \"type\":\"role\",\n" +
                        "      \"value\":\"Administrators\"\n" +
                        "  }\n" +
                        "}")
                .filter(session).when().post("/rest/api/2/issue/{id}/comment")
                .then().log().all().assertThat().statusCode(201).extract().response().asString();

        JsonPath j1 = new JsonPath(addCommentRes);
        String commentID = j1.getString("id");

        /* add attachment to existing issue*/

        //curl -D- -u admin:admin -X POST -H "X-Atlassian-Token: no-check" -F "file=@myfile.txt" https://your-domain.atlassian.net/rest/api/3/issue/TEST-123/attachments

        given().log().all().pathParam("id", "10100").filter(session)
                .header("X-Atlassian-Token", "no-check")
                .header("Content-Type", "multipart/form-data") //bcs we send multipart method:a file
                .multiPart("file", new File("jira.txt"))
                //u have to pass file argument inside this
                //if file is project level, give name directly.if not, give full path
                .when().post("/rest/api/2/issue/{id}/attachments")
                .then().log().all().assertThat().statusCode(200);


        /*integrate quary params and path params both in a single test*/

        /*1.  get issue*/
        String issueDetails = given().filter(session)
                .pathParam("id", "10100")//RETURN ALL THE FIELDS IN THE RESPONSE
                .queryParam("fields", "comment")//FILTER ONLY COMMENTS FROM THE ALL THE FIELDS
                .when().get("/rest/api/2/issue/{id}")
                .then().log().all().extract().response().asString();

        System.out.println("---------issueDetails--------  : " + issueDetails);

        /*2.  check the comment you have added is displaying under comment or not*/
        JsonPath j = new JsonPath(issueDetails);
        int commentCount = j.getInt("fields.comment.comments.size");

        for (int i = 0; i < commentCount; i++) {
            String commentIdIssue = j.get("fields.comment.comments[" + i + "].id").toString();

            if (commentIdIssue.equalsIgnoreCase(commentID)) {
                String message = j.get("fields.comment.comments[" + i + "].body").toString();
                System.out.println(message);
                Assert.assertEquals(message, expectedMessage);
            }

        }
    }

}
