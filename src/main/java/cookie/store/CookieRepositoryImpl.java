package cookie.store;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import io.netty.handler.codec.http.cookie.Cookie;

public class CookieRepositoryImpl implements CookieRepository{
	private static CookieRepositoryImpl instance = new CookieRepositoryImpl();
	private final Map<String, Set<Cookie>> cookiesOfDomain = new HashMap<>();

	private CookieRepositoryImpl() { }

	public static CookieRepositoryImpl getInstance() {
		return instance;
	}

	@Override
	public Set<Cookie> getCookiesByDomain(String domain) {
		Pattern pattern = Pattern.compile(domain);
		return cookiesOfDomain.getOrDefault(domain, new TreeSet<>());
	}

	@Override
	public void saveCookie(Cookie cookie) {
		if (!cookiesOfDomain.containsKey(cookie.domain())) {
			cookiesOfDomain.put(cookie.domain(), new TreeSet<>());
		}
		cookiesOfDomain.get(cookie.domain()).add(cookie);
	}
}
