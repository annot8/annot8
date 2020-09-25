open module io.annot8.common.data {
  requires transitive io.annot8.api;
  requires io.annot8.common.utils;
  requires java.json.bind;
  requires com.google.common;
  requires java.desktop;

  exports io.annot8.common.data.bounds;
  exports io.annot8.common.data.content;
  exports io.annot8.common.data.tuple;
  exports io.annot8.common.data.properties;
  exports io.annot8.common.data.utils;
}
