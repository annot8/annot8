/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.data.bounds;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.annot8.api.data.Content;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NoBoundsTest {

  @Test
  void testNoBounds() {
    NoBounds nb = NoBounds.getInstance();
    Assertions.assertNotNull(nb);
    assertEquals(nb, NoBounds.getInstance());
    assertNotNull(nb.toString());

    Content content = mock(Content.class);
    when(content.getData()).thenReturn("Hello world!");

    assertTrue(nb.isValid(content));
    assertFalse(nb.getData(content, String.class).isPresent());
    assertFalse(nb.getData(content, Long.class).isPresent());
    assertFalse(nb.getData(content).isPresent());
  }
}
