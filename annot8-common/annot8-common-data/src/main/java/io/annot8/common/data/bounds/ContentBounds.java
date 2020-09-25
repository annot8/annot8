/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.data.bounds;

import io.annot8.api.bounds.Bounds;
import io.annot8.api.data.Content;
import java.util.Optional;
import javax.json.bind.annotation.JsonbCreator;

/**
 * Implementation of Bounds indicating that an annotation covers the entire content.
 *
 * <p>This class is a singleton, and should be accessed via getInstance()
 */
public final class ContentBounds implements Bounds {

  private static final ContentBounds INSTANCE = new ContentBounds();

  private ContentBounds() {
    // Empty constructor
  }

  /**
   * Get instance
   *
   * @return the singleton instance of ContentBounds
   */
  @JsonbCreator
  public static ContentBounds getInstance() {
    return INSTANCE;
  }

  @Override
  public String toString() {
    return "ContentBounds";
  }

  @Override
  public <D, C extends Content<D>, R> Optional<R> getData(C content, Class<R> requiredClass) {
    D data = content.getData();

    if (requiredClass.isAssignableFrom(data.getClass())) {
      @SuppressWarnings("unchecked")
      R r = (R) data;
      return Optional.of(r);
    } else {
      return Optional.empty();
    }
  }

  @Override
  public <D, C extends Content<D>> boolean isValid(C content) {
    return true;
  }
}
