package com.ltfullstack.bookservice.query.projection;

import com.ltfullstack.bookservice.command.data.Book;
import com.ltfullstack.bookservice.command.data.BookRepository;
import com.ltfullstack.bookservice.query.model.BookResponseModel;
import com.ltfullstack.bookservice.query.queries.GetAllBooksQuery;
import com.ltfullstack.bookservice.query.queries.GetBookDetailQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookProjection {

    @Autowired
    BookRepository bookRepository;

    @QueryHandler
    public List<BookResponseModel> handle(GetAllBooksQuery query) {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(book -> {
                    BookResponseModel responseModel = new BookResponseModel();
                    BeanUtils.copyProperties(book, responseModel);
                    return responseModel;
                })
                .toList();
    }

    @QueryHandler
    public BookResponseModel handle(GetBookDetailQuery query) throws Exception {
        BookResponseModel responseModel = new BookResponseModel();

        Book book = bookRepository.findById(query.getId()).orElseThrow(() -> new Exception("Book not found with id " + query.getId()));

        BeanUtils.copyProperties(book, responseModel);

        return responseModel;
    }
}
