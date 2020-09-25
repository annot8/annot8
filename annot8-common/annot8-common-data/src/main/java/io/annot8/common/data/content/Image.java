/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.data.content;

import io.annot8.api.data.Content;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Represents a image content.
 *
 * <p>The data contains BufferedImage which is format independent.
 */
public interface Image extends Content<BufferedImage> {

  int getWidth();

  int getHeight();

  void saveAsJpg(OutputStream outputStream) throws IOException;

  void saveAsPng(OutputStream outputStream) throws IOException;
}
