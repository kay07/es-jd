package com.es.controller;

import com.es.entity.Book;
import com.es.service.BookService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author kay
 * @date 2021/9/15
 */
@RestController
@RequestMapping(value = "/book")
public class BookController {
    @Resource
    private BookService bookService;
    @RequestMapping(value = "/out",method = RequestMethod.GET)
    public List<Book> list(@RequestParam int from,@RequestParam String keywords) throws IOException {
       return bookService.list(from,keywords);
    }
    @RequestMapping(value = "/in",method = RequestMethod.GET)
    public boolean intoEs(@RequestParam String keywords) throws Exception {
       return bookService.intoEs(keywords);
    }
}
