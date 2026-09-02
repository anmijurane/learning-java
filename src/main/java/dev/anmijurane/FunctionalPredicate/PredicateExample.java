package dev.anmijurane.FunctionalPredicate;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class PredicateExample {

  public static void main(String[] args) {
    PredicateExample instance = new PredicateExample();
    instance.exercise1();
  }

  public void exercise1() {
    List<Product> products = List.of(
        new Product("1", "Laptop", "Electronics", 1200, 10, 4.5, true),
        new Product("2", "Mouse", "Electronics", 25, 50, 4.2, true),
        new Product("3", "Keyboard", "Electronics", 75, 20, 4.8, true),
        new Product("4", "Phone", "Electronics", 800, 5, 4.7, true),
        new Product("5", "Tablet", "Electronics", 300, 15, 4.4, true));

    Predicate<Product> isPrimeElegible = Product::isPrimeElegible;
    Predicate<Product> isAffordable = product -> product.getPrice() < 9000;
    Predicate<Product> isHighRated = product -> product.getRating() >= 4.5;
    Predicate<Product> isElectronics = product -> product.getCategory().equalsIgnoreCase("Electronics");
    Predicate<Product> hasStock = product -> product.getStock() >= 1;

    Predicate<Product> filtersProducts = isPrimeElegible
        .and(isAffordable)
        .and(isHighRated)
        .and(isElectronics)
        .and(hasStock);

    List<Product> productsFiltered = filterProducts(products, filtersProducts);

    productsFiltered.forEach(System.out::println);

  }

  public List<Product> filterProducts(List<Product> products, Predicate<Product> predicate) {
    return products.stream().filter(predicate).toList();
  }

  public void example() {
    Predicate<Integer> isEven = (num) -> num % 2 == 0;
    boolean resultIsEven = isEven.test(10);
    System.out.println("Is 10 even? " + resultIsEven);

    BiPredicate<String, Integer> isNameStartingWith = (name, num) -> name.startsWith(String.valueOf(num));
    boolean resultIsNameStartingWith = isNameStartingWith.test("John", 1);
    System.out.println("Is John starting with 1? " + resultIsNameStartingWith);

    BiPredicate<String, Integer> isNameStartingWith2 = (name, num) -> name.length() > num;
    boolean resultIsNameStartingWith2 = isNameStartingWith2.test("John", 3);
    System.out.println("Is John starting with 3? " + resultIsNameStartingWith2);

  }

}
