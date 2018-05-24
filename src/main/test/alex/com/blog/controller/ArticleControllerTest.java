package alex.com.blog.controller;

import alex.com.blog.configuration.AppContext;
import alex.com.blog.service.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.servlet.ServletException;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {AppContext.class})
@Transactional
public class ArticleControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ArticleController articleController;

    @Before
    public void setup() throws ServletException {
        mockMvc = MockMvcBuilders.standaloneSetup(articleController).build();
    }
    @Test
    public void getArticleTestNot12Null() throws Exception {
        mockMvc.perform(post("/getArticle/{uid}", nullValue())).andExpect(status().is(400));
    }
    @Test
    public void getArticleTestNot12Id() throws Exception {
        mockMvc.perform(post("/getArticle/{uid}", "8hbk")).andExpect(status().is(400));
    }
    @Test
    public void getArticleTestArticleNoExist() throws Exception {
        mockMvc.perform(post("/getArticle/{uid}", "8hbkwe35gdbv")).andExpect(status().is(404));
    }
    @Test
    public void getArticleTestExist() throws Exception {
        mockMvc.perform(post("/getArticle/{uid}", "819e48f4XHhp")).andExpect(status().is(200));
    }
    @Test
    public void getAllArticleCommentsTestOK() throws Exception {
        mockMvc.perform(post("/getAllComments/{articleUid}", "ab7381cbsfxo")).andExpect(status().is(200));
    }
    @Test
    public void getAllArticleCommentsTestBad() throws Exception {
        mockMvc.perform(post("/getAllComments/{articleUid}", "ab7")).andExpect(status().is(400));
    }
    @Test
    public void getAllArticleCommentsTestNull() throws Exception {
        mockMvc.perform(post("/getAllComments/{articleUid}", nullValue())).andExpect(status().is(400));
    }

    @Test
    public void getAllArticleCommentsTestNf() throws Exception {
        mockMvc.perform(post("/getAllComments/{articleUid}", "a8wiqpv9xsqR")).andExpect(status().is(404));
    }

}
