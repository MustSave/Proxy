package interceptor.request;

import java.util.Set;

import com.github.monkeywie.proxyee.intercept.HttpProxyInterceptPipeline;
import com.github.monkeywie.proxyee.intercept.common.FullRequestIntercept;

import cookie.service.CookieService;
import cookie.service.CookieServiceImpl;
import cookie.CookieUtil;
import cookie.store.strategy.CookieStoreRequestStrategy;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.util.internal.StringUtil;

public class RequestCookieInterceptor extends FullRequestIntercept {
	private final CookieStoreRequestStrategy requestStrategy;
	private final CookieService cookieService;

	public RequestCookieInterceptor() {
		this.cookieService = CookieServiceImpl.getInstance();
		this.requestStrategy = new CookieStoreRequestStrategy(cookieService);
	}

	@Override
	public boolean match(HttpRequest httpRequest, HttpProxyInterceptPipeline pipeline) {
		return requestStrategy.matchDomain(httpRequest);
	}

	@Override
	public void handleRequest(FullHttpRequest httpRequest, HttpProxyInterceptPipeline pipeline) {
		Set<Cookie> cookies = CookieUtil.parseCookie(httpRequest);
		String domain = CookieUtil.getDomain(httpRequest);

		// Store cookie if match condition and not stored before.
		for (Cookie cookie : cookies) {
			if (requestStrategy.match(cookie)) {
				cookie.setDomain(domain);
				requestStrategy.handle(cookie);
			}
		}

		Set<Cookie> storedCookies = cookieService.getCookiesByDomain(domain);
		for (Cookie storedCookie : storedCookies) {
			cookies.removeIf(cookie -> cookie.name().equals(storedCookie.name()));
			cookies.add(storedCookie);
		}

		String requestCookie = CookieUtil.toRequestCookieString(cookies);
		if (!StringUtil.isNullOrEmpty(requestCookie))
			httpRequest.headers().set("Cookie", requestCookie);
	}
}
