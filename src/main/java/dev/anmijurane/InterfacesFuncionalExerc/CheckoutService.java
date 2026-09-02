package dev.anmijurane.InterfacesFuncionalExerc;

public class CheckoutService {

  public void processOrder(ItemShop itemShop, DiscountStrategy strategy) {
    double finalPrice = strategy.apply(itemShop.getPrice());
    itemShop.setPriceWithDiscount(finalPrice);
    itemShop.processPayment();
  }

  public static void main(String[] args) {
    CheckoutService checkoutService = new CheckoutService();

    ItemShop book = new ItemShop("MacBook Pro", 1000.0);
    ItemShop iphone = new ItemShop("iPhone", 1600.0);
    ItemShop appleWatch = new ItemShop("Apple Watch", 1300.0);
    ItemShop airPods = new ItemShop("AirPods", 250.0);
    ItemShop ipad = new ItemShop("iPad", 800.0);

    checkoutService.processOrder(book, price -> price * 0.85);
    checkoutService.processOrder(iphone, price -> price * 0.85);
    checkoutService.processOrder(appleWatch, price -> price * 0.85);
    checkoutService.processOrder(airPods, price -> price * 0.85);
    checkoutService.processOrder(ipad, price -> price * 0.85);

    System.out.println(book.toString());
    System.out.println(iphone.toString());
    System.out.println(appleWatch.toString());
    System.out.println(airPods.toString());
    System.out.println(ipad.toString());
  }

}

@FunctionalInterface
interface DiscountStrategy {
  double apply(double amount);
}
