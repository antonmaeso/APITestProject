package client;

import request.IRequest;
//feel like this should exist
public class Client implements IClient{
    @Override
    public IClient newCall(IRequest request) {
        return null;
    }
}
