/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.support.listeners;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;

class ListenersTest {

  final Listeners<Consumer<Object>, Object> listeners = new Listeners<>(Consumer::accept);

  @Test
  void fireWithoutListeners() {
    listeners.fire(new Object());
  }

  @Test
  void fireWithListeners() {
    List<Object> list1 = new ArrayList<>();
    List<Object> list2 = new ArrayList<>();

    listeners.register(list1::add);

    Object a = new Object();
    Object b = new Object();

    listeners.fire(a);

    assertThat(list1).containsExactly(a);

    listeners.register(list2::add);

    listeners.fire(b);

    assertThat(list1).containsExactly(a, b);
    assertThat(list2).containsExactly(b);
  }

  @Test
  void deregisterListeners() {
    List<Object> list1 = new ArrayList<>();
    listeners.register(list1::add);

    List<Object> list2 = new ArrayList<>();
    Deregister deregister = listeners.register(list2::add);

    Object a = new Object();
    Object b = new Object();

    listeners.fire(a);

    assertThat(list1).containsExactly(a);
    assertThat(list2).containsExactly(a);

    deregister.deregister();

    listeners.fire(b);

    assertThat(list1).containsExactly(a, b);
    assertThat(list2).containsExactly(a);
  }

  @Test
  void clearListeners() {
    List<Object> list1 = new ArrayList<>();
    listeners.register(list1::add);

    List<Object> list2 = new ArrayList<>();
    Deregister deregister = listeners.register(list2::add);

    Object a = new Object();
    Object b = new Object();

    listeners.fire(a);

    assertThat(list1).containsExactly(a);
    assertThat(list2).containsExactly(a);

    listeners.clear();

    listeners.fire(b);

    assertThat(list1).containsExactly(a);
    assertThat(list2).containsExactly(a);
  }
}
