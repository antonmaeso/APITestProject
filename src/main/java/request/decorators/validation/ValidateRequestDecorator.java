package request.decorators.validation;

import mediatype.IMediaType;
import request.IRequest;
import request.decorators.RequestDecorator;

import java.util.Map;

public class ValidateRequestDecorator extends RequestDecorator {
    public ValidateRequestDecorator(IRequest request) {
        super(request);
    }

    @Override
    public IMediaType getMediaType() {
        try {
            mustBeBothMediaTypeAndBodyOrNeither();
        } catch (RequestValidationException e) {
            e.printStackTrace();
        }
        return request.getMediaType();
    }

    @Override
    public String getBody() {
        try {
            mustBeBothMediaTypeAndBodyOrNeither();
        } catch (RequestValidationException e) {
            e.printStackTrace();
        }
        return request.getBody();
    }

    @Override
    public String getUrl() {
        return request.getUrl();
    }

    @Override
    public String getMethod() {
        return request.getMethod();
    }

    @Override
    public Map<String, String> getHeaders() {
        return request.getHeaders();
    }

    private void mustBeBothMediaTypeAndBodyOrNeither() throws RequestValidationException {
        if (isBothOrNeitherNull(request.getMediaType().value(), request.getBody())){
            throw new RequestValidationException("A request must have both a body and a media type or neither body= "
                    + request.getBody() +
                    " media type= " +
                    request.getMediaType().value());
        }
    }

    private boolean isBothOrNeitherNull(String first, String second) {
        return first != null && second == null || first == null && second != null;
    }



}
