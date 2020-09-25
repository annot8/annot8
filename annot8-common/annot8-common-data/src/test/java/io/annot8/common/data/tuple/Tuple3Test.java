/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.data.tuple;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Tuple3Test {

  @Test
  void getA() {
    Tuple3<String, String, String> t = new Tuple3<>("a", "b", "c");
    assertEquals("a", t.getA());
    assertEquals("b", t.getB());
    assertEquals("c", t.getC());
  }

  @Test
  void hashEqualString() {
    Tuple3<String, String, String> a = new Tuple3<>("a", "b", "c");
    Tuple3<String, String, String> b = new Tuple3<>("b", "a", "c");
    Tuple3<String, String, String> a2 = new Tuple3<>("a", "b", "c");

    assertNotEquals(a, b);
    assertNotEquals(a.hashCode(), b.hashCode());
    assertNotEquals(a.toString(), b.toString());

    assertEquals(a, a2);
    assertEquals(a.hashCode(), a2.hashCode());
    assertEquals(a.toString(), a2.toString());
  }
}
