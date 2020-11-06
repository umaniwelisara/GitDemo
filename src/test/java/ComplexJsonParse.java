import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

    public static void main(String[] args) {

        JsonPath js = new JsonPath(Payload.CoursePrice());


        /* Print No of courses returned by API*/
        int courseCount = js.getInt("courses.size()");
        System.out.println("courseCount : " + courseCount);

        /*Print Purchase Amount*/
        int totalAmount = js.getInt("dashboard.purchaseAmount");
        System.out.println("totalAmount : " + totalAmount);

        /* Print Title of the first course*/
        String titleFirstCourse = js.get("courses[0].title");
        System.out.println("titleFirstCourse : " + titleFirstCourse);

        /* Print All course titles and their respective Prices*/
        //array is dynamic
        int course_Count = js.getInt("courses.size()");
        for (int i = 0; i < course_Count; i++) {
            String courseTitle = js.get("courses[" + i + "].title");
            int coursePrice = js.getInt("courses[" + i + "].price");
            System.out.println("courseTitle : " + courseTitle + " _ coursePrice : " + coursePrice);
        }

        /* Print no of copies sold by RPA Course*/
        //array is dynamic
        for (int i = 0; i < course_Count; i++) {
            String courseTitle = js.get("courses[" + i + "].title");
            if (courseTitle.equalsIgnoreCase("RPA")) {
                int copyCount = js.getInt("courses[" + i + "].copies");
                System.out.println("course title :RPA and copies count:" + copyCount);
                break;
            }

        }

       /* Verify if Sum of all Course prices matches with Purchase Amount*/
            //its at SumValidation.java

        }




}
