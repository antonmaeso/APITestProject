package testng;
import okwrapper.OKAPIExecutor;
import org.json.JSONObject;
import org.testng.annotations.*;
import request.RequestBuilder;
import jsonmanagment.ObjectMapping;
import request.RequestBuilders;
import response.IResponse;

import java.io.File;
import java.util.List;

public class BeforeTest {

    private static IResponse response = null;

    @org.testng.annotations.BeforeTest(alwaysRun = true)
    @Parameters({"requestData", "testVariables"})
    public void beforeTest(String requestData, @Optional String testVariables) {
        ObjectMapping<RequestBuilders> objectMapper = new ObjectMapping<>();
        File file = objectMapper.withJsonFile(requestData);
        RequestBuilders requestBuilders =  objectMapper.objectMapper(file, RequestBuilders.class);
        IResponse response = null;
        for (RequestBuilder requestBuilder : requestBuilders.getRequestBuilders()) {
            response = new OKAPIExecutor(requestBuilder.build()).execute();
        }
        BeforeTest.response = response;
    }

    public static IResponse getResponse() {
        return response;
    }
}
