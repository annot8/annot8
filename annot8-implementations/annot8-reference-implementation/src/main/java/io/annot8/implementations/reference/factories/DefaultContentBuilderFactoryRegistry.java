/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.reference.factories;

import io.annot8.common.data.content.FileContent;
import io.annot8.common.data.content.Image;
import io.annot8.common.data.content.InputStreamContent;
import io.annot8.common.data.content.TableContent;
import io.annot8.common.data.content.Text;
import io.annot8.common.data.content.UriContent;
import io.annot8.implementations.reference.content.DefaultFile;
import io.annot8.implementations.reference.content.DefaultImage;
import io.annot8.implementations.reference.content.DefaultInputStream;
import io.annot8.implementations.reference.content.DefaultTableContent;
import io.annot8.implementations.reference.content.DefaultText;
import io.annot8.implementations.reference.content.DefaultUri;
import io.annot8.implementations.support.registries.SimpleContentBuilderFactoryRegistry;

public class DefaultContentBuilderFactoryRegistry extends SimpleContentBuilderFactoryRegistry {

  public DefaultContentBuilderFactoryRegistry() {
    this(true);
  }

  public DefaultContentBuilderFactoryRegistry(boolean includeDefaultContentBuilders) {

    if (includeDefaultContentBuilders) {
      register(Text.class, new DefaultText.BuilderFactory());
      register(FileContent.class, new DefaultFile.BuilderFactory());
      register(InputStreamContent.class, new DefaultInputStream.BuilderFactory());
      register(TableContent.class, new DefaultTableContent.BuilderFactory());
      register(Image.class, new DefaultImage.BuilderFactory());
      register(UriContent.class, new DefaultUri.BuilderFactory());
    }
  }
}
