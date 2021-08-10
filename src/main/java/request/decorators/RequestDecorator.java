package request.decorators;

import request.IRequest;

public abstract class RequestDecorator implements IRequest {
    protected IRequest request;

    public RequestDecorator(IRequest request) {
        this.request = request;
    }
}
