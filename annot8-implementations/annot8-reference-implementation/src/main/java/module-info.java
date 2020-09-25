open module io.annot8.implementations.reference {
  requires io.annot8.api;
  requires transitive io.annot8.implementations.support;
  requires transitive io.annot8.common.utils;
  requires transitive io.annot8.common.data;
  requires java.desktop;

  exports io.annot8.implementations.reference.annotations;
  exports io.annot8.implementations.reference.content;
  exports io.annot8.implementations.reference.data;
  exports io.annot8.implementations.reference.factories;
  exports io.annot8.implementations.reference.references;
  exports io.annot8.implementations.reference.stores;
}
