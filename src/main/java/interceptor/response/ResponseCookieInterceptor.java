package interceptor.response;

import java.util.Set;

import com.github.monkeywie.proxyee.intercept.HttpProxyInterceptPipeline;
import com.github.monkeywie.proxyee.intercept.common.FullResponseIntercept;

import cookie.service.CookieServiceImpl;
import cookie.CookieUtil;
import cookie.store.strategy.CookieStoreResponseStrategy;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.cookie.Cookie;

public class ResponseCookieInterceptor extends FullResponseIntercept {
	private final CookieStoreResponseStrategy responseStrategy;
	private final CookieServiceImpl cookieService;

	public ResponseCookieInterceptor() {
		this.cookieService = CookieServiceImpl.getInstance();
		this.responseStrategy = new CookieStoreResponseStrategy(cookieService);
	}

	@Override
	public boolean match(HttpRequest httpRequest, HttpResponse httpResponse, HttpProxyInterceptPipeline pipeline) {
		return responseStrategy.matchDomain(httpRequest);
	}

	@Override
	public void handleResponse(HttpRequest httpRequest, FullHttpResponse httpResponse,
		HttpProxyInterceptPipeline pipeline) {
		Set<Cookie> cookies = CookieUtil.parseCookie(httpResponse);
		String domain = CookieUtil.getDomain(httpRequest);

		// Store cookie if match condition
		for (Cookie cookie : cookies) {
			if (responseStrategy.match(cookie)) {
				cookie.setDomain(domain);
				responseStrategy.handle(cookie);
			}
		}

		Set<Cookie> storedCookies = cookieService.getCookiesByDomain(domain);
		CookieUtil.setCookie(httpResponse.headers(), storedCookies);

	}

}
