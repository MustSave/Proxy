package interceptor.response;

import java.io.IOException;
import java.io.InputStream;

import com.github.monkeywie.proxyee.intercept.HttpProxyInterceptPipeline;
import com.github.monkeywie.proxyee.intercept.common.FullResponseIntercept;

import interceptor.HttpUtil;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.util.internal.StringUtil;

public class HttpContentDecompressorAdaptor extends FullResponseIntercept {
	@Override
	public boolean match(HttpRequest httpRequest, HttpResponse httpResponse, HttpProxyInterceptPipeline pipeline) {
		String encoding = httpResponse.headers().get(HttpHeaderNames.CONTENT_ENCODING);
		return com.github.monkeywie.proxyee.util.HttpUtil.checkHeader(httpResponse.headers(), HttpHeaderNames.CONTENT_TYPE, "^text/html.*$")
			&& !StringUtil.isNullOrEmpty(encoding);
	}

	@Override
	public void handleResponse(HttpRequest httpRequest, FullHttpResponse httpResponse,
		HttpProxyInterceptPipeline pipeline) {
		String contentEncoding = httpResponse.headers().get(HttpHeaderNames.CONTENT_ENCODING);
		if (StringUtil.isNullOrEmpty(contentEncoding)) return;

		try(InputStream inputStream = HttpUtil.decompressResponseBody(httpResponse, contentEncoding)) {
			if (inputStream != null) {
				httpResponse.content().clear().writeBytes(inputStream, inputStream.available());
				httpResponse.headers().remove(HttpHeaderNames.CONTENT_ENCODING);
			}
		} catch (IOException e) {
		}
	}
}
