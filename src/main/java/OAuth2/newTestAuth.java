package OAuth2;

import Pojo.Api;
import Pojo.GetCourse;
import Pojo.courses;
import Pojo.webAutomation;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.sun.corba.se.impl.util.Version.asString;
import static io.restassured.RestAssured.given;

public class newTestAuth {

    public static void main(String[] args) throws InterruptedException {

        String code = "4%2F0AY0e-g7vdGxcAAuotKpqOdfL4sRtyHVIJiVFb7wiB2uWVf29UKGrwRij162UaMdS4MOaGQ";
// code keeps on changing and can be generated everytime using
        //https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php


        String accessTokenResponse = given()
                .urlEncodingEnabled(false)
                .queryParams("code", code)
                .queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
                .queryParams("grant_type", "authorization_code")
                .when().log().all()
                .post("https://www.googleapis.com/oauth2/v4/token")
                .then().extract().response()
                .asString();

        System.out.println(accessTokenResponse);
        JsonPath jp = new JsonPath(accessTokenResponse);
        String access_token = jp.getString("access_token");

        System.out.println(access_token);


     /*String response = given().queryParam("access_token", access_token).
                when().get("https://rahulshettyacademy.com/getCourse.php").asString();

        System.out.println(response);*/

        GetCourse gc = given().queryParam("access_token", access_token)
                .expect().defaultParser(Parser.JSON)
                .when()
                .get("https://rahulshettyacademy.com/getCourse.php")
                .as(GetCourse.class);
            System.out.println(gc.getInstructor());
            System.out.println(gc.getLinkedIn());

    List<Api> apicourses = gc.getCourses().getApi();
    for(int i=0; i<apicourses.size(); i++){
        if(apicourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")){
            System.out.println(apicourses.get(i).getPrice());
        }
    }
        ArrayList<String> a = new ArrayList<String>();
    String[] courseslist = {"Selenium Webdriver Java", "Cypress","Protractor"};
    List <webAutomation> automationCourses = gc.getCourses().getWebAutomation();
    for(int i=0; i<automationCourses.size(); i++){
        a.add(automationCourses.get(i).getCourseTitle());
    }
    List<String> expectedCoursesList = Arrays.asList(courseslist);
    Assert.assertEquals(expectedCoursesList,a);
    }

    }
