package dev.anmijurane.InterfacesFuncionalExerc;

public class Calculator {

  public int calculateAndPrint(InnerCalculator calculator, int a, int b) {
    int result = calculator.apply(a, b);
    return result;
  }

  public static void main(String[] args) {
    Calculator calculator = new Calculator();
    int sum = calculator.calculateAndPrint((a, b) -> a + b, 10, 20);
    System.out.println("Result: " + sum);
    int sub = calculator.calculateAndPrint((a, b) -> a - b, 10, 20);
    System.out.println("Result: " + sub);
  }

}

@FunctionalInterface
interface InnerCalculator {
  int apply(int a, int b);
}
