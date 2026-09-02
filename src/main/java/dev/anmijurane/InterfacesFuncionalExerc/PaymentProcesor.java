package dev.anmijurane.InterfacesFuncionalExerc;

import java.util.ArrayList;
import java.util.List;

public class PaymentProcesor {

  public void executeTransaction(Transaction trx, TransactionValidator validator) {
    trx.setProcessed(validator.validate(trx));
    if (trx.isProcessed()) {
      System.out.println("Processing payment: \"" + trx.getId() + "\"");
    } else {
      System.out.println("Payment \"" + trx.getId() + "\" rejected for security rules");
    }
  }

  public static void main(String[] args) {
    PaymentProcesor paymentProcesor = new PaymentProcesor();

    List<Transaction> transactions = new ArrayList<>();

    transactions.add(new Transaction("1", 100.0, "MX"));
    transactions.add(new Transaction("2", 200.0, "US"));
    transactions.add(new Transaction("3", 300.0, "CO"));
    transactions.add(new Transaction("4", 400.0, "US"));
    transactions.add(new Transaction("5", 500.0, "EC"));
    transactions.add(new Transaction("6", 600.0, "CA"));
    transactions.add(new Transaction("7", 700.0, "BO"));
    transactions.add(new Transaction("8", 800.0, "CA"));
    transactions.add(new Transaction("9", 900.0, "CO"));
    transactions.add(new Transaction("10", 1000.0, "US"));
    transactions.add(new Transaction("11", 450.0, "NK"));

    transactions.forEach(trx -> {
      paymentProcesor.executeTransaction(trx, tr -> tr.getAmount() <= 800); // IsFraud
      paymentProcesor.executeTransaction(trx, tr -> !tr.getOriginCountry().equals("BO")); // Blacklist
      paymentProcesor.executeTransaction(trx, tr -> !tr.getOriginCountry().equals("NK")); // Blacklist
      paymentProcesor.executeTransaction(trx, tr -> tr.getAmount() > 1.0); // micro payments blocked
    });

    System.out.println("--------------------------------------------------------");
    transactions.stream().forEach(System.out::println);

  }

}

@FunctionalInterface
interface TransactionValidator {
  boolean validate(Transaction trx);
}
