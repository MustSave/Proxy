package interceptor.response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.github.monkeywie.proxyee.intercept.HttpProxyInterceptPipeline;
import com.github.monkeywie.proxyee.intercept.common.FullResponseIntercept;

import interceptor.HttpUtil;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CloudFlareInterceptor extends FullResponseIntercept {
	@Override
	public boolean match(HttpRequest httpRequest, HttpResponse httpResponse, HttpProxyInterceptPipeline pipeline) {
		return HttpUtil.isHtml(httpResponse);
	}

	@Override
	public void handleResponse(HttpRequest httpRequest, FullHttpResponse httpResponse,
		HttpProxyInterceptPipeline pipeline) {
		if (!httpResponse.content().isReadable()) return;
		String response = httpResponse.content().toString(CharsetUtil.UTF_8);

		Document document = Jsoup.parse(response);
		boolean doBypass = document.select("script").stream()
			.anyMatch(element -> element.html().contains("cf_chl_opt"));

		if (doBypass) {
			log.error("CloudFlare Bot Detection detected. Bypass logic will be add soon...");
		}
	}
}
