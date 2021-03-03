package OAuth2;

import Pojo.GetCourse;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.urlEncodingEnabled;

public class AuthorizationCodeTest {

    public static void main(String args[]){

    /*    System.setProperty( "webdriver.chrome.driver", "/Users/swathisanthosh/Downloads/Selenium/Drivers/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
        driver.findElement(By.cssSelector("input[type='email']")).sendKeys("sdubutest@gmail.com");
        driver.findElement(By.xpath("//button[@jsname='LgbsSe']")).click();
        driver.findElement(By.cssSelector("input[type='password']")).sendKeys("qa12345678");
        driver.findElement(By.xpath("//button[@jsname='LgbsSe']")).click();
        String url = driver.getCurrentUrl();*/

        String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AY0e-g7kHINYRUHHkuArGSUfo4YViZtLCasw1EOlmPVjyA7FaBdZkbXBWyV6F2kcCyxEqg&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none#";
        String pc = url.split("code=")[1];
        String code = pc.split("&scope")[0];

          String accessTokenResponse = given().queryParams("code", code,
                  "client_id",
                        "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com",
                            "client_secret","erZOWM9g3UtwNRj340YYaK_W",
                           "grant_type","authorization_code","state", "verifyfjdss",
                            "session_state","ff4a89d1f7011eb34eef8cf02ce4353316d9744b..7eb8",
                            "redirect_uri","https://rahulshettyacademy.com/getCourse.php")
                    .when().log().all().post("https://www.googleapis.com/oauth2/v4/token").asString();
          /*          .queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")

                .queryParams("grant_type", "authorization_code")

                .queryParams("state", "verifyfjdss")

                .queryParams("session_state", "ff4a89d1f7011eb34eef8cf02ce4353316d9744b..7eb8")*/


        JsonPath js = new JsonPath(accessTokenResponse);

        String token = js.getString("access_token");

        System.out.println("Token value extracted = " +token);

       /* String response = given().log().all().queryParam("accessToken","token")
                .when().log().all()
                .get("https://rahulshettyacademy.com/getCourse.php")
                .then().extract().response()
                .asString();
        System.out.println(response);*/



    }

}
