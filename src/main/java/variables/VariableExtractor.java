package variables;

import jsonmanagment.FilesManager;
import org.json.JSONArray;
import org.json.JSONObject;
import response.IResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariableExtractor {

    private static final Map<String,String> testVariables = new HashMap<>();

    private String environment = "src/main/resources/environment/env.json";


    public static void setTestVariables(Map<String, String> testVariables) {
        VariableExtractor.testVariables.putAll(testVariables);
    }

    public void loadEnvironmentVariables(){
        Map<String, String> testLevelVariablesMap = new HashMap<>();
        JSONObject testLevelVariablesJSON = null;
        try {
            testLevelVariablesJSON = new JSONObject(new String(Files.readAllBytes(FilesManager.getFile(environment).toPath())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (String key : testLevelVariablesJSON.keySet()) {
            testLevelVariablesMap.put(key, testLevelVariablesJSON.getString(key));
        }
        VariableExtractor.setTestVariables(testLevelVariablesMap);
    }

    public JSONArray mapValueFromResponseToRequestVariables(IResponse response, JSONArray requests, int i) {
        if (i != requests.length() - 1 && response != null) {
            String request = requests.getJSONObject(i+1).toString();
            List<String> variables = getVariables(request);
            for (String var: variables) {
                String varOriginal = var.split(" ")[0];
                request = new JSONObject(response.getBody()).get(varOriginal).toString();
                testVariables.put(var, request);
            }
            requests = getReplaceVariableInNextRequest(requests);
        }
        return requests;
    }

    public void mapTestLevelVariables(String testVariables) {
        JSONObject testLevelVariablesJSON = new JSONObject(testVariables);
        Map<String, String> testLevelVariablesMap = new HashMap<>();
        for (String key : testLevelVariablesJSON.keySet()) {
            testLevelVariablesMap.put(key, testLevelVariablesJSON.getString(key));
        }
        VariableExtractor.setTestVariables(testLevelVariablesMap);

    }

    private List<String> getVariables(String jsonString) {
        Pattern pattern = Pattern.compile("#.*#");
        Matcher matcher = pattern.matcher(jsonString);

        List<String> variables = new ArrayList<>();
        while (matcher.find()) {
            String variable = matcher.group(0).substring(1, matcher.group(0).length() - 1);
            variables.add(variable);
        }
        return variables;
    }

    private JSONArray getReplaceVariableInNextRequest(JSONArray requests) {
        StringBuilder sb = new StringBuilder(requests.toString());
        for (Map.Entry<String, String> entry : testVariables.entrySet()) {
            int startIndex = sb.indexOf("#" + entry.getKey() + "#");
            while (startIndex != -1) {
                sb.replace(startIndex, startIndex + entry.getKey().length() + 2, entry.getValue());
                startIndex = sb.indexOf("#" + entry.getKey() + "#");
            }
        }
        return new JSONArray(sb.toString());
    }

}
