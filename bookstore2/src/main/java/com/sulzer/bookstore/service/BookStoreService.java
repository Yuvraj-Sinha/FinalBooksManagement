package com.sulzer.bookstore.service;

import java.util.List;

import com.sulzer.bookstore.constants.Category;
import com.sulzer.bookstore.domain.Book;
import com.sulzer.bookstore.service.dto.BookDto;
import com.sulzer.bookstore.service.dto.SellDto;

public interface BookStoreService {
	void addNewBook(BookDto bookDto);

	void addBook(Long id, int quantityToAdd);

	BookDto getBookById(Long id);

	List<BookDto> getAllBooks();

	int getNumberOfBooksById(Long id);

	void updateBook(Long id, BookDto bookDto);

	Book sellBook(Long id);

	List<Book> sellBooks(List<SellDto> sellDtos);

	List<BookDto> getBookByCategoryKeyWord(String keyword, Category category);

	int getNumberOfBooksSoldByCategoryAndKeyword(String keyword, Category category);

}
