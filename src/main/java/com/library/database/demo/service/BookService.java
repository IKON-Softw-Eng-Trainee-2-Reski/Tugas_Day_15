package com.library.database.demo.service;

import com.library.database.demo.entity.Book;
import com.library.database.demo.entity.ResponseMessage;
import com.library.database.demo.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class BookService {
  @Autowired(required = false)
  BookRepository BookRepository;
  public List<Book> getBooks() {
    return (List<Book>) BookRepository.findAll();
  }

  public Book getBookWithId(Integer countryId) {
    return BookRepository.findById(countryId).get();
  }

  public Book getBookWithName(String judul) {
    List<Book> bookList = (List<Book>) BookRepository.findAll();
    for (Book book : bookList) {
      if (judul.equals(book.getJudul())) {
        return book;
      }
    }
    return new Book(0,"NONE","NONE", "NONE","NONE", 0);
  }

  public Book addBook(Book addedBook) {
    long size = BookRepository.count();
    log.info("[user] REPOSITORY SIZE IS: " + size);
    Integer newId = (int) size + 1;
    log.info("[user] newId IS: " + newId);
    Book book = new Book(newId, addedBook.getJudul(), addedBook.getPengarang(), addedBook.getKategori(), addedBook.getPenerbit(), addedBook.getTahun());

    BookRepository.save(book);
    log.info("[user] newly added book in repository is: " + BookRepository.findById(newId).get());
    return BookRepository.findById(newId).get();
  }

  public Book updateBook(Book updatedBook) {
    BookRepository.save(updatedBook);
    return BookRepository.findById(updatedBook.getId()).get();
  }

  public ResponseMessage deleteBook(Integer bookId) {
    BookRepository.deleteById(bookId);
    return new ResponseMessage("Book deleted");
  }

}
