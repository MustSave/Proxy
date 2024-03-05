package cookie.store;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import io.netty.handler.codec.http.cookie.Cookie;
import lombok.Getter;

public class CookieRepositoryImpl implements CookieRepository{
	@Getter
	private static CookieRepositoryImpl instance = new CookieRepositoryImpl();
	private final Map<String, Set<Cookie>> cookiesOfDomain = new HashMap<>();

	private CookieRepositoryImpl() { }

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
