import files.Payload;
import io.restassured.path.json.JsonPath;
import org.junit.Assert;
import org.testng.annotations.Test;

public class SumValidation {
    /* Verify if Sum of all Course prices matches with Purchase Amount*/
    @Test
    public void sumOfCourses(){
        JsonPath js = new JsonPath(Payload.CoursePrice());

        int course_Count = js.getInt("courses.size()");

        int sum=0;

        for (int i = 0; i < course_Count; i++) {

            int prices = js.getInt("courses[" + i + "].price");
            int copiesNo = js.getInt("courses[" + i + "].copies");
            int amount=prices*copiesNo;
            System.out.println(" amount of copies x price ="+amount);

            sum=sum+amount;

        }
        System.out.println("total amount ="+sum);
        int purchaseAmount = js.getInt("dashboard.purchaseAmount");
        System.out.println("total amount ="+purchaseAmount);

        Assert.assertEquals(sum,purchaseAmount);
    }
}
