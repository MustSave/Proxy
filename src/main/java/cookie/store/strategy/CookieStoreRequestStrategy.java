package cookie.store.strategy;

import java.util.Set;

import cookie.service.CookieService;
import io.netty.handler.codec.http.cookie.Cookie;

public class CookieStoreRequestStrategy extends CookieStoreStrategyImpl {
	public CookieStoreRequestStrategy(CookieService cookieService, String domainRegex, String cookieNameRegex) {
		super(cookieService, domainRegex, cookieNameRegex);
	}

	public CookieStoreRequestStrategy(CookieService cookieService) {
		super(cookieService);
	}

	@Override
	public void handle(Cookie cookie) {
		Set<Cookie> cookies = cookieService.getCookiesByDomain(cookie.domain());
		if (cookies.isEmpty() || cookies.stream().noneMatch(c -> cookie.name().equals(c.name()))) {
			cookieService.saveCookie(cookie);
		}
	}
}
