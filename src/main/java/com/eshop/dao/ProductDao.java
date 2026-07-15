package com.eshop.dao;

import com.eshop.model.Product;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品数据访问对象。
 * 此文件无 bug — 作为正常文件的对比。
 */
public class ProductDao {

    public Product findByName(String name) {
        List<Product> products = DatabaseHelper.getProducts();
        for (Product p : products) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    public List<Product> findAll() {
        return new ArrayList<>(DatabaseHelper.getProducts());
    }

    public List<Product> searchByKeyword(String keyword) {
        List<Product> result = new ArrayList<>();
        for (Product p : DatabaseHelper.getProducts()) {
            if (p.getName().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(p);
            }
        }
        return result;
    }

    public void insert(Product product) {
        product.setId(DatabaseHelper.nextProductId());
        DatabaseHelper.getProducts().add(product);
    }
}
