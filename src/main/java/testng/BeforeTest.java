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
import variables.VariableExtractor;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;


public class BeforeTest {
    static {
        PropertyConfigurator.configure("src/main/resources/config/log4j.properties");
    }
    private final VariableExtractor variableExtractor = new VariableExtractor();


    private static final Logger log = Logger.getLogger(BeforeTest.class);

    private static IResponse response = null;

    public static IResponse getResponse() {
        return response;
    }

    @org.testng.annotations.BeforeTest(alwaysRun = true)
    @Parameters({"requestDataFileLocation", "testVariables"})
    public void beforeTest(String requestDataFileLocation, @Optional String testVariables) {
        Map<String, String> testLevelVariablesMap = new HashMap<>();
        variableExtractor.mapTestLevelVariables(testVariables, testLevelVariablesMap);
        JSONArray requests = mapRequestDataToBuilders(requestDataFileLocation);
        BeforeTest.response = makeRequests(requests);
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
                response = new OKAPIExecutor(requestBuilder).execute();
                requests = variableExtractor.mapValueFromResponseToRequest(response, requests, i);
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

}
