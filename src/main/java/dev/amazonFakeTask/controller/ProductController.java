package dev.amazonFakeTask.controller;

import dev.amazonFakeTask.model.Product;
import dev.amazonFakeTask.model.ProductCategoryEnum;
import dev.amazonFakeTask.repository.GenericRepository;

public class ProductController {

  private final GenericRepository<Product, Long> productRepository;

  public ProductController(GenericRepository<Product, Long> productRepo) {
    this.productRepository = productRepo;
  }

  public void addProduct(Long id, String name, double price, int stock, ProductCategoryEnum category) {
    if (id != null || name != "") {
    }
    this.productRepository.add(null);
  }

}
