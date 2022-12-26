package testng;
import jsonmanagment.FilesManager;
import okwrapper.OKAPIExecutor;
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

    private static IResponse response = null;
    public static Map<String,String> testVariables = new HashMap<>();

    @org.testng.annotations.BeforeTest(alwaysRun = true)
    @Parameters({"requestDataFileLocation", "testVariables"})
    public void beforeTest(String requestDataFileLocation, @Optional String testVariables) {
        JSONArray requests = mapRequestDataToBuilders(requestDataFileLocation);
        BeforeTest.response = makeRequests(requests);
    }

    public static IResponse getResponse() {
        return response;
    }

    private JSONArray mapRequestDataToBuilders(String requestDataFileLocation) {
        try {
            return new JSONArray(new String(Files.readAllBytes(FilesManager.getFile(requestDataFileLocation).toPath())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private IResponse makeRequests(JSONArray requests) {
        IResponse response = null;
        for (int i = 0; i < requests.length(); i++) {
            String request = requests.getJSONObject(i).toString();
            Request requestBuilder = new ObjectMapping<>(RequestBuilder.class).stringToObjectMapper(request).build();
            response = new OKAPIExecutor(requestBuilder).execute();
            requests = extractvariableFromNextRequest(response, requests, i);
        }
        return response;
    }

    private JSONArray extractvariableFromNextRequest(IResponse response, JSONArray requests, int i) {
        if (i != requests.length() - 1 && response != null) {
            String request = requests.getJSONObject(i+1).toString();
            List<String> variables = getVariables(request);
            for (String var: variables) {
                request = new JSONObject(response.getBody()).get(var.substring(1, var.length()-1)).toString();
                testVariables.put(var, request);

            }
            String stringRequests = null;
            for (Map.Entry<String, String> entry : testVariables.entrySet()) {
                stringRequests = requests.toString().replace(entry.getKey(), entry.getValue());
            }
            return new JSONArray(stringRequests);

        }
        return requests;
    }

    private List<String> getVariables(String jsonString) {
        Pattern pattern = Pattern.compile("#.*#");
        Matcher matcher = pattern.matcher(jsonString);

        List<String> key = new ArrayList<>();
        if (matcher.find()) {
            key.add(matcher.group(0));
        }
        return key;
    }

}
