package com.ltfullstack.bookservice.query.projection;

import com.ltfullstack.bookservice.command.data.Book;
import com.ltfullstack.bookservice.command.data.BookRepository;
import com.ltfullstack.bookservice.query.queries.GetAllBooksQuery;
import com.ltfullstack.commonservice.model.BookResponseCommonModel;
import com.ltfullstack.commonservice.queries.GetBookDetailQuery;
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
    public List<BookResponseCommonModel> handle(GetAllBooksQuery query) {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(book -> {
                    BookResponseCommonModel responseModel = new BookResponseCommonModel();
                    BeanUtils.copyProperties(book, responseModel);
                    return responseModel;
                })
                .toList();
    }

    @QueryHandler
    public BookResponseCommonModel handle(GetBookDetailQuery query) throws Exception {
        BookResponseCommonModel responseModel = new BookResponseCommonModel();

        Book book = bookRepository.findById(query.getId()).orElseThrow(() -> new Exception("Book not found with id " + query.getId()));

        BeanUtils.copyProperties(book, responseModel);

        return responseModel;
    }
}
