import java.net.UnknownHostException;
import java.nio.channels.ClosedChannelException;

import com.github.monkeywie.proxyee.exception.HttpProxyExceptionHandle;
import com.github.monkeywie.proxyee.intercept.HttpProxyInterceptInitializer;
import com.github.monkeywie.proxyee.intercept.HttpProxyInterceptPipeline;
import com.github.monkeywie.proxyee.server.HttpProxyServer;
import com.github.monkeywie.proxyee.server.HttpProxyServerConfig;

import cert.CertFactory;
import interceptor.request.DefaultHeaderInterceptor;
import interceptor.request.RequestCookieInterceptor;
import interceptor.response.CaptchaInterceptor;
import interceptor.response.CloudFlareInterceptor;
import interceptor.response.ResponseCookieInterceptor;
import io.netty.channel.Channel;
import io.netty.handler.codec.DecoderException;

public class CrawlingProxy {
	public static void main(String[] args) {
		HttpProxyServerConfig config = new HttpProxyServerConfig();
		config.setHandleSsl(true);
		config.setMaxHeaderSize(1024 * 10);

		int port = 8080;
		try {
			port = Integer.parseInt(System.getenv("PORT"));
		} catch (NumberFormatException e) { }

		System.out.printf("Start Proxy Server using port %d", port);
		new HttpProxyServer()
			// .serverConfig(config).caCertFactory(new CertFactory())
			.httpProxyExceptionHandle(new HttpProxyExceptionHandle() {
				@Override
				public void beforeCatch(Channel clientChannel, Throwable cause) throws Exception {
					if (cause instanceof UnknownHostException
						|| cause instanceof DecoderException
						|| cause instanceof ClosedChannelException
					) {
						return;
					}
					throw new RuntimeException(cause);
				}
			})
			.proxyInterceptInitializer(new HttpProxyInterceptInitializer() {
				@Override
				public void init(HttpProxyInterceptPipeline pipeline) {
					// pipeline.addLast(new HttpContentDecompressorAdaptor());
					pipeline.addLast(new CloudFlareInterceptor());
					pipeline.addLast(new CaptchaInterceptor());

					pipeline.addFirst(new RequestCookieInterceptor());
					pipeline.addFirst(new DefaultHeaderInterceptor());
					pipeline.addLast(new ResponseCookieInterceptor());
				}
			}).startAsync("0.0.0.0", port);
	}
}
