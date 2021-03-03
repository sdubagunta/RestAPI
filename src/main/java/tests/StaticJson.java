package tests;

import files.payload;
import io.restassured.RestAssured;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class StaticJson {
    public static void main(String[] args) throws IOException {
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        //ADD(POST)
        String response = given().queryParam("key", "qaclick123").header("Content-Type", "application/json").
                body(new String(Files.readAllBytes(Paths.get("/Users/swathisanthosh/Downloads/RestAssured API/AddPlace.Json")))).when().post("/maps/api/place/add/json").then().assertThat().statusCode(200)
                .body("scope", equalTo("APP"))
                .header("Server","Apache/2.4.18 (Ubuntu)").extract().response().asString();

    }
}
