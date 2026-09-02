package dev.anmijurane.FunctionalPredicate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class Product {

  final String id;
  final String name;
  final String category;
  final double price;
  final int stock;
  final double rating;
  final boolean isPrimeElegible;

}
