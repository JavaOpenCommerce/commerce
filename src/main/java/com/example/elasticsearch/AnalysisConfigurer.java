package com.example.elasticsearch;

import org.hibernate.search.backend.elasticsearch.analysis.ElasticsearchAnalysisConfigurationContext;
import org.hibernate.search.backend.elasticsearch.analysis.ElasticsearchAnalysisConfigurer;

public class AnalysisConfigurer implements ElasticsearchAnalysisConfigurer {

    @Override
    public void configure(ElasticsearchAnalysisConfigurationContext context) {
        context.analyzer("item").custom()
                .tokenizer("standard")
                .tokenFilters("asciifolding", "lowercase");

        context.normalizer("sort").custom()
                .tokenFilters("asciifolding", "lowercase");
    }
}
