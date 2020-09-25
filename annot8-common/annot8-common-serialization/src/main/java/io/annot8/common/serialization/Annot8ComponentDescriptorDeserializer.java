/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.serialization;

import io.annot8.api.components.Annot8ComponentDescriptor;
import java.lang.reflect.Type;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbException;
import javax.json.bind.serializer.DeserializationContext;
import javax.json.bind.serializer.JsonbDeserializer;
import javax.json.stream.JsonParser;

/** Deserialize Annot8ComponentDescriptor from JSON, using the JSON-B deserializer interface */
public class Annot8ComponentDescriptorDeserializer
    implements JsonbDeserializer<Annot8ComponentDescriptor> {

  private final Jsonb jb = createJsonB();

  // Overideable method so that additional configuration can be passed until the issue below is
  // resolved
  protected Jsonb createJsonB() {
    return JsonbBuilder.create();
  }

  @Override
  public Annot8ComponentDescriptor deserialize(
      JsonParser parser, DeserializationContext ctx, Type type) {

    Annot8ComponentDescriptor desc = null;
    while (parser.hasNext()) {
      JsonParser.Event event = parser.next();
      if (event == JsonParser.Event.KEY_NAME) {
        String className = parser.getString();
        parser.next();
        try {
          /* TODO: This is not a good way of doing it, as we lose any user provided config (including additional deserializers)
           *    However, if we don't do this we get a recursive error because we try to deserialize
           *    with ctx which itself tries to use this deserializer
           *    To change this, we need Yasson or JSON-B to update how they implement things
           *    There are a number of open GitHub tickets about this
           *        https://github.com/eclipse-ee4j/yasson/issues/133
           *        https://github.com/eclipse-ee4j/yasson/issues/279
           *        https://github.com/eclipse-ee4j/jsonb-api/issues/147
           */
          desc =
              jb.fromJson(
                  parser.getObject().toString(),
                  Class.forName(className).asSubclass(Annot8ComponentDescriptor.class));
          /* This is the correct way to do the above according to the JSON-B documentation,
           * but fails with the reference implementation (see above)
           *
           * desc =
           * ctx.deserialize(Class.forName(className).asSubclass(Annot8ComponentDescriptor.class),
           * parser);
           */
        } catch (ClassNotFoundException e) {
          throw new JsonbException("Deserialization failed - could not find class " + className, e);
        }
      }
    }
    return desc;
  }
}
