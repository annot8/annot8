/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.data.bounds;

import io.annot8.api.bounds.Bounds;
import io.annot8.api.data.Content;
import io.annot8.common.data.content.Image;
import java.awt.image.BufferedImage;
import java.util.Optional;

/**
 * A bounds which is a rectangle.
 *
 * <p>This is used for images.
 *
 * <p>NOTE that BufferedImage is (0,0) at the upper-left, so top is less than bottom. Since this may
 * be confusing the constructor will correct the ordering
 */
public class RectangleBounds implements Bounds {

  private final int top;
  private final int right;
  private final int bottom;
  private final int left;

  public RectangleBounds(int left, int top, int right, int bottom) {
    this.top = Math.min(top, bottom);
    this.right = Math.max(left, right);
    this.bottom = Math.max(top, bottom);
    this.left = Math.min(left, right);
  }

  @Override
  public <D, C extends Content<D>, R> Optional<R> getData(C content, Class<R> requiredClass) {
    if (content.getDataClass().equals(BufferedImage.class)
        && requiredClass.isAssignableFrom(BufferedImage.class)) {
      BufferedImage bi = (BufferedImage) content.getData();

      BufferedImage subimage = bi.getSubimage(left, top, getWidth(), getHeight());
      return Optional.of((R) subimage);
    }

    return Optional.empty();
  }

  @Override
  public <D, C extends Content<D>> boolean isValid(C content) {
    return Image.class.isInstance(content);
  }

  public int getBottom() {
    return bottom;
  }

  public int getLeft() {
    return left;
  }

  public int getRight() {
    return right;
  }

  public int getTop() {
    return top;
  }

  public int getWidth() {
    return right - left;
  }

  public int getHeight() {
    return bottom - top;
  }
}
