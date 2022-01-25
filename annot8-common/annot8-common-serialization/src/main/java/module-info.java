/** Module for serialization classes */
module io.annot8.common.serialization {
  requires transitive io.annot8.api;
  requires transitive org.slf4j;
  requires transitive jakarta.json;
  requires transitive jakarta.json.bind;

  exports io.annot8.common.serialization;
}
