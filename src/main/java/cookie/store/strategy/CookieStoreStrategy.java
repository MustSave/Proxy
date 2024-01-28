package cookie.store.strategy;

import io.netty.handler.codec.http.cookie.Cookie;

public interface CookieStoreStrategy {
	boolean match(Cookie cookie);
	void handle(Cookie cookie);
}
