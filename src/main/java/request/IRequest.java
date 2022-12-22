package request;

import mediatype.IMediaType;
import java.util.Map;

public interface IRequest {
    IMediaType getMediaType();

    Map<String,Object> getBody();

    String getStringBody();

    String getUrl();

    String getMethod();

    Map<String, String> getHeaders();
}
