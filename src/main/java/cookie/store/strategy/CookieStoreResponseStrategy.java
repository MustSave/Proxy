package cookie.store.strategy;

import cookie.service.CookieService;
import io.netty.handler.codec.http.cookie.Cookie;

public class CookieStoreResponseStrategy extends CookieStoreStrategyImpl {
	public CookieStoreResponseStrategy(CookieService cookieService, String domainRegex, String cookieNameRegex) {
		super(cookieService, domainRegex, cookieNameRegex);
	}

	public CookieStoreResponseStrategy(CookieService cookieService) {
		super(cookieService);
	}

	@Override
	public void handle(Cookie cookie) {
		cookieService.saveCookie(cookie);
	}
}
