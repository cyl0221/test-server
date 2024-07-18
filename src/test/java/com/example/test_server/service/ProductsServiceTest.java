package com.example.test_server.service;


import com.example.test_server.dto.PageRequestDTO;
import com.example.test_server.dto.PageResultDTO;
import com.example.test_server.dto.ProductsDTO;
import com.example.test_server.entity.Products;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductsServiceTest {

  @Autowired
  private ProductsService productsService;

  @Test
  void testRegister() {
    ProductsDTO dto = ProductsDTO.builder()
        .pname("new pname")
        .pmodel("new pmodel")
        .pmaker("maker1")
        .build();
    Long pno = productsService.register(dto);
    System.out.println(pno);
  }
  @Test
  public void testList() {
    PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
        .page(22).size(10).build();
    PageResultDTO<ProductsDTO, Products> resultDTO =
        productsService.getList(pageRequestDTO);

    System.out.println("PREV:"+resultDTO.isPrev());
    System.out.println("NEXT:"+resultDTO.isNext());
    System.out.println("TOTAL:"+resultDTO.getTotalPage());
    System.out.println("=======================================================");
    for (ProductsDTO productsDTO : resultDTO.getDtoList()) {
      System.out.println(productsDTO);
    }
    System.out.println("=======================================================");
    resultDTO.getPageList().forEach(i -> {
      if(i!=1) System.out.print(", ");
      System.out.print(i);
    });
    System.out.println();
  }
}
