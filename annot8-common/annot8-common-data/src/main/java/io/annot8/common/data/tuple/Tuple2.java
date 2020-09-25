/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.data.tuple;

import java.util.Objects;

public class Tuple2<A, B> implements Tuple {
  private final A a;
  private final B b;

  public Tuple2(A a, B b) {
    this.a = a;
    this.b = b;
  }

  public A getA() {
    return a;
  }

  public B getB() {
    return b;
  }

  public <Z> Tuple3<A, B, Z> append(Z z) {
    return new Tuple3<>(getA(), getB(), z);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Tuple2<?, ?> tuple2 = (Tuple2<?, ?>) o;
    return Objects.equals(a, tuple2.a) && Objects.equals(b, tuple2.b);
  }

  @Override
  public int hashCode() {
    return Objects.hash(a, b);
  }

  @Override
  public String toString() {
    return String.format("(%s, %s)", a, b);
  }
}
