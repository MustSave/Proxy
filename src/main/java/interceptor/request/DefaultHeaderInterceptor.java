package interceptor.request;

import com.github.monkeywie.proxyee.intercept.HttpProxyInterceptPipeline;
import com.github.monkeywie.proxyee.intercept.common.FullRequestIntercept;

import interceptor.HttpUtil;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequest;

public class DefaultHeaderInterceptor extends FullRequestIntercept {
	@Override
	public boolean match(HttpRequest httpRequest, HttpProxyInterceptPipeline pipeline) {
		return HttpUtil.shouldUseDefaultHeader(httpRequest);
	}

	@Override
	public void handleRequest(FullHttpRequest httpRequest, HttpProxyInterceptPipeline pipeline) {
		HttpUtil.useDefaultHeader(httpRequest);
	}
}
