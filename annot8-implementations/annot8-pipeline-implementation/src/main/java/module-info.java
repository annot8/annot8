open module io.annot8.implementations.pipelines {
  requires transitive io.annot8.api;
  requires org.slf4j;
  requires io.annot8.implementations.support;
  requires io.annot8.common.components;

  exports io.annot8.implementations.pipeline;
}
