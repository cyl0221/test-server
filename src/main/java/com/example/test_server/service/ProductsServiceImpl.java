package com.example.test_server.service;


import com.example.test_server.dto.PageRequestDTO;
import com.example.test_server.dto.PageResultDTO;
import com.example.test_server.dto.ProductsDTO;
import com.example.test_server.entity.Products;
import com.example.test_server.entity.QProducts;
import com.example.test_server.repository.ProductsRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductsServiceImpl implements ProductsService {
  private final ProductsRepository productsRepository;


  @Override
  public Long register(ProductsDTO dto) {
    Products products = dtoToEntity(dto);
    productsRepository.save(products);
    return products.getPno();
  }

  @Override
  public PageResultDTO<ProductsDTO, Products> getList(PageRequestDTO pageRequestDTO) {
    Pageable pageable = pageRequestDTO.getPageable(Sort.by("pno").descending());
    BooleanBuilder booleanBuilder = getSearch(pageRequestDTO);
    Page<Products> result = productsRepository.findAll(booleanBuilder, pageable);

    Function<Products, ProductsDTO> fn = new Function<Products, ProductsDTO>() {
      @Override
      public ProductsDTO apply(Products products) {
        return entityToDto(products);
      }
    };
    return new PageResultDTO<>(result, fn);
  }
  private BooleanBuilder getSearch(PageRequestDTO pageRequestDTO) {
    String type = pageRequestDTO.getType();
    String keyword = pageRequestDTO.getKeyword();
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    QProducts qProducts = QProducts.products;
    BooleanExpression expression = qProducts.pno.gt(0L); // 전체조건부여
    booleanBuilder.and(expression);

    BooleanBuilder conditionBuilder = new BooleanBuilder();
    if (type == null || type.trim().length() == 0) return booleanBuilder;
    if (type.contains("n")) conditionBuilder.or(qProducts.pname.contains(keyword));
    if (type.contains("m")) conditionBuilder.or(qProducts.pmodel.contains(keyword));
    if (type.contains("k")) conditionBuilder.or(qProducts.pmaker.contains(keyword));
    booleanBuilder.and(conditionBuilder);
    return booleanBuilder;
  }

  @Override
  public ProductsDTO read(Long pno) {
    Optional<Products> result = productsRepository.findById(pno);
    return result.isPresent() ? entityToDto(result.get()) : null;
  }

  @Override
  public void modify(ProductsDTO productsDTO) {
    Optional<Products> result = productsRepository.findById(productsDTO.getPno());
    if (result.isPresent()) {
      Products products = result.get();
      products.changePname(productsDTO.getPname());
      products.changePmodel(productsDTO.getPmodel());
      products.changePmaker(productsDTO.getPmaker());
      products.changePrice(productsDTO.getPrice());
      productsRepository.save(products);
    }
  }

  @Override
  public void remove(ProductsDTO productsDTO) {
    Optional<Products> result = productsRepository.findById(productsDTO.getPno());
    if (result.isPresent()) {
      productsRepository.deleteById(productsDTO.getPno());
    }
  }
}
