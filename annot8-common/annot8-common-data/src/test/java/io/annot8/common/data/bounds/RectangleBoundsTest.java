/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.data.bounds;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.annot8.api.data.Content;
import io.annot8.api.data.Item;
import io.annot8.api.properties.ImmutableProperties;
import io.annot8.api.stores.AnnotationStore;
import io.annot8.common.data.content.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class RectangleBoundsTest {

  @Test
  void getData() {
    RectangleBounds r = new RectangleBounds(20, 10, 80, 90);

    Optional<BufferedImage> data = r.getData(new FakeImage());

    assertTrue(data.isPresent());

    BufferedImage image = data.get();

    assertEquals(80, image.getHeight());
    assertEquals(60, image.getWidth());
  }

  class FakeImage implements Image {

    @Override
    public int getWidth() {
      return 0;
    }

    @Override
    public int getHeight() {
      return 0;
    }

    @Override
    public void saveAsJpg(OutputStream outputStream) throws IOException {}

    @Override
    public void saveAsPng(OutputStream outputStream) throws IOException {}

    @Override
    public Item getItem() {
      return null;
    }

    @Override
    public BufferedImage getData() {
      return new BufferedImage(100, 100, TYPE_INT_RGB);
    }

    @Override
    public Class<BufferedImage> getDataClass() {
      return BufferedImage.class;
    }

    @Override
    public Class<? extends Content<BufferedImage>> getContentClass() {
      return Image.class;
    }

    @Override
    public AnnotationStore getAnnotations() {
      return null;
    }

    @Override
    public String getDescription() {
      return null;
    }

    @Override
    public String getId() {
      return null;
    }

    @Override
    public ImmutableProperties getProperties() {
      return null;
    }
  }
}
