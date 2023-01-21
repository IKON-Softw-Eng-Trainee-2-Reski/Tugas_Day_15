package com.library.database.demo.controller;

import com.library.database.demo.entity.Book;
import com.library.database.demo.entity.ResponseMessage;
import com.library.database.demo.exception.EmptyTableException;
import com.library.database.demo.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class LibraryController {
  @Autowired(required = false)
  BookService bookService;


  @Operation(summary = "Get all books")
  @GetMapping(path="v1/book")
  public ResponseEntity<List<Book>> getBooks() {
    log.info("[user] v1/book is called");
    try {
      ResponseEntity<List<Book>> response = new ResponseEntity<>(bookService.getBooks(), HttpStatus.OK);
      log.info("[user] response length is: "+response.getBody().size());

      if (response.getBody().size() < 1) {
        throw new EmptyTableException("Data doesn't exist in table!!!");
      } else {
        return response;
      }
    } catch (InvalidDataAccessResourceUsageException e) { //db exists, tbl doesn't exist
      log.info("[user] exception message is: "+e);
      return new ResponseEntity<>(HttpStatus.GONE);
    } catch (CannotCreateTransactionException e) { //during spring boot run, db is deleted
      log.info("[user] exception message is: "+e);
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    } catch (EmptyTableException e) { //tbl exists but is empty
      log.info("[user] exception message is: "+e);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) { //any other exceptions
      log.info("[user] exception message is: "+e);
      return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
    }
  }

  @GetMapping(path="/v1/book/{booksId}")
  public ResponseEntity<Book> getBookWithId(@PathVariable Integer booksId) {
    try {
      return new ResponseEntity<>(bookService.getBookWithId(booksId), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }

  @GetMapping(path="/v1/book/judul")
  public ResponseEntity<Book> getBookWithName(@RequestParam(value="judul") String judul) {
    try {
      return new ResponseEntity<>(bookService.getBookWithName(judul), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }


  @PostMapping(path="v1/book")
  public ResponseEntity<Book> addBook(@RequestBody Book addedBook) {
    try {
      return new ResponseEntity<>(bookService.addBook(addedBook), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }

  @Operation(summary = "Update existing book")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Updated the book",
          content = { @Content(mediaType = "application/json",
              schema = @Schema(implementation = Book.class)) })})
  @PutMapping(path="v1/book")
  public ResponseEntity<Book> updateBook(@RequestBody Book updatedBook) {
    try {
      return new ResponseEntity<>(bookService.updateBook(updatedBook), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }

  @Operation(summary = "Delete a book by its id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Deleted book",
          content = { @Content(mediaType = "application/json",
              schema = @Schema(implementation = Book.class)) }),
      @ApiResponse(responseCode = "400", description = "Invalid id supplied",
          content = @Content),
      @ApiResponse(responseCode = "404", description = "Book not found",
          content = @Content) })
  @DeleteMapping(path="v1/book/{idBook}")
  public ResponseEntity<ResponseMessage> deleteBook(@Parameter(description = "id of book to be deleted") @PathVariable Integer idBook) {
    try {
      return new ResponseEntity<>(bookService.deleteBook(idBook), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }
}
