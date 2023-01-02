package testng;
import jsonmanagment.FilesManager;
import okwrapper.OKAPIExecutor;
import okwrapper.OKAPIListener;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.*;
import request.Request;
import request.RequestBuilder;
import jsonmanagment.ObjectMapping;
import response.IResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BeforeTest {
    static {
        PropertyConfigurator.configure("src/main/resources/config/log4j.properties");
    }

    private static final Logger log = Logger.getLogger(BeforeTest.class);

    private static IResponse response = null;
    public static Map<String,String> testVariables = new HashMap<>();

    @org.testng.annotations.BeforeTest(alwaysRun = true)
    @Parameters({"requestDataFileLocation", "testVariables"})
    public void beforeTest(String requestDataFileLocation, @Optional String testVariables) {
        Map<String, String> testLevelVariablesMap = new HashMap<>();
        mapTestLevelVariables(testVariables, testLevelVariablesMap);
        BeforeTest.testVariables.putAll(testLevelVariablesMap);
        JSONArray requests = mapRequestDataToBuilders(requestDataFileLocation);
        BeforeTest.response = makeRequests(requests);
    }

    private void mapTestLevelVariables(String testVariables, Map<String, String> testLevelVariablesMap) {
        JSONObject testLevelVariablesJSON = new JSONObject(testVariables);
        for (String key : testLevelVariablesJSON.keySet()) {
            testLevelVariablesMap.put(key, testLevelVariablesJSON.getString(key));
        }
    }

    public static IResponse getResponse() {
        return response;
    }

    private JSONArray mapRequestDataToBuilders(String requestDataFileLocation) {
        try {
            log.info("request Data File Location " + requestDataFileLocation);
            return new JSONArray(new String(Files.readAllBytes(FilesManager.getFile(requestDataFileLocation).toPath())));
        } catch (IOException e) {
            log.debug(e);
            throw new RuntimeException(e);
        }
    }

    private IResponse makeRequests(JSONArray requests) {
        IResponse response = null;
        for (int i = 0; i < requests.length(); i++) {
            JSONObject request = requests.getJSONObject(i);
            if(request.get("method").equals("CALLBACK")){
                spinUpServerForCallBackThenDestroy();
            } else {
                String stringRequest = String.valueOf(request);
                log.info("Request: " + i + " " + stringRequest);
                Request requestBuilder = new ObjectMapping<>(RequestBuilder.class).stringToObjectMapper(stringRequest).build();
                log.info("variables " + testVariables);
                response = new OKAPIExecutor(requestBuilder).execute();
                requests = extractVariableFromNextRequest(response, requests, i);
            }
        }
        return response;
    }

    private void spinUpServerForCallBackThenDestroy() {
        try {
            new OKAPIListener().createNewListener();
        } catch (Exception e) {
            log.debug(e);
            throw new RuntimeException(e);
        }
    }

    private JSONArray extractVariableFromNextRequest(IResponse response, JSONArray requests, int i) {
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

}
