/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.data.bounds;

import io.annot8.api.bounds.Bounds;
import io.annot8.api.data.Content;
import java.util.Optional;
import javax.json.bind.annotation.JsonbCreator;

/**
 * Implementation of Bounds indicating that an annotation does not have any bounds (i.e. it is
 * metadata).
 *
 * <p>This class is a singleton, and should be accessed via getInstance()
 */
public final class NoBounds implements Bounds {

  private static final NoBounds INSTANCE = new NoBounds();

  private NoBounds() {
    // Empty constructor
  }

  /**
   * Get instance
   *
   * @return the singleton instance of NoBounds
   */
  @JsonbCreator
  public static NoBounds getInstance() {
    return INSTANCE;
  }

  @Override
  public String toString() {
    return "NoBounds";
  }

  @Override
  public <D, C extends Content<D>, R> Optional<R> getData(C content, Class<R> requiredClass) {
    return Optional.empty();
  }

  @Override
  public <D, C extends Content<D>> boolean isValid(C content) {
    return true;
  }
}
