package tests;

import files.Common;
import files.payload;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers.*;
import org.testng.*;

public class Basics {

    public static void main(String[] args) {
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        //ADD(POST)
        String response = given().queryParam("key", "qaclick123").header("Content-Type", "application/json").
                body(payload.addPlace).when().post("/maps/api/place/add/json").then().assertThat().statusCode(200)
                .body("scope", equalTo("APP"))
                .header("Server","Apache/2.4.18 (Ubuntu)").extract().response().asString();

        JsonPath js = new JsonPath(response);
        String placeid = js.getString("place_id");
        System.out.println(placeid);
    //GET
        given().log().all().queryParam("key","qaclick123")
                .queryParam("place_id",placeid)
                .when().get("/maps/api/place/get/json");
    //UPDATE

        String newaddress = "70 Summer walk, USA";
        given().log().all().queryParam("key","qaclick123")
                .header("Content-Type", "application/json")
                .body("{\r\n" +
                        "\"place_id\":\""+placeid+"\",\r\n" +
                        "\"address\":\""+newaddress+"\",\r\n" +
                        "\"key\":\"qaclick123\"\r\n" +
                        "}").when().put("maps/api/place/update/json")
                .then().assertThat().log().all().statusCode(200).body("msg",equalTo("Address successfully updated"));

        //GET
        String getPlaceResponse = given().log().all().queryParam("key","qaclick123")
                .queryParam("place_id",placeid)
                .when().get("/maps/api/place/get/json")
                .then().assertThat().log().all().statusCode(200).extract().response().asString();


        String actualaddress = Common.rawToJson(getPlaceResponse).getString("address");
        Assert.assertEquals(actualaddress, newaddress);
    }
}