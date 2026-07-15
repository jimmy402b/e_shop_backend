package com.eshop.service;

import com.eshop.model.Product;
import com.eshop.dao.ProductDao;
import com.eshop.util.CacheManager;
import com.eshop.util.FileUploadUtil;

import java.io.IOException;
import java.util.List;

/**
 * 商品业务逻辑。
 * BUGS: 库存扣减竞态条件 + 路径遍历调用 + 缓存非线程安全
 */
public class ProductService {
    private final ProductDao productDao = new ProductDao();
    private final CacheManager cache = new CacheManager();

    public Product searchByName(String name) {
        return productDao.findByName(name);
    }

    public List<Product> searchProducts(String keyword) {
        String cacheKey = "search:" + keyword;
        Object cached = cache.get(cacheKey);
        if (cached != null) {
            return (List<Product>) cached;
        }
        List<Product> result = productDao.searchByKeyword(keyword);
        cache.put(cacheKey, result);
        return result;
    }

    public List<Product> listAll() {
        return productDao.findAll();
    }

    public void addProduct(Product product) {
        productDao.insert(product);
    }

    /**
     * BUG: synchronized 在方法签名上，但内部操作不是原子的。
     * 两个线程可能同时通过 stock > 0 检查然后都扣减。
     */
    public synchronized boolean updateStock(Long productId, int delta) {
        Product product = productDao.findAll().stream()
            .filter(p -> p.getId().equals(productId))
            .findFirst()
            .orElse(null);

        if (product == null) return false;

        int newStock = product.getStock() + delta;
        if (newStock < 0) return false;
        product.setStock(newStock);
        return true;
    }

    /**
     * S2: 调用 FileUploadUtil.saveFile，路径遍历漏洞从这里被触发
     */
    public String uploadImage(Long productId, String filename, byte[] content)
            throws IOException {
        Product product = productDao.findAll().stream()
            .filter(p -> p.getId().equals(productId))
            .findFirst()
            .orElse(null);
        if (product == null) throw new IllegalArgumentException("Product not found");

        String savedPath = FileUploadUtil.saveFile(filename, content);
        cache.put("image:" + productId, savedPath);
        return savedPath;
    }
}
