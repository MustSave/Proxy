package interceptor.response;

import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

import org.jsoup.internal.StringUtil;

import com.github.monkeywie.proxyee.intercept.HttpProxyInterceptPipeline;
import com.github.monkeywie.proxyee.intercept.common.FullResponseIntercept;

import interceptor.HttpUtil;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NetfunnelInterceptor extends FullResponseIntercept {
	private final Pattern uriPattern = Pattern.compile("netfunnel", Pattern.CASE_INSENSITIVE);

	@Override
	public boolean match(HttpRequest httpRequest, HttpResponse httpResponse,
		HttpProxyInterceptPipeline httpProxyInterceptPipeline) {
		String uri = httpRequest.uri();
		return HttpUtil.isJavaScript(httpResponse) && !StringUtil.isBlank(uri) && uriPattern.matcher(uri).find();
	}

	@Override
	public void handleResponse(HttpRequest httpRequest, FullHttpResponse httpResponse,
		HttpProxyInterceptPipeline pipeline) {
		if (!httpResponse.content().isReadable()) return;
		String response = httpResponse.content().toString(CharsetUtil.UTF_8);

		String bypass = response.replaceFirst("TS_BYPASS\\s*=\\sfalse;?", "TS_BYPASS = true;");
		httpResponse.content().clear().writeCharSequence(bypass, StandardCharsets.UTF_8);

		log.info("Nefunnel detected. Successfully overwrite bypass logic");
	}
}
