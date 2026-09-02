package dev.anmijurane.InterfacesFuncionalExerc;

import lombok.RequiredArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Transaction {
  private final String id;
  private final double amount;
  private final String originCountry;
  private boolean isProcessed;
}
