/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.data.tuple;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Tuple1Test {

  @Test
  void getA() {
    Tuple1<String> t = new Tuple1<>("a");
    assertEquals("a", t.getA());
  }

  @Test
  void append() {
    Tuple1<String> t = new Tuple1<>("a");
    Tuple2<String, String> t2 = t.append("b");
    assertEquals(new Tuple2<>("a", "b"), t2);
  }

  @Test
  void hashEqualString() {
    Tuple1<String> a = new Tuple1<>("a");
    Tuple1<String> b = new Tuple1<>("b");
    Tuple1<String> a2 = new Tuple1<>("a");

    assertNotEquals(a, b);
    assertNotEquals(a.hashCode(), b.hashCode());
    assertNotEquals(a.toString(), b.toString());

    assertEquals(a, a2);
    assertEquals(a.hashCode(), a2.hashCode());
    assertEquals(a.toString(), a2.toString());
  }
}
