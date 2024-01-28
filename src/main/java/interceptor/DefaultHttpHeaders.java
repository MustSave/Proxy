package interceptor;

public class DefaultHttpHeaders extends io.netty.handler.codec.http.DefaultHttpHeaders {
	public DefaultHttpHeaders() {
		this
		.set("cache-control", "no-cache")
		.set("pragma", "no-cache")
		.set("sec-ch-ua", "\"Chromium\";v=\"119\", \"Not?A_Brand\";v=\"24\"")
		.set("sec-ch-ua-arch", "\"arm\"")
		.set("sec-ch-ua-bitness", "\"64\"")
		.set("sec-ch-ua-full-version", "\"119.0.6045.105\"")
		.set("sec-ch-ua-full-version-list", "\"Chromium\";v=\"119.0.6045.105\", \"Not?A_Brand\";v=\"24.0.0.0\"")
		.set("sec-ch-ua-mobile", "?0")
		.set("sec-ch-ua-model", "\"\"")
		.set("sec-ch-ua-platform", "\"macOS\"")
		.set("sec-ch-ua-platform-version", "\"14.2.1\"")
		.set("sec-fetch-dest", "document")
		.set("sec-fetch-mode", "navigate")
		.set("sec-fetch-site", "none")
		.set("sec-fetch-user", "?1")
		.set("upgrade-insecure-requests", "1")
		.set("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36");
	}
}
