package tests;


import Pojo.AddPlace;
import Pojo.Location;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class SerializationTest {
    public static void main(String[] args){
    RestAssured.baseURI = "https://rahulshettyacademy.com";
    AddPlace place1 = new AddPlace();
        List<String> types = new ArrayList<String>();
        Location location = new Location();

    place1.setAccuracy(50);
    place1.setName("Frontline house");
    place1.setPhone_number("(+91) 983 893 3937");
    place1.setAddress("29, side layout, cohen 09");
    place1.setLanguage("French-IN");
    place1.setWebsite("http://google.com");

        location.setLat(-38.383494);
        location.setLng(33.427362);
        place1.setLocation(location);

        types.add("shoe park");
        types.add("shop");
        place1.setTypes(types);
    Response response = given().queryParam("key","qaclick123")
            .body(place1)
            .when()
            .post("/maps/api/place/add/json")
            .then().assertThat().statusCode(200).extract().response();
    String responseAsString = response.asString();
    System.out.println(responseAsString);
}
}
