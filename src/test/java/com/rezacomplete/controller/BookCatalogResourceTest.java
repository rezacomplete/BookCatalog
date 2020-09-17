package com.rezacomplete.controller;

import com.rezacomplete.BookCatalogApplication;
import com.rezacomplete.model.CatalogInfo;
import com.rezacomplete.service.BookCatalogService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookCatalogApplication.class)
@WebAppConfiguration
public class BookCatalogResourceTest {

    private MockMvc mockMvc;

    @MockBean
    private BookCatalogService bookCatalogService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void retrieveBookCatalog() throws Exception {
        List<CatalogInfo> response = new ArrayList<>();
        CatalogInfo catalogInfo = new CatalogInfo();
        catalogInfo.setName("book1");
        catalogInfo.setRating(1.5);
        catalogInfo.setBookId("book1");

        response.add(catalogInfo);

        when(bookCatalogService.retrieveBookCatalog("user1")).thenReturn(response);

        mockMvc.perform(get("/catalog/user1"))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"bookId\":\"book1\",\"name\":\"book1\",\"rating\":1.5}]"))
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    public void retrieveBookCatalogWhenNoResponse() throws Exception {
        List<CatalogInfo> response = new ArrayList<>();

        when(bookCatalogService.retrieveBookCatalog("user1")).thenReturn(response);

        mockMvc.perform(get("/catalog/user1"))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"))
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }
}