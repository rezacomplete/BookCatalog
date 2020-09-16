package com.rezacomplete.controller;


import com.rezacomplete.model.CatalogInfo;
import com.rezacomplete.service.BookCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookCatalogResource {

    @Autowired
    private BookCatalogService bookCatalogService;

    @RequestMapping(path = "/catalog/{userId}", method = RequestMethod.GET)
    public List<CatalogInfo> retrieveBookCatalog(@PathVariable String userId) {

        //todo some input validations here ...
        return bookCatalogService.retrieveBookCatalog(userId);
    }
}
