package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class JsonDataReader {
    private static final JsonNode rootNode;

    static {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream input = JsonDataReader.class.getClassLoader().getResourceAsStream("testData.json")) {
            if (input == null) {
                throw new RuntimeException("Unable to find testData.json in src/test/resources");
            }
            rootNode = mapper.readTree(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse testData.json", e);
        }
    }

    public static JsonNode getNode(String key) {
        return rootNode.get(key);
    }

    public static String getString(String key) {
        return rootNode.get(key).asText();
    }

    public static String getNestedString(String parentKey, String childKey) {
        return rootNode.get(parentKey).get(childKey).asText();
    }

    public static Object[][] getInvalidLoginsData() {
        JsonNode invalidArray = rootNode.get("invalidLogins");
        Object[][] data = new Object[invalidArray.size()][2];
        for (int i = 0; i < invalidArray.size(); i++) {
            data[i][0] = invalidArray.get(i).get("username").asText();
            data[i][1] = invalidArray.get(i).get("password").asText();
        }
        return data;
    }
}