package com.afromito.biblioteca.api.service;


import com.afromito.biblioteca.api.model.entities.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BookServiceTest {

  BookService service;



  @Test
  @DisplayName("Deve salvar um livro")
  public void saveBookTest() {
    Book book = Book.builder().isbn("123").author("fulano").title("java").build();

    Book savedBook = service.save(book);

    assertThat(savedBook.getId()).isNotNull();
    assertThat(savedBook.getIsbn()).isEqualTo("123");
    assertThat(savedBook.getTitle()).isEqualTo("java");
    assertThat(savedBook.getAuthor()).isEqualTo("fulano");
  }
}
