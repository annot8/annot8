/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.reference.content;

import static org.junit.jupiter.api.Assertions.*;

import io.annot8.api.data.Item;
import io.annot8.api.exceptions.Annot8RuntimeException;
import io.annot8.implementations.reference.content.DefaultInputStream.Builder;
import io.annot8.implementations.reference.content.DefaultInputStream.BuilderFactory;
import io.annot8.testing.testimpl.TestConstants;
import io.annot8.testing.testimpl.TestItem;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Test;

class DefaultInputStreamTest {

  Item item = new TestItem();

  @Test
  void testBuilderFactory() {
    BuilderFactory factory = new BuilderFactory();
    assertNotNull(factory.create(new TestItem()));
  }

  @Test
  void testBuilder() {
    Builder builder = new Builder(item);
    DefaultInputStream content =
        builder.create(
            TestConstants.CONTENT_ID,
            TestConstants.CONTENT_DESCRIPTION,
            null,
            () -> new ByteArrayInputStream("test".getBytes()));
    assertEquals(TestConstants.CONTENT_ID, content.getId());
    assertEquals(TestConstants.CONTENT_DESCRIPTION, content.getDescription());
    try {
      assertEquals("test", new String(content.getData().readAllBytes()));
    } catch (IOException e) {
      fail("Test should not throw an exception here", e);
    }
    // Test reading for a second time
    try {
      assertEquals("test", new String(content.getData().readAllBytes()));
    } catch (IOException e) {
      fail("Test should not throw an exception here", e);
    }
  }

  @Test
  void testNonSupplierError() {
    Builder builder = new Builder(item);
    InputStream inputStream = new ByteArrayInputStream("test".getBytes());
    assertThrows(Annot8RuntimeException.class, () -> builder.withData(inputStream));
  }
}
