open module io.annot8.common.processing {
  requires transitive io.annot8.api;
  requires transitive io.annot8.common.data;
  requires com.google.common;

  exports io.annot8.common.processing.filters;
  exports io.annot8.common.processing.indices;
}
