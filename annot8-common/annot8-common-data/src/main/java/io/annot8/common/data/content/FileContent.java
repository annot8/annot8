/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.data.content;

import io.annot8.api.data.Content;
import java.io.File;

/**
 * Content which is backed by a file.
 *
 * <p>In order to access this file you will need to have permissions, be on same machine, etc. As
 * such whilst files are easy to handle they aren't very useful in many cases.
 *
 * <p>We recommend starting reading files early in the pipeline, but moving to {@link
 * InputStreamContent}.
 */
public interface FileContent extends Content<File> {}
