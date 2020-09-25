open module io.annot8.testing.testimpl {
  requires transitive io.annot8.api;
  requires io.annot8.implementations.support;
  requires io.annot8.common.utils;
  requires io.annot8.common.data;
  requires org.slf4j;
  requires java.json.bind;
  requires java.desktop;

  exports io.annot8.testing.testimpl.content;
  exports io.annot8.testing.testimpl;
}
