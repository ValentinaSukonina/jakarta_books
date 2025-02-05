package com.example.jakarta_books.service;

import com.example.jakarta_books.dto.BookDto;
import com.example.jakarta_books.dto.Books;
import com.example.jakarta_books.entity.Book;
import com.example.jakarta_books.repository.BookRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@ApplicationScoped
public class BookService {
    BookRepository bookRepository;

    public BookService() {
    }

    @Inject
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Books allBooks() {
        return new Books(bookRepository.findAll()
                .stream().map(BookDto::map).toList());
    }

    public BookDto oneBook(UUID id) {
        Book book = bookRepository.findById(id);
        if (book == null) {
            throw new NotFoundException("Book with ID " + id + " not found");
        }
        return BookDto.map(book);
    }

    public Book addBook(BookDto bookDto) {
        return bookRepository.createNew(BookDto.map(bookDto));
    }

public Response deleteBook(UUID id) {
    Book book = bookRepository.findById(id);
    if (book == null) {
        throw new NotFoundException("Book with ID " + id + " not found");
    }
    bookRepository.deleteById(id);
    return Response.noContent().build();
}

    public BookDto updateBook(UUID id, BookDto bookDto) {
        Book existingBook = BookDto.map(oneBook(id));
        existingBook.setId(id);
        existingBook.setTitle(bookDto.title());
        existingBook.setAuthor(bookDto.author());
        existingBook.setPublicationYear(bookDto.publicationYear());
        existingBook.setGenre(bookDto.genre());
        return BookDto.map(bookRepository.updateBook(existingBook));
    }

}

