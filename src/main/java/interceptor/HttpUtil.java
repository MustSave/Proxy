package interceptor;

import static io.netty.handler.codec.http.HttpHeaderValues.*;

import java.io.InputStream;
import java.util.Map;

import interceptor.response.decompressor.CompressedType;
import interceptor.response.decompressor.DecompressorFactory;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.util.internal.StringUtil;

public class HttpUtil {
	private static final HttpHeaders defaultHeaders = new DefaultHttpHeaders();
	private HttpUtil() {}
	public static InputStream decompressResponseBody(FullHttpResponse httpResponse, String contentEncoding) {
		if (StringUtil.isNullOrEmpty(contentEncoding)) return null;

		if (GZIP.contentEqualsIgnoreCase(contentEncoding) || X_GZIP.contentEqualsIgnoreCase(contentEncoding)) {
			return DecompressorFactory.getDecompressor(CompressedType.GZIP).toDecompressedInputStream(httpResponse.content());
		}

		if (BR.contentEqualsIgnoreCase(contentEncoding)) {
			return DecompressorFactory.getDecompressor(CompressedType.BR).toDecompressedInputStream(httpResponse.content());
		}

		if (DEFLATE.contentEqualsIgnoreCase(contentEncoding) || X_DEFLATE.contentEqualsIgnoreCase(contentEncoding)) {
			return DecompressorFactory.getDecompressor(CompressedType.DEFLATE).toDecompressedInputStream(httpResponse.content());
		}

		return null;
	}

	public static boolean shouldUseDefaultHeader(HttpRequest httpRequest) {
		String ua = httpRequest.headers().get(HttpHeaderNames.USER_AGENT);
		if (StringUtil.isNullOrEmpty(ua)) return true;

		ua = ua.toLowerCase();
		if (ua.startsWith("mozilla")) return false;
		return true;
	}

	public static void useDefaultHeader(HttpRequest httpRequest) {
		var header = httpRequest.headers();
		for (Map.Entry<String, String> defaultHeader : defaultHeaders) {
			header.set(defaultHeader.getKey(), defaultHeader.getValue());
		}
	}
}
