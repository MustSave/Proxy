package interceptor.response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.github.monkeywie.proxyee.intercept.HttpProxyInterceptPipeline;
import com.github.monkeywie.proxyee.intercept.common.FullResponseIntercept;
import com.github.monkeywie.proxyee.util.HttpUtil;

import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.util.CharsetUtil;

public class CaptchaInterceptor extends FullResponseIntercept {
	@Override
	public boolean match(HttpRequest httpRequest, HttpResponse httpResponse, HttpProxyInterceptPipeline pipeline) {
		return HttpUtil.checkHeader(httpResponse.headers(), HttpHeaderNames.CONTENT_TYPE, "^text/html.*$");
	}

	@Override
	public void handleResponse(HttpRequest httpRequest, FullHttpResponse httpResponse,
		HttpProxyInterceptPipeline pipeline) {
		if (!httpResponse.content().isReadable()) return;
		String response = httpResponse.content().toString(CharsetUtil.UTF_8);

		Document document = Jsoup.parse(response);
		boolean doBypass = document.select("div.g-recaptcha").size() > 0;

		if (doBypass) {
			System.out.println("캡차 탐지");
		}
	}
}
