package com.afromito.biblioteca.api.api.resource;


import com.afromito.biblioteca.api.api.dto.BookDTO;
import com.afromito.biblioteca.api.model.entities.Book;
import com.afromito.biblioteca.api.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class BookControllerTest {

  static String BOOK_API = "/api/books";

  @Autowired
  MockMvc mvc;

  @MockBean
  BookService service;

  @Test
  @DisplayName("Deve criar um livro com sucesso")
  public void createBookTest() throws Exception {

    BookDTO dto = BookDTO.builder().author("Artur").title("Head Java").isbn("1").build();
    Book savedBook = Book.builder().id(10l).author("Artur").title("Head Java").isbn("1").build();
    BDDMockito.given(service.save(Mockito.any(Book.class))).willReturn(savedBook);

    String json = new ObjectMapper().writeValueAsString(dto);

    MockHttpServletRequestBuilder request = MockMvcRequestBuilders
        .post(BOOK_API)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(json);

    mvc
        .perform(request)
        .andExpect(status().isCreated())
        .andExpect(jsonPath("id").isNotEmpty())
        .andExpect(jsonPath("title").value(dto.getTitle()))
        .andExpect(jsonPath("author").value(dto.getAuthor()))
        .andExpect(jsonPath("isbn").value(dto.getIsbn()))
    ;


  }


   @Test
  @DisplayName("Deve lançar erro de validacao quando nao houver dados suficientes para criacao de livro")
  public void createInvalidBookTest(){

  }
}
