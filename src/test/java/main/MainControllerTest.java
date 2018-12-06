package main;

import main.controller.MainController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("admin")
@ActiveProfiles("test")
@TestPropertySource("classpath:application-test.yml")
@Sql(value = {"/create-user-before.sql", "/create-messages-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-messages-after.sql", "/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class MainControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    MainController controller;

    @Test
    public void mainPageTest() throws Exception{
        this.mockMvc.perform(get("/main"))
                .andDo(print())
                .andExpect(authenticated())
        .andExpect(xpath("//div[@id=\"navbarSupportedContent\"]/div/a").string("admin"));
    }

    @Test
    public void messageListTest() throws Exception{
        this.mockMvc.perform(get("/main"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='message-list']/div").nodeCount(3));
    }

    @Test
    public void filterMessageTest() throws Exception{
        this.mockMvc.perform(get("/main").param("filter", "testmessage1"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='message-list']/div").nodeCount(1))
                .andExpect(xpath("//div[@id='message-list']/div[@data-id=1]").exists());
    }

    @Test
    public void addMessageTest() throws Exception{
        MockHttpServletRequestBuilder multipart = multipart("/main")
                .file("file", "123456".getBytes())
                .param("text", "test text")
                .param("tag", "testtag")
                .with(csrf());
        this.mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='message-list']/div").nodeCount(4))
                .andExpect(xpath("//div[@id='message-list']/div[@data-id=10]").exists());
//                .andExpect(xpath("//div[@id='message-list']/div[@data-id=10]/div/p[1]").string("test text"))
//                .andExpect(xpath("//div[@id='message-list']/div[@data-id=10]/div/p[2]").string("#testtag"));
    }
}
