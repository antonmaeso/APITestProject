package request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "requestBuilders"
})
public class RequestBuilders {
    @JsonProperty("requestBuilders")
    private List<RequestBuilder> requestBuilders = null;

    @JsonProperty("requestBuilders")
    public List<RequestBuilder> getRequestBuilders() {
        return requestBuilders;
    }

    @JsonProperty("requestBuilders")
    public void setRequestBuilders(List<RequestBuilder> requestBuilders) {
        this.requestBuilders = requestBuilders;
    }
}
