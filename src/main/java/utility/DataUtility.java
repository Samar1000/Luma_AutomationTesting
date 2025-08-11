package utility;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DataUtility {
    private static final String TEST_DATA_PATH ="src/test/resources/TestData/";
    public static String getJsonData(String fileName, String field){
        try (FileReader reader = new FileReader(TEST_DATA_PATH + fileName + ".json")) {
            JsonElement jsonElement = JsonParser.parseReader(reader);
            String[] keys = field.split("\\.");
            for (String key : keys) {
                if (key.matches(".+\\[\\d+]")) {
                    String arrayKey = key.substring(0, key.indexOf("["));
                    int index = Integer.parseInt(key.replaceAll(".*\\[(\\d+)]", "$1"));
                    jsonElement = jsonElement.getAsJsonObject().get(arrayKey).getAsJsonArray().get(index);
                } else {
                    jsonElement = jsonElement.getAsJsonObject().get(key);
                }
            }
            return jsonElement.getAsString();
        } catch (IOException e) {
            throw new RuntimeException("Error reading JSON file: " + fileName, e);
        }
    }
    public static Map<String, String> getValidSignUpData() {
        Map<String, String> data = new HashMap<>();
        data.put("first name", getJsonData("CreateAnAccountData", "first name"));
        data.put("last name", getJsonData("CreateAnAccountData", "last name"));
        String dataEmail = getJsonData("CreateAnAccountData", "valid email");
        String timestamp = new java.text.SimpleDateFormat("ssmmHHddMMyyyy").format(new java.util.Date());
        String uniqueEmail = dataEmail.replace("@", "+" + timestamp + "@");
        data.put("valid email", uniqueEmail);
        data.put("valid password", getJsonData("CreateAnAccountData", "valid password"));
        return data;
    }
    public static Map<String, String> getValidShippingData() {
        Map<String, String> data = new HashMap<>();
        data.put("email", getJsonData("ShippingData", "email"));
        data.put("firstName", getJsonData("ShippingData", "firstName"));
        data.put("lastName", getJsonData("ShippingData", "lastName"));
        data.put("company", getJsonData("ShippingData", "company"));
        data.put("address.street.line1", getJsonData("ShippingData", "address.street.line1"));
        data.put("address.street.line2", getJsonData("ShippingData", "address.street.line2"));
        data.put("address.street.line3", getJsonData("ShippingData", "address.street.line3"));
        data.put("address.city", getJsonData("ShippingData", "address.city"));
        data.put("address.state", getJsonData("ShippingData", "address.state"));
        data.put("address.zip", getJsonData("ShippingData", "address.zip"));
        data.put("address.country", getJsonData("ShippingData", "address.country"));
        data.put("phone", getJsonData("ShippingData", "phone"));
        return data;
    }
    public static Map<String, String> getRegisteredShippingData() {
        Map<String, String> data = getValidShippingData();
        data.remove("email");
        data.remove("firstName");
        data.remove("lastName");
        return data;
    }
}
