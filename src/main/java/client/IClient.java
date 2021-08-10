package client;

import request.IRequest;
import request.decorators.validation.ValidateRequestDecorator;

public interface IClient {
    IClient newCall(IRequest request);
}
