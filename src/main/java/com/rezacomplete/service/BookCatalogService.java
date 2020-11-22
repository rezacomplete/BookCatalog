package com.rezacomplete.service;

import com.rezacomplete.model.CatalogInfo;

import java.util.List;

public interface BookCatalogService {


    List<CatalogInfo> retrieveBookCatalog(String userId);

    void init();
}
