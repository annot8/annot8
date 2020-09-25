/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.reference.content;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.annot8.implementations.support.properties.MapImmutableProperties;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.junit.jupiter.api.Test;

class DefaultImageTest {

  DefaultImage image =
      new DefaultImage(
          null,
          "t",
          "d",
          new MapImmutableProperties.Builder().save(),
          () -> new BufferedImage(100, 120, BufferedImage.TYPE_INT_RGB));

  @Test
  void getSize() {
    assertEquals(100, image.getWidth());
    assertEquals(120, image.getHeight());
  }

  @Test
  void saveAsJpg() throws IOException {
    ByteArrayOutputStream boas = new ByteArrayOutputStream();
    image.saveAsJpg(boas);
    byte[] bytes = boas.toByteArray();

    assertTrue(bytes.length > 0);
    assertEquals(0xFF, bytes[0] & 0XFF);
    assertEquals(0xD8, bytes[1] & 0XFF);
    assertEquals(0xFF, bytes[2] & 0XFF);
  }

  @Test
  void saveAsPng() throws IOException {
    ByteArrayOutputStream boas = new ByteArrayOutputStream();
    image.saveAsPng(boas);
    byte[] bytes = boas.toByteArray();

    assertTrue(bytes.length > 0);
    assertEquals(0x89, bytes[0] & 0XFF);
    assertEquals('P', bytes[1]);
    assertEquals('N', bytes[2]);
    assertEquals('G', bytes[3]);
  }
}
