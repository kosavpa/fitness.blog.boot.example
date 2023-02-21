package com.kosavpa.first.boot.example.config.security;


import com.kosavpa.first.boot.example.config.vk.VkOAuth2AccessTokenResponseHttpMessageConverter;
import com.kosavpa.first.boot.example.config.vk.VkOAuth2UserService;
import com.kosavpa.first.boot.example.dao.entity.users.UserEntity;
import com.kosavpa.first.boot.example.dao.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain webSecurity(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeRequests()
                    .antMatchers("/blog/*/delete", "/add", "/blog/*/redact/").hasRole("ADMIN")
                    .antMatchers("/registration").not().fullyAuthenticated()
                    .antMatchers("/blog/*").authenticated()
                    .antMatchers("/blog").permitAll()
                    .antMatchers("/**").permitAll()
                .and()
                    .cors().disable()
                    .csrf().disable()
                .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/")
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                        .deleteCookies("JSESSIONID")
                .and()
                .rememberMe()
                    .key("rm-secret")
                    .tokenValiditySeconds(25200)
                .and()
                    .oauth2Login()
                        .loginPage("/oauth2LoginPage")
                        .tokenEndpoint()
                        .accessTokenResponseClient(defaultAuthorizationCodeTokenResponseClientWithVkRestOperation())
                    .and()
                        .userInfoEndpoint()
                        .userService(defaultUserInfoWithVkRestOperation())
                    .and()
                .and()
                .build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private VkOAuth2UserService defaultUserInfoWithVkRestOperation() {
        return new VkOAuth2UserService();
    }

    private OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> defaultAuthorizationCodeTokenResponseClientWithVkRestOperation() {
        DefaultAuthorizationCodeTokenResponseClient tokenResponseClient = new DefaultAuthorizationCodeTokenResponseClient();
        OAuth2AccessTokenResponseHttpMessageConverter httpMessageConverter = new OAuth2AccessTokenResponseHttpMessageConverter();

        httpMessageConverter.setAccessTokenResponseConverter(new VkOAuth2AccessTokenResponseHttpMessageConverter());

        RestTemplate vkRestTemplate = new RestTemplate(Arrays.asList(
                new FormHttpMessageConverter(),
                httpMessageConverter
        ));

        vkRestTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());
        tokenResponseClient.setRestOperations(vkRestTemplate);

        return tokenResponseClient;
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(this.vkClientRegistration());
    }

    private ClientRegistration vkClientRegistration() {
        return ClientRegistration.withRegistrationId("vk")
                .clientId("51557406")
                .clientSecret("1HWwYOe91E4s1VYZHSQ2")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
                .scope("read-article")
                .authorizationUri("https://oauth.vk.com/authorize")
                .tokenUri("https://oauth.vk.com/access_token")
                .userInfoUri("https://api.vk.com/method/users.get?")
                .userNameAttributeName("id")
                .clientName("Vk")
                .build();
    }
}
