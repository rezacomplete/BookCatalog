package com.rezacomplete.service;

import com.rezacomplete.model.BookInfo;
import com.rezacomplete.model.BookRating;
import com.rezacomplete.model.CatalogInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookCatalogServiceImpl implements BookCatalogService {

    @Value("${bookRatingUrl}")
    private String bookRatingUrl;

    @Value("${bookInfoUrl}")
    private String bookInfoUrl;

    @Value("${addBookInfoUrl}")
    private String addBookInfoUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<CatalogInfo> retrieveBookCatalog(String userId) {
        List<CatalogInfo> catalogInfoList = new ArrayList<>();

        //ParameterizedTypeReference<T>: The purpose of this class is to enable capturing and passing a generic Type.
        //In order to capture the generic type and retain it at runtime, you need to create a subclass as follows:
        //ParameterizedTypeReference<List<String>> typeRef = new ParameterizedTypeReference<List<String>>() {}

        ResponseEntity<List<BookRating>> response = restTemplate.exchange(bookRatingUrl + userId, HttpMethod.GET,
                        null, new ParameterizedTypeReference<List<BookRating>>() {});

        List<BookRating> bookRatings = response.getBody();

        if (bookRatings != null) {
            for (BookRating bookRating : bookRatings) {
                CatalogInfo catalogInfo = new CatalogInfo();
                catalogInfo.setBookId(bookRating.getBookId());
                catalogInfo.setRating(bookRating.getRating());

                BookInfo bookInfo = restTemplate.getForObject(bookInfoUrl + bookRating.getBookId(), BookInfo.class);
                catalogInfo.setName(bookInfo != null ? bookInfo.getName() : "");

                catalogInfoList.add(catalogInfo);
            }
        }

        return catalogInfoList;
    }

    @Override
    public void init() {
        BookInfoRequest request = new BookInfoRequest();
        request.setId("1");
        request.setName("Reza Adventures");

        restTemplate.postForObject(addBookInfoUrl, request, String.class);
    }
}
