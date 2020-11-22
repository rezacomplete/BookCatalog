package com.rezacomplete.controller;


import com.rezacomplete.model.CatalogInfo;
import com.rezacomplete.service.BookCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @PostMapping(path="init")
    public @ResponseBody String init() {
        bookCatalogService.init();
        return "Initiated!";
    }
}
