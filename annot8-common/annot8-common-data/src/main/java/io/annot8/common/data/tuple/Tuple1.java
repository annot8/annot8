/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.data.tuple;

import java.util.Objects;

public class Tuple1<A> implements Tuple {
  private final A a;

  public Tuple1(A a) {
    this.a = a;
  }

  public A getA() {
    return a;
  }

  public <Z> Tuple2<A, Z> append(Z z) {
    return new Tuple2<>(getA(), z);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Tuple1<?> tuple1 = (Tuple1<?>) o;
    return Objects.equals(a, tuple1.a);
  }

  @Override
  public int hashCode() {
    return Objects.hash(a);
  }

  @Override
  public String toString() {
    return String.format("(%s)", a);
  }
}
