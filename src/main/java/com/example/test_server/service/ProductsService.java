package com.example.test_server.service;

import com.example.test_server.dto.PageRequestDTO;
import com.example.test_server.dto.PageResultDTO;
import com.example.test_server.dto.ProductsDTO;
import com.example.test_server.entity.Products;

public interface ProductsService {
  default Products dtoToEntity(ProductsDTO dto) {
    Products products= Products.builder()
        .pno(dto.getPno())
        .pname(dto.getPname())
        .pmodel(dto.getPmodel())
        .pmaker(dto.getPmaker())
        .price(dto.getPrice())
        .build();
    return products;

  }
  default ProductsDTO entityToDto(Products products) {
    ProductsDTO productsDTO = ProductsDTO.builder()
        .pno(products.getPno())
        .pname(products.getPname())
        .pmodel(products.getPmodel())
        .pmaker(products.getPmaker())
        .price(products.getPrice())
        .regDate(products.getRegDate())
        .modDate(products.getModDate())
        .build();
    return productsDTO;
  }
  Long register(ProductsDTO dto);
  PageResultDTO<ProductsDTO, Products> getList(PageRequestDTO pageRequestDTO);
  ProductsDTO read(Long pno);
  void modify(ProductsDTO productsDTO);
  void remove(ProductsDTO productsDTO);

}
