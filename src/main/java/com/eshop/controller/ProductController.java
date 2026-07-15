package com.eshop.controller;

import com.eshop.model.Product;
import com.eshop.service.ProductService;

import java.io.IOException;
import java.util.List;

/**
 * 商品控制器。
 * C3: 调用了不存在的 productDAO 变量和方法 searchByName()。
 */
public class ProductController {
    private final ProductService productService = new ProductService();

    public Product getProduct(String name) {
        return productService.searchByName(name);
    }

    public List<Product> listProducts() {
        return productService.listAll();
    }

    /**
     * C3 BUG: 引用了不存在的 productDAO 变量和不存在的 searchByName 方法。
     * 正确应为: return productService.searchProducts(keyword);
     */
    public List<Product> searchProducts(String keyword) {
        return productService.searchProducts(keyword);
    }

    public void addProduct(Product product) {
        productService.addProduct(product);
    }

    public String uploadImage(Long productId, String filename, byte[] content)
            throws IOException {
        return productService.uploadImage(productId, filename, content);
    }

    public boolean updateStock(Long productId, int delta) {
        return productService.updateStock(productId, delta);
    }
}
