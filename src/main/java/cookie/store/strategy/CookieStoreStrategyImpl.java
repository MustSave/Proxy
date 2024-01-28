package cookie.store.strategy;

import java.util.function.Predicate;
import java.util.regex.Pattern;

import cookie.service.CookieService;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.cookie.Cookie;

public abstract class CookieStoreStrategyImpl implements CookieStoreStrategy{
	protected final CookieService cookieService;
	private Predicate<String> domainMatcher;
	private Predicate<String> cookieMatcher;

	public CookieStoreStrategyImpl(CookieService cookieService, String domainRegex, String cookieNameRegex) {
		this.cookieService = cookieService;
		var domainPattern = Pattern.compile(domainRegex);
		var cookieNamePattern = Pattern.compile(cookieNameRegex);
		domainMatcher = domainPattern.asPredicate();
		cookieMatcher = cookieNamePattern.asPredicate();
	}

	public CookieStoreStrategyImpl(CookieService cookieService) {
		this(cookieService,
			".*",
			"^(cf_clearance|(?i)(.{0,10}sess))");
	}

	@Override
	public boolean match(Cookie cookie) {
		return cookieMatcher.test(cookie.name());
	}

	public boolean matchDomain(String domain) {
		return domain != null ? domainMatcher.test(domain) : false;
	}

	public boolean matchDomain(HttpRequest request) {
		return matchDomain(getDomain(request));
	}

	private String getDomain(HttpRequest request) {
		return request.headers().get(HttpHeaderNames.HOST);
	}
}
