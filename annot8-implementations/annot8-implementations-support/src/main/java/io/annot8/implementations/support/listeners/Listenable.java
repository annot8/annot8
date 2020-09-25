/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.support.listeners;

public interface Listenable<L> {

  Deregister register(L listener);

  void deregister(L listener);
}
