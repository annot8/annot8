/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.reference.data;

import io.annot8.api.data.Item;
import io.annot8.implementations.reference.factories.DefaultContentBuilderFactoryRegistry;
import io.annot8.implementations.reference.factories.DefaultItemFactory;
import io.annot8.testing.tck.impl.AbstractItemTest;

public class DefaultItemTest extends AbstractItemTest {

  @Override
  protected Item getItem() {
    DefaultContentBuilderFactoryRegistry registry = new DefaultContentBuilderFactoryRegistry(true);
    return new DefaultItem(new DefaultItemFactory(registry), registry);
  }
}
