package com.example.productapi.configuration;

import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurationContext;
import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomLuceneAnalyzerConfig implements LuceneAnalysisConfigurer {

    @Override
    public void configure(LuceneAnalysisConfigurationContext context) {
        context.analyzer("english").custom()
                .tokenizer("standard")
                .tokenFilter("lowercase")
                .tokenFilter("porterStem")
                .tokenFilter("asciiFolding");

        context.analyzer("autocomplete").custom()
                .tokenizer("standard")
                .tokenFilter("lowercase")
                .tokenFilter("asciiFolding")
                .tokenFilter("edgeNGram")
                .param("minGramSize", "3")
                .param("maxGramSize", "7");

        context.normalizer("sort").custom()
                .tokenFilter("lowercase")
                .tokenFilter("asciiFolding");
    }
}
