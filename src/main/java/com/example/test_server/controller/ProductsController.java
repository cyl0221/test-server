package com.example.test_server.controller;

import com.example.test_server.dto.PageRequestDTO;
import com.example.test_server.dto.ProductsDTO;
import com.example.test_server.service.ProductsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/products")
@Log4j2
@RequiredArgsConstructor
public class ProductsController {

  private final ProductsService productsService;

@GetMapping({"","/","/list"})
public String list(Model model, PageRequestDTO pageRequestDTO) {
  model.addAttribute("pageResultDTO", productsService.getList(pageRequestDTO));
  return "/products/list";
}

@GetMapping("/register")
public void registerGet() {
  log.info("register get.....");
}

@PostMapping("/register")
public String registerPost(ProductsDTO productsDTO, RedirectAttributes ra) {
  log.info("register post........");
  Long pno = productsService.register(productsDTO);
  ra.addFlashAttribute("msg", pno+ "번이 등록");
  return "redirect:/products/list";
}
@GetMapping({"/read", "/modify"})
public void read(Long pno, PageRequestDTO pageRequestDTO, Model model) {
  ProductsDTO productsDTO = productsService.read(pno);
  typeKeywordInit(pageRequestDTO);
  model.addAttribute("productsDTO", productsDTO);
}
@PostMapping("/modify")
public String modify(ProductsDTO productsDTO, PageRequestDTO pageRequestDTO,
                     RedirectAttributes ra) {

  productsService.modify(productsDTO);
  typeKeywordInit(pageRequestDTO);
  ra.addFlashAttribute("msg", productsDTO.getPno() + "번이 수정");
  ra.addAttribute("page", pageRequestDTO.getPage());
  ra.addAttribute("type", pageRequestDTO.getType());
  ra.addAttribute("keyword", pageRequestDTO.getKeyword());
  ra.addAttribute("pno", productsDTO.getPno());
  return "redirect:/products/read";
}
@PostMapping("/remove")
public String remove(ProductsDTO productsDTO, PageRequestDTO pageRequestDTO,
                     RedirectAttributes ra) {
  productsService.remove(productsDTO);

  if (productsService.getList(pageRequestDTO).getDtoList().size() == 0 &&
      pageRequestDTO.getPage() != 1) {
    pageRequestDTO.setPage(pageRequestDTO.getPage() - 1);
  }
  typeKeywordInit(pageRequestDTO);
  ra.addFlashAttribute("msg", productsDTO.getPno() + "번이 삭제");
  ra.addAttribute("page", pageRequestDTO.getPage());
  ra.addAttribute("type", pageRequestDTO.getType());
  ra.addAttribute("keyword", pageRequestDTO.getKeyword());
  return "redirect:/products/list";
}

private void typeKeywordInit(PageRequestDTO pageRequestDTO){
  if (pageRequestDTO.getType().equals("null")) pageRequestDTO.setType("");
  if (pageRequestDTO.getKeyword().equals("null")) pageRequestDTO.setKeyword("");
}
}


