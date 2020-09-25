/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.components.capabilities;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import io.annot8.api.capabilities.AnnotationCapability;
import io.annot8.api.capabilities.Capabilities;
import io.annot8.api.capabilities.ContentCapability;
import io.annot8.api.capabilities.GroupCapability;
import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.Test;

public class SimpleCapabilitiesTest {

  @Test
  public void test() {
    AnnotationCapability ac1 = mock(AnnotationCapability.class);
    AnnotationCapability ac2 = mock(AnnotationCapability.class);

    GroupCapability gc1 = mock(GroupCapability.class);
    GroupCapability gc2 = mock(GroupCapability.class);

    ContentCapability cc1 = mock(ContentCapability.class);
    ContentCapability cc2 = mock(ContentCapability.class);

    Capabilities c =
        new SimpleCapabilities(
            Arrays.asList(ac1, gc1), Arrays.asList(ac2, cc1, cc2), Collections.singletonList(gc2));

    assertEquals(2, c.creates().count());
    assertEquals(3, c.processes().count());
    assertEquals(1, c.deletes().count());

    assertEquals(1, c.creates(AnnotationCapability.class).count());
    assertEquals(1, c.creates(GroupCapability.class).count());
    assertEquals(0, c.creates(ContentCapability.class).count());

    assertEquals(1, c.processes(AnnotationCapability.class).count());
    assertEquals(0, c.processes(GroupCapability.class).count());
    assertEquals(2, c.processes(ContentCapability.class).count());

    assertEquals(0, c.deletes(AnnotationCapability.class).count());
    assertEquals(1, c.deletes(GroupCapability.class).count());
    assertEquals(0, c.deletes(ContentCapability.class).count());
  }

  @Test
  public void testBuilderFrom() {
    AnnotationCapability ac1 = mock(AnnotationCapability.class);
    AnnotationCapability ac2 = mock(AnnotationCapability.class);

    GroupCapability gc1 = mock(GroupCapability.class);
    GroupCapability gc2 = mock(GroupCapability.class);

    ContentCapability cc1 = mock(ContentCapability.class);
    ContentCapability cc2 = mock(ContentCapability.class);

    Capabilities c1 =
        new SimpleCapabilities(
            Arrays.asList(ac1, gc1), Arrays.asList(ac2, cc1, cc2), Collections.singletonList(gc2));
    Capabilities c2 = new SimpleCapabilities.Builder().from(c1).build();

    assertArrayEquals(c1.creates().toArray(), c2.creates().toArray());
    assertArrayEquals(c1.processes().toArray(), c2.processes().toArray());
    assertArrayEquals(c1.deletes().toArray(), c2.deletes().toArray());
  }
}
