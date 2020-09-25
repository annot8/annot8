open module io.annot8.common.processing {
  requires transitive io.annot8.api;
  requires com.google.common;
  requires io.annot8.common.data;

  exports io.annot8.common.processing.filters;
  exports io.annot8.common.processing.indices;
}
