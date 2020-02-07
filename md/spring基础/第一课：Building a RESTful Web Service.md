构建RESTful Web 服务

代码
@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}
}

总结：
1.@RequestParam：绑定query参数 name 到 greeting()方法的name参数
2.返回的Greeting对象转换为JSON，因为spring的Http message convert支持，自动选择了MappingJackson2HttpMessageConverter做对象转JSON的工作
3.@SpringBootApplication注解相当于添加了以下注解：
@Configuration 将该类标记为Application context上下文的的bean定义源（Tags the class as a source of bean definitions for the application context）
@EnableAutoConfiguration
告诉Spring Boot根据类路径设置、其他bean和各种属性设置开始添加bean。例如，如果spring webmvc位于类路径上，则此注释将应用程序标记为web应用程序并激活关键行为，例如设置DispatcherServlet。
(Tells Spring Boot to start adding beans based on classpath settings, other beans, and various property settings. For example, if spring-webmvc is on the classpath, this annotation flags the application as a web application and activates key behaviors, such as setting up a DispatcherServlet.)
@ComponentScan
Tells Spring to look for other components, configurations, and services in the com/example package, letting it find the controllers

