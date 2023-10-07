//package com.company.mykongspringv1.controller;
//
//import com.company.mykongspringv1.model.Book;
//import com.company.mykongspringv1.repository.BookRepository;
//import com.company.mykongspringv1.service.BookService;
//import io.restassured.RestAssured;
//import io.restassured.config.RestAssuredConfig;
//import io.restassured.http.ContentType;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//
//import static io.restassured.RestAssured.given;
//import static io.restassured.config.JsonConfig.jsonConfig;
//import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.BIG_DECIMAL;
//import static net.bytebuddy.matcher.ElementMatchers.is;
//import static org.hamcrest.Matchers.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@ActiveProfiles("test")
//class BookControllerTest {
//
//    @Autowired
//    BookService bookService;
//
//    @Autowired
//    BookRepository bookRepository;
//
//    @BeforeEach
//    void setUp() {
//        Book b1 = new Book("Book A",
//                BigDecimal.valueOf(9.99),
//                LocalDate.of(2023, 8, 31));
//        Book b2 = new Book("Book B",
//                BigDecimal.valueOf(19.99),
//                LocalDate.of(2023, 7, 31));
//        Book b3 = new Book("Book C",
//                BigDecimal.valueOf(29.99),
//                LocalDate.of(2023, 6, 10));
//        Book b4 = new Book("Book D",
//                BigDecimal.valueOf(39.99),
//                LocalDate.of(2023, 5, 5));
//
//        bookService.save(b1);
//        bookService.save(b2);
//        bookService.save(b3);
//        bookService.save(b4);
////        bookRepository.saveAll(List.of(b1, b2, b3, b4));
//    }
//
//    @AfterEach
//    public void tearDown() {
//        // Clean up data after each test
////        bookService.deleteAll();
//    }
//
//    @Test
//    public void testFindAll() {
//
//        given()
//                .contentType(ContentType.JSON)
//                .when()
//                .get("/books")
//                .then()
//                .statusCode(200)
//                .contentType(ContentType.JSON)
//                .body(".", hasSize(4));
//    }
//
//    @Test
//    public void testFindByTitle() {
//
//        String title = "Book C";
//
//        given()
//                .config(RestAssuredConfig.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL)))
//                .contentType(ContentType.JSON)
//                .pathParam("title", title)
//                .when()
//                .get("/books/find/title/{title}")
//                .then()
//                .statusCode(200)
//                .contentType(ContentType.JSON)
//                .body(
//                        ".", hasSize(1),
//                        "[0].title", equalTo("Book C"),
//                        "[0].price", is(new BigDecimal("29.99")),
//                        "[0].publishDate", equalTo("2023-06-10")
//                );
//    }
//
////    @Test
////    public void testFindByPublishedDateAfter() {
////
////        String date = "2023-07-01";
////
////        Response result = given()
////                .config(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL)))
////            .contentType(ContentType.JSON)
////                .pathParam("date", date)
////                .when()
////                .get("/books/find/date-after/{date")
////                .then()
////                .statusCode(200)
////                .contentType(ContentType.JSON)
////                .body(
////                        ".", hasSize(2),
////                        "title", hasItems("Book A", "Book B"),
////                        "price", hasItems(new BigDecimal("9.99"), new BigDecimal("19.99")),
////                        "publishDate", hasItems("2023-08-31", "2023-07-31")
////                )
////                .extract().response();
////
////        System.out.println(result.asString());
////    }
//
//    @Test
//    public void testDeleteById() {
//        Long id = 1L;
//        given()
//                .pathParam("id", id)
//                .when()
//                .delete("/books/{id}")
//                .then()
//                .statusCode(204);
//    }
//
//    @Test
//    public void testCreate() {
//
//        given()
//                .contentType(ContentType.JSON)
//                .body("{ \"title\": \"Book E\", \"price\": \"9.99\", \"publishDate\": \"2023-09-14\" }")
//                .when()
//                .post("/books")
//                .then()
//                .statusCode(201)
//                .contentType(ContentType.JSON);
//
//        given()
//                .config(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL)))
//                .contentType(ContentType.JSON)
//                .pathParam("title", "Book E")
//                .when()
//                .get("/books/find/title/{title}")
//                .then()
//                .statusCode(200)
//                .contentType(ContentType.JSON)
//                .body(
//                        ".", hasSize(1),
//                        "[0].title", equalTo("Book E"),
//                        "[0].price", is("9.99"),
//                        "[0].publishDate", equalTo("2023-09-14")
//                );
//    }
//
//    @Test
//    public void testUpdate() {
//
//        Book bookD = bookService.findByTitle("Book D").get(0);
//        System.out.println(bookD);
//
//        Long id = bookD.getId();
//
//        bookD.setTitle("Book E");
//        bookD.setPrice(new BigDecimal("199.99"));
//        bookD.setPublishDate(LocalDate.of(2024, 1, 31));
//
//        given()
//                .contentType(ContentType.JSON)
//                .body(bookD)
//                .when()
//                .put("/books")
//                .then()
//                .statusCode(200)
//                .contentType(ContentType.JSON);
//
//        Book updatedBook = bookService.findById(id).orElseThrow();
//        System.out.println(updatedBook);
//
//        assertEquals(id, updatedBook.getId());
//        assertEquals("Book E", updatedBook.getTitle());
//        assertEquals(new BigDecimal("199.99"), updatedBook.getPrice());
//        assertEquals(LocalDate.of(2024, 1, 31), updatedBook.getPublishDate());
//
//    }
//}