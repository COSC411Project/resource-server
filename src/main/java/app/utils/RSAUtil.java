package app.utils;

import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

public class RSAUtil {

	public static PublicKey getPublicKey(String publicKeyPath) {
		try (FileInputStream inputStream = new FileInputStream(publicKeyPath)) {
			byte[] bytes = inputStream.readAllBytes();
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return keyFactory.generatePublic(keySpec);
		} catch (Exception ex) {
		}
		
		return null;
	}
	
}
