package com.example.jakarta_books.dto;

import com.example.jakarta_books.entity.Book;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record BookDto(UUID id, @NotBlank String title, @NotEmpty String author, @Positive int publicationYear, String genre) {
    public static BookDto map(Book book) {
        return new BookDto(book.getId(), book.getTitle(), book.getAuthor(), book.getPublicationYear(), book.getGenre());
    }

    public static Book map(BookDto bookDto) {
        Book book = new Book();
        book.setId(bookDto.id);
        book.setTitle(bookDto.title);
        book.setAuthor(bookDto.author);
        book.setPublicationYear(bookDto.publicationYear);
        book.setGenre(bookDto.genre);
        return book;
    }
}
