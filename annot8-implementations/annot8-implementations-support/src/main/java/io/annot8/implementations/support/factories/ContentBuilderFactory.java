/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.support.factories;

import io.annot8.api.data.Content;
import io.annot8.api.data.Item;

/**
 * Factory to createContent an content builder.
 *
 * <p>Typically used in a Item.createContent().
 */
public interface ContentBuilderFactory<D, C extends Content<D>> {

  /**
   * Create a new builder for the provided item.
   *
   * @param item the item owning this content
   * @return non-null builder
   */
  Content.Builder<C, D> create(Item item);

  /**
   * Get the class of the data this content holds
   *
   * @return the data class
   */
  Class<D> getDataClass();

  /**
   * Get the content class created.
   *
   * @return content class (being implemented)
   */
  Class<C> getContentClass();
}
