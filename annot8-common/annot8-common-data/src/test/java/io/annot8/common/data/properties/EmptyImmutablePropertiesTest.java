/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.data.properties;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EmptyImmutablePropertiesTest {

  @Test
  void getAll() {
    assertTrue(EmptyImmutableProperties.getInstance().getAll().isEmpty());
  }

  @Test
  void testToString() {
    String s = EmptyImmutableProperties.getInstance().toString();
    Assertions.assertNotNull(s);
    Assertions.assertNotEquals("", s);
  }

  @Test
  void testEmptyImmutableProperties() {
    EmptyImmutableProperties eip = EmptyImmutableProperties.getInstance();
    Assertions.assertNotNull(eip);
    Assertions.assertEquals(eip, EmptyImmutableProperties.getInstance());
  }
}
