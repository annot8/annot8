/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.support.registries;

import io.annot8.api.components.Annot8ComponentDescriptor;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Annot8ComponentRegistryTest {

  private Annot8ComponentRegistry registry;

  @BeforeEach
  public void before() {
    Set<Class<? extends Annot8ComponentDescriptor>> components = new HashSet<>();
    components.add(TestProcessorDescriptor.class);
    components.add(TestSourceDescriptor.class);
    registry = new Annot8ComponentRegistry(components);
  }

  @Test
  public void testGet() {
    Assertions.assertNotNull(registry);
    Assertions.assertEquals(1, registry.getProcessors().count());
    Assertions.assertEquals(1, registry.getSources().count());
  }
}
