package com.rezacomplete.service;

import com.rezacomplete.model.BookInfo;
import com.rezacomplete.model.BookRating;
import com.rezacomplete.model.CatalogInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookCatalogServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private BookCatalogServiceImpl bookCatalogService;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void retrieveBookCatalog() {
        List<BookRating> bookRatings = new ArrayList<>();
        BookRating bookRating = new BookRating();
        bookRating.setBookId("1");
        bookRating.setRating(1.5);
        bookRatings.add(bookRating);

        BookInfo bookInfo = new BookInfo();
        bookInfo.setBookId("1");
        bookInfo.setName("book1");

        ResponseEntity<List<BookRating>> response = new ResponseEntity<>(bookRatings, HttpStatus.OK);

        when(restTemplate.exchange("nulluser1", HttpMethod.GET, null, new ParameterizedTypeReference<List<BookRating>>() {}))
                .thenReturn(response);
        when(restTemplate.getForObject("null1", BookInfo.class)).thenReturn(bookInfo);

        List<CatalogInfo> result = bookCatalogService.retrieveBookCatalog("user1");

        assertEquals("1", result.get(0).getBookId());
        assertEquals("book1", result.get(0).getName());
        assertEquals(1.5, result.get(0).getRating(), 0.05);
    }

    @Test
    public void retrieveBookCatalogWhenNoBook() {
        List<BookRating> bookRatings = new ArrayList<>();

        ResponseEntity<List<BookRating>> response = new ResponseEntity<>(bookRatings, HttpStatus.OK);

        when(restTemplate.exchange("nulluser1", HttpMethod.GET, null, new ParameterizedTypeReference<List<BookRating>>() {}))
                .thenReturn(response);

        List<CatalogInfo> result = bookCatalogService.retrieveBookCatalog("user1");

        assertTrue(result.isEmpty());
    }
}