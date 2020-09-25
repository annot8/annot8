/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.data.bounds;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.annot8.api.data.Content;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ContentBoundsTest {

  @Test
  void testContentBounds() {
    ContentBounds cb = ContentBounds.getInstance();
    Assertions.assertNotNull(cb);
    assertEquals(cb, ContentBounds.getInstance());

    assertTrue(cb.isValid(null));

    Content<String> content = mock(Content.class);
    when(content.getData()).thenReturn("test content");
    when(content.getDataClass()).thenReturn(String.class);

    Optional<String> data = cb.getData(content, String.class);
    assertEquals("test content", data.get());

    Optional<String> data2 = cb.getData(content);
    assertEquals("test content", data2.get());
  }
}
