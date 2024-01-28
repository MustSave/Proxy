package cert;

import java.io.ByteArrayInputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.github.monkeywie.proxyee.server.HttpProxyCACertFactory;

public class CertFactory implements HttpProxyCACertFactory {
	private final String certificatePem = "-----BEGIN CERTIFICATE-----\n"
		+ "-----END CERTIFICATE-----";

	private final String privateKeyPem = "-----BEGIN RSA PRIVATE KEY-----\n"
		+ "-----END RSA PRIVATE KEY-----";

	public CertFactory() {
		Security.addProvider(new BouncyCastleProvider());
	}

	@Override
	public X509Certificate getCACert() throws Exception {
		CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
		byte[] certBytes = org.bouncycastle.util.encoders.Base64.decode(certificatePem
			.replace("-----BEGIN CERTIFICATE-----", "")
			.replace("-----END CERTIFICATE-----", "")
			.replaceAll("\\s", ""));
		return (X509Certificate) certFactory.generateCertificate(new ByteArrayInputStream(certBytes));
	}

	@Override
	public PrivateKey getCAPriKey() throws Exception {
		byte[] keyBytes = Base64.getDecoder().decode(
			privateKeyPem.replace("-----BEGIN RSA PRIVATE KEY-----", "")
				.replace("-----END RSA PRIVATE KEY-----", "")
				.replaceAll("\\s", ""));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
		return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(keyBytes));
	}
}
