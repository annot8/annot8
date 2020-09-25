/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.reference.content;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import io.annot8.api.data.Content;
import io.annot8.api.exceptions.IncompleteException;
import io.annot8.implementations.reference.content.DefaultFile.BuilderFactory;
import io.annot8.testing.testimpl.TestItem;
import java.io.File;
import org.junit.jupiter.api.Test;

public class DefaultFileTest {

  @Test
  public void testBuilderFactory() {
    BuilderFactory factory = new BuilderFactory();
    assertNotNull(factory.create(new TestItem()));
    assertBasicBuilderUsage(factory.create(new TestItem()));
    assertIncompleteBuilderUsage(factory.create(new TestItem()));
  }

  private void assertIncompleteBuilderUsage(Content.Builder<DefaultFile, File> builder) {
    assertThrows(IncompleteException.class, builder::save);
  }

  private void assertBasicBuilderUsage(Content.Builder<DefaultFile, File> builder) {
    Content<File> content = null;
    try {
      content = builder.withDescription("test").withData(new File("test")).save();
    } catch (IncompleteException e) {
      fail("Exception not expected here", e);
    }
    assertEquals("test", content.getDescription());
    assertEquals(new File("test"), content.getData());
  }
}
