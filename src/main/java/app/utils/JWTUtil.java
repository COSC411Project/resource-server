package app.utils;

import java.security.interfaces.RSAPublicKey;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

public class JWTUtil {

	public static DecodedJWT decode(String token, RSAPublicKey publicKey) {
		Algorithm algorithm = Algorithm.RSA256(publicKey);
		JWTVerifier verifier = JWT.require(algorithm).build();
		
		try {
			return verifier.verify(token);
		} catch (Exception ex) {
			return null;
		}
	}
	
}
