package response;

import request.IRequest;

import java.util.List;
import java.util.Map;

public interface IResponse {

     int getCode();

     String getBody();

     Map<String, List<String>> getHeaders();

    List<String> getHeader(String headerKey);

     IRequest getRequest();


}
