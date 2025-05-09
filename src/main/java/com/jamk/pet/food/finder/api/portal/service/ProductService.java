package com.jamk.pet.food.finder.api.portal.service;

import com.jamk.pet.food.finder.api.portal.exception.ProductNotFoundException;
import com.jamk.pet.food.finder.api.portal.mapper.ProductMapper;
import com.jamk.pet.food.finder.api.portal.model.Product;
import com.jamk.pet.food.finder.api.portal.model.ProductDto;
import com.jamk.pet.food.finder.api.portal.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final MongoTemplate mongoTemplate;
    private final ProductMapper mapper;

    public Page<ProductDto> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable).map(mapper::toProductDto);
    }

    public Page<ProductDto> getByPetTypeAndPetAgeAndPriceAndRatingAndCharacteristic(String petType, Integer petAge, Integer price, Integer rating, List<String> characteristics, Pageable pageable) {
        return productRepository.findByPetTypeAndPetAgeAndPriceLessThanEqualAndCharacteristics(petType, petAge, price, rating, characteristics, pageable).map(mapper::toProductDto);
    }

    public Page<ProductDto> getByPetTypeAndPetAgeAndPrice(String petType, Integer petAge, Integer price, Pageable pageable) {
        return productRepository.findByPetTypeAndPetAgeAndPriceLessThanEqual(petType, petAge, price, pageable).map(mapper::toProductDto);
    }

    public ProductDto getById(String id) {
        return productRepository.findById(id).map(mapper::toProductDto).orElseThrow(() -> new ProductNotFoundException("Product not found by id: " + id));
    }

    public Product getProductById(String id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found by id: " + id));
    }

    public List<ProductDto> getThreeBestRated() {
        return productRepository.findAllByOrderByRatingDesc().stream().limit(3).map(mapper::toProductDto).toList();
    }

    public Map<String, Integer> getNumberOfProductsByCharacteristic() {
        List<Map<String, Object>> countProductsByCharacteristic = productRepository.countProductsByCharacteristic();

        return countProductsByCharacteristic.stream().map(map -> Map.of(map.get("characteristic"), map.get("count"))).flatMap(objectObjectMap -> objectObjectMap.entrySet().stream()).collect(Collectors.toMap(entry -> (String) entry.getKey(), entry -> (Integer) entry.getValue()));
    }

    public Long getTotalProducts() {
        return productRepository.count();
    }

    public ProductDto save(Product product) {
        return mapper.toProductDto(productRepository.save(product));
    }

    public ProductDto update(String id, Product updatedProduct) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found by id: " + id));
        updatedProduct.setId(product.getId());
        return mapper.toProductDto(productRepository.save(updatedProduct));
    }

    public void deleteById(String id) {
        productRepository.deleteById(id);
    }

    public void setProductRating(String id, Double rating) {
        productRepository.findById(id).ifPresent(product -> {
            product.setRating(rating);
            save(product);
        });
    }

    public Page<ProductDto> searchProductsByParams(String id, String title, String brand, String origin, String descriptionShort, String petType, Integer petAge, Double price, Double rating, List<String> characteristics, String searchParam, Pageable pageable) {
        List<Criteria> criteriaList = new ArrayList<>();

        if (valueExists(id)) addIdCriteria(criteriaList, "id", id);
        if (valueExists(title)) addRegexCriteria(criteriaList, "title", title);
        if (valueExists(brand)) addRegexCriteria(criteriaList, "brand", brand);
        if (valueExists(origin)) addRegexCriteria(criteriaList, "origin", origin);
        if (valueExists(descriptionShort)) addRegexCriteria(criteriaList, "descriptionShort", descriptionShort);

        if (valueExists(petType)) addRegexCriteria(criteriaList, "petType", petType);
        if (valueExists(petAge)) addNumberCriteriaEquals(criteriaList, "petAge", petAge);
        if (valueExists(price)) addNumberCriteriaLessThanEquals(criteriaList, "price", price);
        if (valueExists(rating)) addNumberCriteriaLessThanEqualsOrNull(criteriaList, "rating", rating);
        if (valueExists(characteristics)) addArrayCriteria(criteriaList, "characteristics", characteristics);

        if (valueExists(searchParam)) {
            Criteria orCriteria = new Criteria().orOperator(Criteria.where("title").regex(Pattern.quote(searchParam), "i"), Criteria.where("descriptionShort").regex(Pattern.quote(searchParam), "i"), Criteria.where("brand").regex(Pattern.quote(searchParam), "i"), Criteria.where("origin").regex(Pattern.quote(searchParam), "i"));
            criteriaList.add(orCriteria);
        }

        Query query = new Query();
        if (!criteriaList.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
        }

        long total = mongoTemplate.count(query, Product.class);
        query.with(pageable);

        List<Product> products = mongoTemplate.find(query, Product.class);
        return new PageImpl<>(products, pageable, total).map(mapper::toProductDto);
    }

    private boolean valueExists(Object value) {
        if (value == null) {
            return false;
        }
        if (value instanceof String) {
            return !((String) value).isBlank();
        }
        if (value instanceof Collection<?>) {
            return !((Collection<?>) value).isEmpty();
        }
        return true;
    }

    private void addRegexCriteria(List<Criteria> list, String field, String value) {
        list.add(Criteria.where(field).regex(Pattern.quote(value), "i"));
    }

    private void addNumberCriteriaLessThanEquals(List<Criteria> list, String field, Double value) {
        list.add(Criteria.where(field).lte(value));
    }

    private void addNumberCriteriaLessThanEqualsOrNull(List<Criteria> list, String field, Double value) {
        Criteria criteria = new Criteria().orOperator
                (Criteria.where(field).lte(value), Criteria.where(field).is(null));
        list.add(criteria);
    }

    private void addNumberCriteriaLessThanEquals(List<Criteria> list, String field, Integer value) {
        list.add(Criteria.where(field).lt(value));
    }

    private void addNumberCriteriaGreaterThan(List<Criteria> list, String field, Integer value) {
        list.add(Criteria.where(field).gt(value));
    }

    private void addNumberCriteriaEquals(List<Criteria> list, String field, Integer value) {
        list.add(Criteria.where(field).is(value));
    }

    private void addArrayCriteria(List<Criteria> list, String field, List<String> values) {
        list.add(Criteria.where(field).all(values));
    }

    private void addIdCriteria(List<Criteria> list, String field, String value) {
        list.add(Criteria.where("_id").is(new ObjectId(value)));
    }
}
