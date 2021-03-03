package tests;

import files.payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;


public class PurchaseAmountValidation {

    @Test
    public void SumofCourses() {
        int TotalAmount = 0;

        JsonPath js = new JsonPath(payload.coursePrice());
        int purchaseAmount = js.getInt("dashboard.purchaseAmount");
        int numOfCourses = js.getInt("courses.size()");
        for (int i = 0; i < numOfCourses; i++) {

            TotalAmount += (js.getInt("courses[" + i + "].price"))
                    * (js.getInt("courses[" + i + "].copies"));

        }
        System.out.println("Total Amount calculated is: " + TotalAmount);
        Assert.assertEquals(TotalAmount, purchaseAmount);
    }
}
