/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.support.factories;

import io.annot8.api.annotations.Group;
import io.annot8.api.data.Item;
import io.annot8.api.stores.GroupStore;

/**
 * Factory to createContent an group builder.
 *
 * <p>Typically used in a GroupStore.getBuilder().
 */
@FunctionalInterface
public interface GroupBuilderFactory {

  /**
   * Create a new builder for the provided item.
   *
   * @param item the item owning this content
   * @param groupStore the group store
   * @return non-null
   */
  Group.Builder create(Item item, GroupStore groupStore);
}
