package tests;

import files.payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

public class ComplexJasonParse {
public static void main(String[] args){

    JsonPath js = new JsonPath(payload.coursePrice());
  int numOfCourses  = js.getInt("courses.size()");
  System.out.println("Number of Courses: " +numOfCourses);
  int purchaseAmount = js.getInt("dashboard.purchaseAmount");
  System.out.println("Purchase Amount is: " +purchaseAmount);
  String course1 = js.getString("courses[0].title");
  System.out.println("Title of first course is: " +course1);
String courses = js.getString("courses.title");
for(int i=0; i<numOfCourses; i++){
    System.out.println(js.getString("courses["+i+"].title")
            +"\t"+(js.getString("courses["+i+"].price")));
}

    for(int i=0; i<numOfCourses;i++){
        String courseName = js.getString("courses["+i+"].title");
        if(courseName.equalsIgnoreCase("RPA")){
            System.out.println("Number of copies sold for book RPA: " +js.getInt("courses["+i+"].copies"));
        }
    }


}
    }
