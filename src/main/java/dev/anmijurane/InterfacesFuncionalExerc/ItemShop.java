package dev.anmijurane.InterfacesFuncionalExerc;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ItemShop {

  private final String name;
  private final double price;
  private double priceWithDiscount;

  public void processPayment() {
    System.out.println(
        "Processing payment for " + name + " price real " + price + " and price with discount " + priceWithDiscount);
    System.out.println("Payment processed");
  }

}
