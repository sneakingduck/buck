/*
 * Copyright 2014-present Facebook, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.facebook.buck.android;

import static com.facebook.buck.jvm.java.JavaCompilationConstants.ANDROID_JAVAC_OPTIONS;
import static com.facebook.buck.jvm.java.JavaCompilationConstants.DEFAULT_JAVA_CONFIG;
import static com.facebook.buck.jvm.java.JavaCompilationConstants.DEFAULT_JAVA_OPTIONS;

import com.facebook.buck.config.FakeBuckConfig;
import com.facebook.buck.io.filesystem.ProjectFilesystem;
import com.facebook.buck.jvm.java.JavaBuckConfig;
import com.facebook.buck.jvm.kotlin.KotlinBuckConfig;
import com.facebook.buck.jvm.scala.ScalaBuckConfig;
import com.facebook.buck.model.BuildTarget;
import com.facebook.buck.rules.AbstractNodeBuilder;
import com.facebook.buck.toolchain.impl.TestToolchainProvider;
import java.util.Optional;

public class RobolectricTestBuilder
    extends AbstractNodeBuilder<
        RobolectricTestDescriptionArg.Builder, RobolectricTestDescriptionArg,
        RobolectricTestDescription, RobolectricTest> {

  public static final AndroidLibraryCompilerFactory DEFAULT_ANDROID_COMPILER_FACTORY =
      new DefaultAndroidLibraryCompilerFactory(
          new TestToolchainProvider(),
          DEFAULT_JAVA_CONFIG,
          new ScalaBuckConfig(FakeBuckConfig.builder().build()),
          new KotlinBuckConfig(FakeBuckConfig.builder().build()));

  private RobolectricTestBuilder(BuildTarget target, JavaBuckConfig javaBuckConfig) {
    super(
        new RobolectricTestDescription(
            new TestToolchainProvider(),
            javaBuckConfig,
            DEFAULT_JAVA_OPTIONS,
            ANDROID_JAVAC_OPTIONS,
            /* testRuleTimeoutMs */ Optional.empty(),
            null,
            DEFAULT_ANDROID_COMPILER_FACTORY),
        target);
  }

  private RobolectricTestBuilder(BuildTarget target, ProjectFilesystem filesystem) {
    super(
        new RobolectricTestDescription(
            new TestToolchainProvider(),
            DEFAULT_JAVA_CONFIG,
            DEFAULT_JAVA_OPTIONS,
            ANDROID_JAVAC_OPTIONS,
            /* testRuleTimeoutMs */ Optional.empty(),
            null,
            DEFAULT_ANDROID_COMPILER_FACTORY),
        target,
        filesystem);
  }

  private RobolectricTestBuilder(
      BuildTarget target, ProjectFilesystem filesystem, JavaBuckConfig javaBuckConfig) {
    super(
        new RobolectricTestDescription(
            new TestToolchainProvider(),
            javaBuckConfig,
            DEFAULT_JAVA_OPTIONS,
            ANDROID_JAVAC_OPTIONS,
            /* testRuleTimeoutMs */ Optional.empty(),
            null,
            DEFAULT_ANDROID_COMPILER_FACTORY),
        target,
        filesystem);
  }

  public static RobolectricTestBuilder createBuilder(BuildTarget target) {
    return new RobolectricTestBuilder(target, DEFAULT_JAVA_CONFIG);
  }

  public static RobolectricTestBuilder createBuilder(
      BuildTarget target, JavaBuckConfig javaBuckConfig) {
    return new RobolectricTestBuilder(target, javaBuckConfig);
  }

  public static RobolectricTestBuilder createBuilder(
      BuildTarget target, ProjectFilesystem filesystem) {
    return new RobolectricTestBuilder(target, filesystem);
  }

  public static RobolectricTestBuilder createBuilder(
      BuildTarget target, ProjectFilesystem filesystem, JavaBuckConfig javaBuckConfig) {
    return new RobolectricTestBuilder(target, filesystem, javaBuckConfig);
  }

  public RobolectricTestBuilder addDep(BuildTarget rule) {
    getArgForPopulating().addDeps(rule);
    return this;
  }

  public RobolectricTestBuilder addProvidedDep(BuildTarget rule) {
    getArgForPopulating().addProvidedDeps(rule);
    return this;
  }
}
