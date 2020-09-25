/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.data.tuple;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Tuple2Test {

  @Test
  void get() {
    Tuple2<String, String> t = new Tuple2<>("a", "b");
    assertEquals("a", t.getA());
    assertEquals("b", t.getB());
  }

  @Test
  void append() {
    Tuple2<String, String> t2 = new Tuple2<>("a", "b");
    Tuple3<String, String, String> t3 = t2.append("c");
    assertEquals(new Tuple3<>("a", "b", "c"), t3);
  }

  @Test
  void hashEqualString() {
    Tuple2<String, String> a = new Tuple2<>("a", "b");
    Tuple2<String, String> b = new Tuple2<>("b", "a");
    Tuple2<String, String> a2 = new Tuple2<>("a", "b");

    assertNotEquals(a, b);
    assertNotEquals(a.hashCode(), b.hashCode());
    assertNotEquals(a.toString(), b.toString());

    assertEquals(a, a2);
    assertEquals(a.hashCode(), a2.hashCode());
    assertEquals(a.toString(), a2.toString());
  }
}
