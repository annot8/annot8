/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.serialization;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.annot8.common.serialization.TestNested.Descriptor;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import org.junit.jupiter.api.Test;

public class Annot8ComponentDescriptorSerializerTest {

  @Test
  public void test() {
    JsonbConfig config =
        new JsonbConfig().withSerializers(new Annot8ComponentDescriptorSerializer());
    Jsonb jb = JsonbBuilder.create(config);

    String json = jb.toJson(new TestDescriptor("Test", "localhost", 8080));
    assertEquals(
        "{\""
            + TestDescriptor.class.getName()
            + "\":{\"name\":\"Test\",\"settings\":{\"host\":\"localhost\",\"port\":8080}}}",
        json);
  }

  @Test
  public void testNested() {
    JsonbConfig config =
        new JsonbConfig().withSerializers(new Annot8ComponentDescriptorSerializer());
    Jsonb jb = JsonbBuilder.create(config);

    Descriptor desc = new Descriptor();
    String json = jb.toJson(desc);
    assertEquals(
        "{\"io.annot8.common.serialization.TestNested$Descriptor\":{\"name\":\"\",\"settings\":{}}}",
        json);
  }
}
