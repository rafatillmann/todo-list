package br.com.java.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.java.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

	@Autowired
	private IUserRepository userRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		var servletPath = request.getServletPath();

		if (servletPath.startsWith("/tasks/")){
			var auth = request.getHeader("Authorization");
			var authEncoded = auth.substring("Basic".length()).trim();
			byte[] authDecoded = Base64.getDecoder().decode(authEncoded);
			var authString = new String(authDecoded);
			String[] credentials = authString.split(":");
			var username = credentials[0];
			var password = credentials[0];
			var user = this.userRepository.findByUsername(username);

			if (user != null){
				//var passwordHash = BCrypt.withDefaults().hashToString(12, password.toCharArray());
				var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword().toCharArray());

				if(passwordVerify.verified){
					request.setAttribute("idUser", user.getId());
					filterChain.doFilter(request, response);
				}else{
					response.sendError(401);
				}

			}else {
				response.sendError(401);
			}
		} else {
			filterChain.doFilter(request, response);
		}
	}
}
