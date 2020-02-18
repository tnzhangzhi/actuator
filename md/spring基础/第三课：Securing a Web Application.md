第一步：添加几个视图和路径映射
@Configuration
public class MvcConfig implements WebMvcConfigurer {

	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/home").setViewName("home");
		registry.addViewController("/").setViewName("home");
		registry.addViewController("/hello").setViewName("hello");
		registry.addViewController("/login").setViewName("login");
	}

}

第二步：

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/","/home").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .permitAll();
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}

备注：
The WebSecurityConfig class is annotated with @EnableWebSecurity to enable Spring Security’s web security support 
and provide the Spring MVC integration. It also extends WebSecurityConfigurerAdapter 
and overrides a couple of its methods to set some specifics of the web security configuration.

The configure(HttpSecurity) method defines which URL paths should be secured 
and which should not. Specifically, the / and /home paths are configured to not require any authentication.
 All other paths must be authenticated.
 
The userDetailsService() method sets up an in-memory user store with a single user. 
That user is given a user name of user, a password of password, and a role of USER.