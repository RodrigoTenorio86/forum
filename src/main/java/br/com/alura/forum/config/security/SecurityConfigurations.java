package br.com.alura.forum.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {
	@Autowired
	private AutenticacaoService autenticacaoService;
	
	//Config. de Autenticacao
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
         
	}

	//Config. de Autorizacao
	@Override
	protected void configure(HttpSecurity http) throws Exception {
                  http.authorizeRequests()
                  	  .antMatchers(HttpMethod.GET,"/v1/topicos").permitAll()
                  	  .antMatchers(HttpMethod.GET,"/v1/topicos/*").permitAll()
                  	  .anyRequest().authenticated()
                  	  .and().formLogin();
	}
    //config. de recursos estaticos(js,css, imagens, etc..)
	@Override
	public void configure(WebSecurity web) throws Exception {

	}
	
	/**
	 * 
	 * 
	 * @param args
	
	public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder().encode("123"));
	}
	 */
}
