package testng;
import jsonmanagment.IObjectMapper;
import org.json.JSONObject;
import org.testng.annotations.*;
import request.RequestBuilder;
import jsonmanagment.ObjectMapping;

import java.io.File;
import java.util.List;

import static org.testng.AssertJUnit.assertEquals;

public class BeforeTest {

    JSONObject json = null;

    @org.testng.annotations.BeforeTest(alwaysRun = true)
    @Parameters({"requestData", "testVariables"})
    public void beforeTest(String requestData, @Optional String testVariables) {
        ObjectMapping<RequestBuilder> objectMapper = new ObjectMapping<RequestBuilder>();
        File file = objectMapper.withJsonFile(requestData);
        List<RequestBuilder> requestBuilders =  objectMapper.objectMapper(file, RequestBuilder.class);

    }

}
