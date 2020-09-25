/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.testing.testimpl;

import io.annot8.api.data.Item;
import io.annot8.api.stores.GroupStore;
import io.annot8.implementations.support.stores.GroupStoreFactory;

public class TestGroupStoreFactory implements GroupStoreFactory {

  @Override
  public GroupStore create(Item item) {
    return new TestGroupStore(item);
  }
}
