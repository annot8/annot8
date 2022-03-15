/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.reference.factories;

import io.annot8.api.data.Item;
import io.annot8.api.data.ItemFactory;
import io.annot8.implementations.reference.data.DefaultItem;
import io.annot8.implementations.support.registries.ContentBuilderFactoryRegistry;

public class DefaultItemFactory implements ItemFactory {

  private final ContentBuilderFactoryRegistry contentBuilderFactoryRegistry;

  public DefaultItemFactory(ContentBuilderFactoryRegistry contentBuilderFactoryRegistry) {
    this.contentBuilderFactoryRegistry = contentBuilderFactoryRegistry;
  }

  @Override
  public Item create(Item parent, String id) {
    return new DefaultItem(
        id, parent == null ? null : parent.getId(), this, contentBuilderFactoryRegistry);
  }
}
