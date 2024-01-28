package cookie.service;

import java.util.Set;

import cookie.store.CookieRepository;
import cookie.store.CookieRepositoryImpl;
import io.netty.handler.codec.http.cookie.Cookie;

public class CookieServiceImpl implements CookieService {
	private static CookieServiceImpl instance = new CookieServiceImpl();
	private final CookieRepository cookieRepository;

	private CookieServiceImpl() {
		this.cookieRepository = CookieRepositoryImpl.getInstance();
	}

	public static CookieServiceImpl getInstance() {
		return instance;
	}

	@Override
	public Set<Cookie> getCookiesByDomain(String domain) {
		return cookieRepository.getCookiesByDomain(domain);
	}

	@Override
	public void saveCookie(Cookie cookie) {
		System.out.println("saved cookie = " + cookie);
		cookieRepository.saveCookie(cookie);
	}
}
