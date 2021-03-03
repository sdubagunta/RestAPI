package JIRATEST;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.io.File;

import static io.restassured.RestAssured.given;

public class JiraTest {

    public static void main(String args[]){

    RestAssured.baseURI = "http://localhost:8080";
    //Login

        SessionFilter session = new SessionFilter();
        given().header("Content-Type","application/json")
                .body("{\n" +
                        "  \"username\": \"sdubutest\",\n" +
                        "  \"password\": \"Test@123456%\"\n" +
                        "}").filter(session).when().post("/rest/auth/1/session")
                            .then().extract().response().asString();

        //Add Comment
   String addCommentResponse = given().pathParam("key","PROJ-30").queryParam("JSESSIONID","392F0A271BEBD822C09838D6A94F03C7")
        .header("Content-Type","application/json")
            .body("{\n" +
                    "    \"body\": \"Comment added from RestAPI Automation\",\n" +
                    "    \"visibility\": {\n" +
                    "        \"type\": \"role\",\n" +
                    "        \"value\": \"Administrators\"\n" +
                    "    }\n" +
                    "}").filter(session).when().post("/rest/api/2/issue/{key}/comment")
            .then().assertThat().statusCode(201).extract().response().asString();
        JsonPath js = new JsonPath(addCommentResponse);
       String commentID= js.getString("id");
        String expectedComment = js.getString("body");
    //Add Attachment
        given().pathParam("key","PROJ-30")
                .header("X-Atlassian-Token","no-check")
                .header("Content-Type","multipart/form-data")
                .multiPart("file",new File("Jira.txt"))
                .filter(session).when().post("/rest/api/2/issue/{key}/attachments")
                .then().assertThat().statusCode(200);

        //Get Issue

                String issueDetails=  given().pathParam("key","PROJ-30")
                        .queryParam("fields","comment").filter(session)
                .when().get("/rest/api/2/issue/{key}")
                .then().extract().response().asString();

                System.out.println(issueDetails);

                JsonPath js1 = new JsonPath(issueDetails);
                int numOfComments = js1.get("fields.comment.comments.size()");
                System.out.println("Number of Comments =" +numOfComments);
                for(int i=0; i<numOfComments;i++){
                   String  id= js1.get("fields.comment.comments["+i+"].id").toString();
                    if(id.equalsIgnoreCase( commentID)){
                        String commentAdded =js1.get("fields.comment.comments["+i+"].body").toString();
                        System.out.println(commentAdded);
                        Assert.assertEquals(commentAdded, expectedComment);
                    }
                }
    }
}