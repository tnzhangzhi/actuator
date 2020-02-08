代码：
测试01
@SpringBootTest
@AutoConfigureMockMvc
public class TestingWebApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnDefaultMessage() throws Exception{
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
        .andExpect(content().string(containsString("hello world")));
    }
}

测试02
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) 随机端口
public class HttpRequestTest {

    @LocalServerPort 注入随机的端口
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void greetingShouldReturnDefaultMessage(){
        assertThat(this.restTemplate.getForObject("http://localhost:"+port+"/",String.class)).contains("hello world");
    }
}

备注：
@SpringBootTest注解告诉springboot 寻找注解了@SpringBootApplication的main类并启动application context
@AutoConfigureMockMvc 不启动server
Another useful approach is to not start the server at all but to test only the layer below that, 
where Spring handles the incoming HTTP request and hands it off to your controller. 
That way, almost of the full stack is used, and your code will be called in exactly the same way as if it were processing a real HTTP request but without the cost of starting the server. 
To do that, use Spring’s MockMvc and ask for that to be injected for you by using the @AutoConfigureMockMvc


@WebMvcTest
public class WebLayerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnDefaultMessage() throws Exception{
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("hello world")));
    }
}

@WebMvcTest只启动了web层，也就是没有启动整个application context，
还可以只启动某个web层类如@WebMvcTest(ChartRoomController.class)
如果ChartRoomController里面需要注入其它类，则启动失败
可以通过@MockBean注入如下：
@MockBean
private XXXService xxxService;