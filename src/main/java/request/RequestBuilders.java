package request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.json.JSONObject;

import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "requestBuilders"
})
public class RequestBuilders {

    @JsonProperty("requests")
    private List<JSONObject> requests = null;

    @JsonProperty("requestBuilders")
    private List<RequestBuilder> requestBuilders = null;

    @JsonProperty("requests")
    public List<JSONObject> getRequests() {
        return requests;
    }

    @JsonProperty("requests")
    public void setRequests(List<JSONObject> requests) {
        this.requests = requests;
    }

    @JsonProperty("requestBuilders")
    public List<RequestBuilder> getRequestBuilders() {
        return requestBuilders;
    }

    @JsonProperty("requestBuilders")
    public void setRequestBuilders(List<RequestBuilder> requestBuilders) {
        this.requestBuilders = requestBuilders;
    }

//    public List<String> getRequestsString() {
//
//        return requests.stream()
//                .map(Object::JSONObject)
//                .collect(Collectors.toList());
//    }

}
