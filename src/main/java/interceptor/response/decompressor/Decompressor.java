package interceptor.response.decompressor;

import java.io.InputStream;

import io.netty.buffer.ByteBuf;

public interface Decompressor {
	String decompress(ByteBuf buf);
	InputStream toDecompressedInputStream(ByteBuf buf);
}
