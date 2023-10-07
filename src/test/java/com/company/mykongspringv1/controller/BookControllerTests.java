package com.company.mykongspringv1.controller;
import com.company.mykongspringv1.model.Book;
import com.company.mykongspringv1.service.BookService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BookControllerTests {

    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void testGetAllBooks() throws Exception {
        List<Book> books = Arrays.asList(new Book(), new Book()); // Create sample books
        when(bookService.findAll()).thenReturn(books);

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));

        verify(bookService, times(1)).findAll();
        verifyNoMoreInteractions(bookService);
    }

    @Test
    public void testGetBookById() throws Exception {
        long bookId = 1L;
//        Book book = new Book(); // Create a sample book
        Book book = new Book(1L, "Book A",
                BigDecimal.valueOf(9.99),
                LocalDate.of(2023, 8, 31));
        when(bookService.findById(bookId)).thenReturn(Optional.of(book));

        mockMvc.perform(get("/books/{id}", bookId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(bookId));

        verify(bookService, times(1)).findById(bookId);
        verifyNoMoreInteractions(bookService);
    }

    @Test
    public void testCreateBook() throws Exception {
//        Book book = new Book(); // Create a sample book
        Book book = new Book(1L, "Book A",
                BigDecimal.valueOf(9.99),
                LocalDate.of(2023, 8, 31));
        when(bookService.save(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")) // You can provide a JSON representation of the book here
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());

        verify(bookService, times(1)).save(any(Book.class));
        verifyNoMoreInteractions(bookService);
    }

    @Test
    public void testUpdateBook() throws Exception {
        long bookId = 8L;
//        Book book = new Book(); // Create a sample book
        Book book = new Book(8L, "testBook",
                BigDecimal.valueOf(50),
                LocalDate.of(2020, 10, 10));
        when(bookService.save(any(Book.class))).thenReturn(book);

        mockMvc.perform(put("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")) // You can provide a JSON representation of the book here
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(bookId));

        verify(bookService, times(1)).save(any(Book.class));
        verifyNoMoreInteractions(bookService);
    }

    @Test
    public void testDeleteBook() throws Exception {
        long bookId = 1L;

        mockMvc.perform(delete("/books/{id}", bookId))
                .andExpect(status().isNoContent());

        verify(bookService, times(1)).deleteById(bookId);
        verifyNoMoreInteractions(bookService);
    }
}