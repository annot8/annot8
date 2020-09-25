open module io.annot8.testing.tck.impl {
  requires io.annot8.implementations.support;
  requires io.annot8.common.data;
  requires io.annot8.testing.testimpl;
  requires org.junit.jupiter.api;
  requires org.mockito;
  requires org.assertj.core;

  exports io.annot8.testing.tck.impl;
}
