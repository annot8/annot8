/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.support.stores;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import io.annot8.api.data.Item;
import io.annot8.api.data.ItemFactory;
import io.annot8.implementations.support.listeners.Deregister;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NotifyingItemFactoryTest {

  @Mock ItemFactory delegateItemFactory;

  @Mock Item itemWithoutParent;

  @Mock Item itemWithParent;

  private NotifyingItemFactory itemFactory;

  @BeforeEach
  public void beforeEach() {

    itemFactory = new NotifyingItemFactory(delegateItemFactory);
  }

  @Test
  void create() {

    when(delegateItemFactory.create()).thenReturn(itemWithoutParent);

    List<Item> items = new LinkedList<>();
    itemFactory.register(items::add);
    final Item item = itemFactory.create();

    assertThat(item).isEqualTo(itemWithoutParent);
    Assertions.assertThat(items).containsExactly(itemWithoutParent);
  }

  @Test
  void createWithParent() {
    when(delegateItemFactory.create(Mockito.any(Item.class))).thenReturn(itemWithParent);

    List<Item> items = new LinkedList<>();
    itemFactory.register(items::add);
    final Item item = itemFactory.create(itemWithoutParent);

    assertThat(item).isEqualTo(itemWithParent);
    Assertions.assertThat(items).containsExactly(itemWithParent);
  }

  @Test
  void deregister() {
    when(delegateItemFactory.create(Mockito.any(Item.class))).thenReturn(itemWithParent);

    List<Item> items = new LinkedList<>();

    Consumer<Item> consumer = items::add;
    Deregister deregister = itemFactory.register(consumer);

    // This would fire as above

    deregister.deregister();

    final Item item = itemFactory.create(itemWithoutParent);

    assertThat(item).isEqualTo(itemWithParent);
    Assertions.assertThat(items).isEmpty();
  }
}
