package com.es.utils;

import com.es.entity.Book;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kay
 * @date 2021/9/15
 */
@Component
public class ParseHtml {

    public List<Book> parse(String keyword) throws Exception {
        String url="https://search.jd.com/Search?keyword="+keyword;
        Document parse = Jsoup.parse(new URL(url), 10000);
        Element j_goodsList = parse.getElementById("J_goodsList");
        Elements li = j_goodsList.getElementsByTag("li");
        List<Book> list=new ArrayList<Book>();
        for (Element element : li) {
            //这里可能会因为网页标签的不同而得不到数据，需根据当时的实际情况分析网页
            String image = element.getElementsByTag("img").eq(0).attr("data-lazy-img");
            String price = element.getElementsByClass("p-price").eq(0).text();
            String name = element.getElementsByClass("p-name").eq(0).text();
            Book book=new Book();
            book.setImage(image);
            book.setPrice(price);
            book.setTitle(name);
            list.add(book);
        }

        return list;
    }
}
