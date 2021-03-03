package tests;

import files.Common;
import files.payload;
import io.restassured.RestAssured;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DynamicJson {

    @Test(dataProvider="BookData")
    public void addBook(String isin, String aisle){
        RestAssured.baseURI="http://216.10.245.166";
        String response =        given().header("Content-Type","application/json").
                body(payload.addBook(isin,aisle)).
                        when()
                        .post("Library/Addbook.php")
                        .then().assertThat().statusCode(200)
                        .extract().response().asString();
        String id = Common.rawToJson(response).get("ID");
        System.out.println(id);
    }

    @DataProvider(name = "BookData")
    public Object[][] dataset(){
        Object dataset[][] = new Object[2][2];
        dataset[0][0]="asfasf";
        dataset[0][1]="slkjksd";
        dataset[1][0]="asf2asf";
        dataset[1][1]="slkjk2sd";
        return dataset;
    }
}
