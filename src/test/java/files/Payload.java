package files;

public class Payload {

    public static String AddPlace(){
        return "{\n" +
                "    \"location\": {\n" +
                "        \"lat\": -38.383494,\n" +
                "        \"lng\": 33.427362\n" +
                "    },\n" +
                "    \"accuracy\": 50,\n" +
                "    \"name\": \"Sewana Pedesa\",\n" +
                "    \"phone_number\": \"(+91) 000 893 3937\",\n" +
                "    \"address\": \"29, pettah, colombo\",\n" +
                "    \"types\": [\n" +
                "        \"shoe park\",\n" +
                "        \"shop\"\n" +
                "    ],\n" +
                "    \"website\": \"http://google.com\",\n" +
                "    \"language\": \"French-IN\"\n" +
                "}";
    }
 /*   public static String UpdatePlace(){
        return "{\n" +
                "\"place_id\":\""+placeID+"\",\n" +
                "\"address\":\"70B, Floria, Sri Lanka\",\n" +
                "\"key\":\"qaclick123\"\n" +
                "}";
    }*/

    public static String CoursePrice(){
        return "{\n" +
                "  \"dashboard\": {\n" +
                "    \"purchaseAmount\": 910,\n" +
                "    \"website\": \"rah ulshettyacademy.com\"\n" +
                "  },\n" +
                "  \"courses\": [\n" +
                "    {\n" +
                "      \"title\": \"selenium python\",\n" +
                "      \"price\": 50,\n" +
                "      \"copies\": 6\n" +
                "    },\n" +
                "    {\n" +
                "      \"title\": \"cypress\",\n" +
                "      \"price\": 40,\n" +
                "      \"copies\": 4\n" +
                "    },\n" +
                "    {\n" +
                "      \"title\": \"RPA\",\n" +
                "      \"price\": 45,\n" +
                "      \"copies\": 10\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }


    public static String AddBook(String isbn,String aisle){
        String payload_addBook="{\n" +
        "\n" +
        "\"name\":\"Ammeelia\",\n" +
        "\"isbn\":\""+isbn+"\",\n" +
        "\"aisle\":\""+aisle+"\",\n" +
        "\"author\":\"Doll Ghost\"\n" +
        "}\n" +
        " \n";
        return payload_addBook;
    }

    public static String DeleteBook(String isbn,String aisle){
        String id=isbn+aisle;

        String payload_deleteBook="{\n" +
                " \n" +
                "\"ID\" : \""+id+"\"\n" +
                " \n" +
                "}\n";
        return payload_deleteBook;

    }
}
