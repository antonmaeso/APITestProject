package request;

import mediatype.IMediaType;
import java.util.Map;

public interface IRequest {
    IMediaType getMediaType();

    String getBody();

    String getUrl();

    String getMethod();

    Map<String, String> getHeaders();
}
