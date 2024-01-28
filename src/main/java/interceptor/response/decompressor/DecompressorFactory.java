package interceptor.response.decompressor;

public class DecompressorFactory {
	private DecompressorFactory() {}
	public static Decompressor getDecompressor(CompressedType compressedType) {
		if (CompressedType.GZIP.equals(compressedType)) {
			return new GzipDecompressor();
		} else if (CompressedType.BR.equals(compressedType)) {
			return new BrotliDecompressor();
		} else if (CompressedType.DEFLATE.equals(compressedType)) {
			return new DeflateDecompressor();
		}
		throw new EnumConstantNotPresentException(compressedType.getClass(), "지원하지 않은 압축 형식");
	}
}
