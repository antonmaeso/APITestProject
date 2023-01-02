package interceptor;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.log4j.Logger;

import java.io.IOException;

public class InterceptorModifyRequest implements Interceptor{
    private static final Logger log = Logger.getLogger(InterceptorModifyRequest.class);

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        log.info(request.toString());
        return chain.proceed(request);    }
}
