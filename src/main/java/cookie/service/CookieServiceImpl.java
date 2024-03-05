package cookie.service;

import java.util.Set;

import cookie.store.CookieRepository;
import cookie.store.CookieRepositoryImpl;
import io.netty.handler.codec.http.cookie.Cookie;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CookieServiceImpl implements CookieService {
	@Getter
	private static CookieServiceImpl instance = new CookieServiceImpl();
	private final CookieRepository cookieRepository;

	private CookieServiceImpl() {
		this.cookieRepository = CookieRepositoryImpl.getInstance();
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
