/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.pipeline;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import io.annot8.api.pipelines.ItemStatus;
import io.annot8.api.pipelines.PipelineItemState;
import org.junit.jupiter.api.Test;

class SimplePipelineItemStateTest {

  @Test
  void test() {
    PipelineItemState pih = new SimplePipelineItemState();

    assertEquals(0, pih.getAll().size());

    pih.setItemStatus("test123", ItemStatus.PROCESSING);

    assertEquals(1, pih.getAll().size());
    assertEquals(ItemStatus.PROCESSING, pih.getItemStatus("test123").orElseThrow());

    pih.setItemStatus("test123", ItemStatus.PROCESSED_OK);

    assertEquals(1, pih.getAll().size());
    assertEquals(ItemStatus.PROCESSED_OK, pih.getItemStatus("test123").orElseThrow());

    assertFalse(pih.getItemStatus("not_set").isPresent());
  }
}
