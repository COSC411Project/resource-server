package app.security;

import java.io.IOException;
import java.security.interfaces.RSAPublicKey;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.interfaces.DecodedJWT;

import app.utils.JWTUtil;
import app.utils.RSAUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TokenVerificationFilter extends OncePerRequestFilter {
	
	@Value("${rsa.public-key.path}")
	private String publicKeyPath;

	@Override
	protected void doFilterInternal(HttpServletRequest request, 
									HttpServletResponse response, 
									FilterChain filterChain)
			throws ServletException, IOException {
		if (request.getRequestURI().contains("/jobs")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String header = request.getHeader("Authorization");
		if (header == null || !header.contains("Bearer")) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
		String[] components = header.split(" ");
		if (components.length != 2) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
		String fullPublicKeyPath = System.getProperty("user.home") + "/" + publicKeyPath;
		RSAPublicKey publicKey = (RSAPublicKey) RSAUtil.getPublicKey(fullPublicKeyPath);
		
		DecodedJWT decodedJWT = JWTUtil.decode(components[1], publicKey);
		if (decodedJWT == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
		int userId = decodedJWT.getClaim("user").asInt();
		List<SimpleGrantedAuthority> authorities = decodedJWT.getClaim("authorities")
														     .asList(String.class)
														     .stream()
														     .map(authority -> new SimpleGrantedAuthority(authority))
														     .toList();
		VerifiedUserToken authentication = new VerifiedUserToken(userId, authorities);
		
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(authentication);
		
		filterChain.doFilter(request, response);
	}

}
