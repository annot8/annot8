/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.components.capabilities;

import static org.junit.jupiter.api.Assertions.*;

import io.annot8.api.capabilities.ContentCapability;
import io.annot8.common.data.content.Text;
import org.junit.jupiter.api.Test;

class SimpleContentCapabilityTest {

  @Test
  void testGetters() {
    ContentCapability cc = new SimpleContentCapability(Text.class);

    assertEquals(Text.class, cc.getType());
  }

  @Test
  void testEqualAndHashCode() {
    ContentCapability cc1 = new SimpleContentCapability(Text.class);
    ContentCapability cc2 = new SimpleContentCapability(Text.class);

    assertEquals(cc1, cc2);
    assertNotEquals("Hello world", cc1);
    assertNotEquals(123, cc1);

    assertEquals(cc1.hashCode(), cc2.hashCode());
  }
}
