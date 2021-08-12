package testng;
import okwrapper.OKAPIExecutor;
import org.json.JSONObject;
import org.testng.annotations.*;
import request.RequestBuilder;
import jsonmanagment.ObjectMapping;
import response.IResponse;

import java.io.File;
import java.util.List;

public class BeforeTest {

    private static IResponse response = null;

    @org.testng.annotations.BeforeTest(alwaysRun = true)
    @Parameters({"requestData", "testVariables"})
    public void beforeTest(String requestData, @Optional String testVariables) {
        ObjectMapping<RequestBuilder> objectMapper = new ObjectMapping<>();
        File file = objectMapper.withJsonFile(requestData);
        List<RequestBuilder> requestBuilders =  objectMapper.objectMapper(file, RequestBuilder.class);
        IResponse response = null;
        for (RequestBuilder requestBuilder : requestBuilders) {
            response = new OKAPIExecutor(requestBuilder.build()).execute();
        }
        BeforeTest.response = response;
    }

    public static IResponse getResponse() {
        return response;
    }
}
