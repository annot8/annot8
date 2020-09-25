/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.utils.java;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.Test;

class CollectionUtilsTest {

  @Test
  void nullCollection() {
    final Collection<Object> objects = CollectionUtils.nonNullCollection(null);

    assertNotNull(objects);
    assertEquals(0, objects.size());
  }

  @Test
  void notNullCollection() {
    List<Object> list = new ArrayList<>(10);
    final Collection<Object> objects = CollectionUtils.nonNullCollection(list);
    assertSame(objects, list);
  }
}
