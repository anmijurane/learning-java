package dev.amazonFakeTask.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Product implements Identifiable<Long> {

  private Long id;
  private String name;
  private double price;
  private int stock;
  private ProductCategoryEnum category;

  public Long getId() {
    return this.id;
  }

  @Override
  public boolean equals(Object obj) {
    Product tempProduct = (Product) obj;
    if (obj == null || getClass() != obj.getClass())
      return false;
    return this.id == tempProduct.getId();
  }

}
