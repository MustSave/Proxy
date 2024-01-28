package interceptor.response.decompressor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.DeflaterInputStream;

import io.netty.buffer.ByteBuf;

public class DeflateDecompressor implements Decompressor {
	@Override
	public String decompress(ByteBuf buf) {
		byte[] bytes = new byte[buf.readableBytes()];
		buf.readBytes(bytes);
		try (ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
			 InputStream inputStream = new DeflaterInputStream(bin);
			 ByteArrayOutputStream bout = new ByteArrayOutputStream()) {
			byte[] buffer = new byte[1024];
			int len;
			while ((len = inputStream.read(buffer)) > 0) {
				bout.write(buffer, 0, len);
			}
			return bout.toString(StandardCharsets.UTF_8);
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public InputStream toDecompressedInputStream(ByteBuf buf) {
		byte[] bytes = new byte[buf.readableBytes()];
		buf.readBytes(bytes);
		try (ByteArrayInputStream bin = new ByteArrayInputStream(bytes)) {
			InputStream inputStream = new DeflaterInputStream(bin);
			return inputStream;
		} catch (IOException e) {
			return null;
		}
	}
}
