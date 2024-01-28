package cookie.store;

import java.util.Set;

import io.netty.handler.codec.http.cookie.Cookie;

public interface CookieRepository {
	Set<Cookie> getCookiesByDomain(String domain);
	void saveCookie(Cookie cookie);
}
