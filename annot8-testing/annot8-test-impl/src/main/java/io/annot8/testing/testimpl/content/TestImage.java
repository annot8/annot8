/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.testing.testimpl.content;

import io.annot8.api.data.Content;
import io.annot8.api.data.Item;
import io.annot8.api.properties.ImmutableProperties;
import io.annot8.common.data.content.Image;
import io.annot8.implementations.support.content.AbstractContentBuilder;
import io.annot8.implementations.support.content.AbstractContentBuilderFactory;
import io.annot8.testing.testimpl.AbstractTestContent;
import io.annot8.testing.testimpl.TestAnnotationStore;
import io.annot8.testing.testimpl.TestProperties;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.function.Supplier;
import javax.imageio.ImageIO;

public class TestImage extends AbstractTestContent<BufferedImage> implements Image {

  private int height;
  private Integer width;

  public TestImage() {
    this(null);
  }

  public TestImage(Item item) {
    this(
        item,
        "id",
        "description",
        new TestProperties(),
        () -> new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB));
  }

  public TestImage(
      Item item,
      String id,
      String description,
      ImmutableProperties properties,
      Supplier<BufferedImage> data) {
    super(item, BufferedImage.class, TestAnnotationStore::new, id, description, properties, data);

    this.height = data.get().getHeight();
    this.width = data.get().getWidth();
  }

  @Override
  public Class<Image> getContentClass() {
    return Image.class;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public int getWidth() {
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

      return new TestImage(getItem(), id, description, properties, data);
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
