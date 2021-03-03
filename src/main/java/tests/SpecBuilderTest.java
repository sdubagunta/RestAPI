package tests;
import Pojo.AddPlace;
import Pojo.Location;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class SpecBuilderTest {
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

        RequestSpecification request = new RequestSpecBuilder()
                                                        .setBaseUri("https://rahulshettyacademy.com")
                                                        .addQueryParam("key","qaclick123")
                                                        .setContentType(ContentType.JSON).build();
        ResponseSpecification resspec  = new ResponseSpecBuilder()
                .expectStatusCode(200).expectContentType(ContentType.JSON).build();

        RequestSpecification req = given().spec(request)
                                        .body(place1);
        Response response=    req.when()
                                        .post("/maps/api/place/add/json")
                                        .then().spec(resspec).extract().response();
    String responseAsString = response.asString();
    System.out.println(responseAsString);
}
}
