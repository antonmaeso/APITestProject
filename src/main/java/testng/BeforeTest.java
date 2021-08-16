package testng;
import jsonmanagment.FilesManager;
import okwrapper.OKAPIExecutor;
import org.testng.annotations.*;
import request.RequestBuilder;
import jsonmanagment.ObjectMapping;
import request.RequestBuilders;
import response.IResponse;

public class BeforeTest {

    private static IResponse response = null;

    @org.testng.annotations.BeforeTest(alwaysRun = true)
    @Parameters({"requestDataFileLocation", "testVariables"})
    public void beforeTest(String requestDataFileLocation, @Optional String testVariables) {
        RequestBuilders requestBuilders = mapRequestDataToBuilders(requestDataFileLocation);
        BeforeTest.response = makeRequests(requestBuilders);
    }

    public static IResponse getResponse() {
        return response;
    }

    private RequestBuilders mapRequestDataToBuilders(String requestDataFileLocation) {
        return new ObjectMapping<>(RequestBuilders.class).objectMapper(FilesManager.getFile(requestDataFileLocation));
    }

    private IResponse makeRequests(RequestBuilders requestBuilders) {
        IResponse response = null;
        for (RequestBuilder requestBuilder : requestBuilders.getRequestBuilders()) {
            response = new OKAPIExecutor(requestBuilder.build()).execute();
        }
        return response;
    }

}
