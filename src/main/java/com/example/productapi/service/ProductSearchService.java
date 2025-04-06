package com.example.productapi.service;

import com.example.productapi.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductSearchService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void indexAllProducts() {
        SearchSession searchSession = entityManager.unwrap(SearchSession.class);
        searchSession.massIndexer(Product.class).start();
    }

    @Transactional(readOnly = true)
    public List<Product> search(String searchTerm) {
        SearchSession searchSession = entityManager.unwrap(SearchSession.class);

        return searchSession.search(Product.class)
                .where(f -> f.match()
                        .fields("title", "description")
                        .matching(searchTerm)
                        .fuzzy(2)) // Allow for 2 character differences
                .fetchAllHits();
    }

    @Transactional(readOnly = true)
    public List<Product> advancedSearch(String searchTerm, int limit, int offset) {
        SearchSession searchSession = entityManager.unwrap(SearchSession.class);

        return searchSession.search(Product.class)
                .where(f -> f.bool()
                        .must(f.match()
                                .fields("title", "description")
                                .matching(searchTerm)
                                .fuzzy(2)))
                .sort(f -> f.field("title_sort").then().field("description_sort"))
                .fetchHits(offset, limit);
    }
}