package com.example.test_server.repository;


import com.example.test_server.entity.Products;
import com.example.test_server.entity.QProducts;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import java.util.Optional;


@SpringBootTest
class ProductsRepositoryTests {

  @Autowired
  private ProductsRepository productsRepository;

  @Test
  public void testRepository() {
    System.out.println(">>" + productsRepository.getClass().getName());
  }

  @Test
  public void insertDummies() {
    IntStream.rangeClosed(1, 100).forEach(new IntConsumer() {
      @Override
      public void accept(int i) {
        Products products = Products.builder()
            .pname("Name...." + i)
            .pmodel("Model...." + i)
            .pmaker("Maker...." + i)
            .price(i)
            .build();
        System.out.println(productsRepository.save(products));
      }
    });
  }

  @Test
  public void testUpdate() {
    Optional<Products> result = productsRepository.findById(100L);
    if (result.isPresent()) {
      Products products = result.get();
      products.changePname("Changed Name...");
      products.changePmodel("Changed Model...");
      products.changePmaker("Changed Maker...");
      productsRepository.save(products);
    }
  }
  @Test
  public void testQuery1() {
    Pageable pageable = PageRequest.of(0,10, Sort.by("pno").descending());

    QProducts qProducts = QProducts.products;

    String keyword = "1";

    BooleanBuilder builder = new BooleanBuilder();

    BooleanExpression expression = qProducts.pname.contains(keyword);

    builder.and(expression);

    Page<Products> result = productsRepository.findAll(builder, pageable);
    result.stream().forEach(new Consumer<Products>() {
      @Override
      public void accept(Products products) {
        System.out.println(products);
      }
    });
  }

  // 다중 항목 검색
  @Test
  public void testQuery2() {
    Pageable pageable = PageRequest.of(0, 10, Sort.by("pno").descending());
    QProducts qProducts = QProducts.products;
    String keyword = "1";
    BooleanBuilder builder = new BooleanBuilder();
    BooleanExpression exPname = qProducts.pname.contains(keyword);
    BooleanExpression exPmodel = qProducts.pmodel.contains(keyword);
    BooleanExpression exAll = exPname.or(exPmodel);
    builder.and(exAll);
    builder.and(qProducts.pno.gt(0L)); //형식적이지만 추가해서 조건을 온전하게 함.
    Page<Products> result = productsRepository.findAll(builder, pageable);
    result.stream().forEach(products -> System.out.println(products));
  }

}
