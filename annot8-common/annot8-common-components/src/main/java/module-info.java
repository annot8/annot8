open module io.annot8.common.components {
  requires transitive io.annot8.api;
  requires org.slf4j;
  requires micrometer.core;
  requires java.json.bind;
  requires io.annot8.common.data;

  exports io.annot8.common.components;
  exports io.annot8.common.components.logging;
  exports io.annot8.common.components.metering;
  exports io.annot8.common.components.capabilities;
}
