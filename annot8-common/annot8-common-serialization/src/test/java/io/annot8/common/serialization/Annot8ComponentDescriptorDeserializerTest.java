/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.serialization;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.annot8.api.components.Annot8ComponentDescriptor;
import io.annot8.common.serialization.TestNested.Configuration;
import io.annot8.common.serialization.TestNested.Descriptor;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import org.junit.jupiter.api.Test;

public class Annot8ComponentDescriptorDeserializerTest {

  @Test
  public void test() {
    JsonbConfig config =
        new JsonbConfig().withDeserializers(new Annot8ComponentDescriptorDeserializer());
    Jsonb jb = JsonbBuilder.create(config);

    Annot8ComponentDescriptor desc =
        jb.fromJson(
            "{\""
                + TestDescriptor.class.getName()
                + "\":{\"name\":\"Test\",\"settings\":{\"host\":\"localhost\",\"port\":8080}}}",
            Annot8ComponentDescriptor.class);
    assertEquals(TestDescriptor.class, desc.getClass());
    assertEquals("Test", desc.getName());

    assertEquals(TestSettings.class, desc.getSettings().getClass());
    TestSettings ts = (TestSettings) desc.getSettings();
    assertEquals("localhost", ts.getHost());
    assertEquals(8080, ts.getPort());

    assertEquals(TestProcessor.class, desc.create(null).getClass());
  }

  @Test
  public void testNested() {
    JsonbConfig config =
        new JsonbConfig().withDeserializers(new Annot8ComponentDescriptorDeserializer());
    Jsonb jb = JsonbBuilder.create(config);

    Annot8ComponentDescriptor desc =
        jb.fromJson(
            "{\"" + Descriptor.class.getName() + "\":{\"name\":\"Test\",\"settings\":{}}}",
            Annot8ComponentDescriptor.class);

    assertEquals(Descriptor.class, desc.getClass());
    assertEquals("Test", desc.getName());
    assertEquals(Configuration.class, desc.getSettings().getClass());
  }
}
