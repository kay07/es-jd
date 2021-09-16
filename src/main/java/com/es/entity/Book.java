package com.es.entity;

import lombok.Data;
import org.springframework.stereotype.Repository;

/**
 * @author kay
 * @date 2021/9/15
 */
@Data
@Repository
public class Book {
    private String title;
    private String price;
    private String image;
}
