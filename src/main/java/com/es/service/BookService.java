package com.es.service;

import com.es.entity.Book;

import java.io.IOException;
import java.util.List;

/**
 * @author kay
 * @date 2021/9/15
 */
public interface BookService {
    List<Book> list(int from,String keywords) throws IOException;
    boolean intoEs(String keywords) throws Exception;
}
