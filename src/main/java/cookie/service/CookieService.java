package cookie.service;

import java.util.Set;

import io.netty.handler.codec.http.cookie.Cookie;

public interface CookieService {
	Set<Cookie> getCookiesByDomain(String domain);
	void saveCookie(Cookie cookie);
}
