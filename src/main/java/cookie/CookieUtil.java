package cookie;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.cookie.ClientCookieEncoder;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;
import io.netty.handler.codec.http.cookie.ServerCookieEncoder;
import io.netty.util.internal.StringUtil;

public class CookieUtil {
	public static void setCookie(HttpHeaders headers, Collection<? extends Cookie> cookies) {
		for (Cookie cookie : cookies) {
			cookie.setPath("/");
			cookie.setMaxAge(Long.MAX_VALUE);
			headers.add(HttpHeaderNames.SET_COOKIE, ServerCookieEncoder.STRICT.encode(cookie));
		}
	}

	public static Set<Cookie> parseCookie(HttpRequest request) {
		String cookieHeader = request.headers().get(HttpHeaderNames.COOKIE);
		if (StringUtil.isNullOrEmpty(cookieHeader)) return new TreeSet<>();
		Set<Cookie> cookies = ServerCookieDecoder.STRICT.decode(cookieHeader);
		return cookies;
	}

	public static Set<Cookie> parseCookie(HttpResponse response) {
		String cookieHeader = response.headers().get(HttpHeaderNames.SET_COOKIE);
		if (StringUtil.isNullOrEmpty(cookieHeader)) return new TreeSet<>();
		Set<Cookie> cookies = ServerCookieDecoder.STRICT.decode(cookieHeader);
		return cookies;
	}

	public static String toRequestCookieString(Collection<? extends Cookie> cookies) {
		return ClientCookieEncoder.LAX.encode(cookies);
	}

	public static String getDomain(HttpRequest request) {
		return request.headers().get(HttpHeaderNames.HOST);
	}

	public static void overwriteCookie(HttpHeaders headers) {

	}
}
