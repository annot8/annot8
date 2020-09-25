/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.pipeline;

import static org.mockito.Mockito.*;

import io.annot8.api.components.Processor;
import io.annot8.api.components.ProcessorDescriptor;
import io.annot8.api.components.Source;
import io.annot8.api.components.SourceDescriptor;
import io.annot8.api.components.responses.ProcessorResponse;
import io.annot8.api.components.responses.SourceResponse;
import io.annot8.api.context.Context;
import io.annot8.api.data.Item;
import io.annot8.api.data.ItemFactory;
import io.annot8.api.pipelines.PipelineDescriptor;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;

public class InMemoryPipelineDescriptorRunnerTest {

  @Test
  public void test() {
    ItemFactory itemFactory = mock(ItemFactory.class);
    when(itemFactory.create()).thenReturn(mock(Item.class));

    // In total, creates 5 items
    Source source1 = mock(Source.class);
    Source source2 = mock(Source.class);
    when(source1.read(any()))
        .thenAnswer(
            (Answer<SourceResponse>)
                invocationOnMock -> {
                  invocationOnMock.getArgument(0, ItemFactory.class).create();
                  return SourceResponse.ok();
                })
        .thenAnswer(
            (Answer<SourceResponse>)
                invocationOnMock -> {
                  invocationOnMock.getArgument(0, ItemFactory.class).create();
                  return SourceResponse.ok();
                })
        .thenAnswer(
            (Answer<SourceResponse>)
                invocationOnMock -> {
                  invocationOnMock.getArgument(0, ItemFactory.class).create();
                  return SourceResponse.ok();
                })
        .thenAnswer(
            (Answer<SourceResponse>)
                invocationOnMock -> {
                  invocationOnMock.getArgument(0, ItemFactory.class).create();
                  return SourceResponse.done();
                });
    when(source2.read(any()))
        .thenReturn(SourceResponse.empty())
        .thenAnswer(
            (Answer<SourceResponse>)
                invocationOnMock -> {
                  invocationOnMock.getArgument(0, ItemFactory.class).create();
                  return SourceResponse.ok();
                })
        .thenReturn(SourceResponse.sourceError());

    Processor processor1 = mock(Processor.class);
    Processor processor2 = mock(Processor.class);
    when(processor1.process(any()))
        .thenReturn(
            ProcessorResponse.ok(),
            ProcessorResponse.itemError(),
            ProcessorResponse.ok(),
            ProcessorResponse.ok(),
            ProcessorResponse.ok());
    when(processor2.process(any()))
        .thenReturn(
            ProcessorResponse.ok(), ProcessorResponse.ok(), ProcessorResponse.processingError());

    SourceDescriptor sd1 = mock(SourceDescriptor.class);
    when(sd1.create(any(Context.class))).thenReturn(source1);
    SourceDescriptor sd2 = mock(SourceDescriptor.class);
    when(sd2.create(any(Context.class))).thenReturn(source2);

    ProcessorDescriptor pd1 = mock(ProcessorDescriptor.class);
    when(pd1.create(any(Context.class))).thenReturn(processor1);
    ProcessorDescriptor pd2 = mock(ProcessorDescriptor.class);
    when(pd2.create(any(Context.class))).thenReturn(processor2);

    PipelineDescriptor pipelineDescriptor = mock(PipelineDescriptor.class);
    when(pipelineDescriptor.getName()).thenReturn("test");
    when(pipelineDescriptor.getSources()).thenReturn(Arrays.asList(sd1, sd2));
    when((pipelineDescriptor.getProcessors())).thenReturn(Arrays.asList(pd1, pd2));

    InMemoryPipelineRunner runner = new InMemoryPipelineRunner(pipelineDescriptor, itemFactory);
    runner.run();

    verify(itemFactory, times(5)).create();
    verify(source1, times(4)).read(any());
    verify(source2, times(3)).read(any());
    verify(processor1, times(5)).process(any());
    verify(processor2, times(3)).process(any());
  }
}
