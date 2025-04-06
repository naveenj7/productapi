package com.example.productapi.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Indexed
@Schema(description = "Product model representing a product entity")
public class Product {

    @Id
    @Schema(description = "Unique product ID from external source", example = "101")
    private Long id;

    @KeywordField(normalizer = "sort", name = "title_sort")
    @FullTextField(analyzer = "autocomplete", searchAnalyzer = "english")
    @Schema(description = "Title of the product", example = "iPhone 13")
    private String title;

    @KeywordField(normalizer = "sort", name = "description_sort")
    @FullTextField(analyzer = "autocomplete", searchAnalyzer = "english")
    @Schema(description = "Short description of the product", example = "Apple iPhone 13 with A15 chip")
    private String description;

    @KeywordField
    @Schema(description = "Stock keeping unit", example = "APL-IPH13")
    private String sku;
}
