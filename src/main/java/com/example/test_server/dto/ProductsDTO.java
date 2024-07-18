package com.example.test_server.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductsDTO {
  private Long pno;
  private String pname;
  private String pmodel;
  private String pmaker;
  private int price;
  private LocalDateTime regDate, modDate;
}
