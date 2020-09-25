/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.reference.content;

import io.annot8.api.data.Content;
import io.annot8.api.data.Item;
import io.annot8.api.properties.ImmutableProperties;
import io.annot8.common.data.content.Image;
import io.annot8.implementations.reference.stores.DefaultAnnotationStore;
import io.annot8.implementations.support.content.AbstractContent;
import io.annot8.implementations.support.content.AbstractContentBuilder;
import io.annot8.implementations.support.content.AbstractContentBuilderFactory;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.function.Supplier;
import javax.imageio.ImageIO;

public class DefaultImage extends AbstractContent<BufferedImage> implements Image {

  private Integer height;
  private Integer width;

  public DefaultImage(
      Item item,
      String id,
      String description,
      ImmutableProperties properties,
      Supplier<BufferedImage> data) {
    super(
        item,
        BufferedImage.class,
        Image.class,
        DefaultAnnotationStore::new,
        id,
        description,
        properties,
        data);
  }

  @Override
  public int getHeight() {
    if (height == null) {
      height = getData().getHeight();
    }
    return height;
  }

  @Override
  public int getWidth() {
    if (width == null) {
      width = getData().getWidth();
    }
    return width;
  }

  @Override
  public void saveAsJpg(OutputStream outputStream) throws IOException {
    // Convert to RGB so we can save it out
    BufferedImage bImgRgb = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

    Graphics g = bImgRgb.createGraphics();
    g.drawImage(getData(), 0, 0, null);
    g.dispose();

    boolean ret = ImageIO.write(bImgRgb, "jpg", outputStream);
    if (!ret) throw new IOException("No writer found for format JPG");
  }

  @Override
  public void saveAsPng(OutputStream outputStream) throws IOException {
    boolean ret = ImageIO.write(getData(), "png", outputStream);
    if (!ret) throw new IOException("No writer found for format PNG");
  }

  public static class Builder extends AbstractContentBuilder<BufferedImage, Image> {

    protected Builder(Item item) {
      super(item);
    }

    @Override
    protected Image create(
        String id,
        String description,
        ImmutableProperties properties,
        Supplier<BufferedImage> data) {

      return new DefaultImage(getItem(), id, description, properties, data);
    }
  }

  public static class BuilderFactory extends AbstractContentBuilderFactory<BufferedImage, Image> {

    public BuilderFactory() {
      super(BufferedImage.class, Image.class);
    }

    @Override
    public Content.Builder<Image, BufferedImage> create(Item item) {
      return new Builder(item);
    }
  }
}
