/** Module for serialization classes */
module io.annot8.common.serialization {
  requires transitive io.annot8.api;
  requires transitive org.slf4j;
  requires jakarta.json;
  requires jakarta.json.bind;

  exports io.annot8.common.serialization;
}
