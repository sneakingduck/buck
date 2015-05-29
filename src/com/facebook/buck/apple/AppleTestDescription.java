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

package com.facebook.buck.apple;

import com.facebook.buck.cxx.CxxCompilationDatabase;
import com.facebook.buck.cxx.CxxDescriptionEnhancer;
import com.facebook.buck.cxx.CxxPlatform;
import com.facebook.buck.cxx.Linker;
import com.facebook.buck.log.Logger;
import com.facebook.buck.model.BuildTarget;
import com.facebook.buck.model.Flavor;
import com.facebook.buck.model.FlavorDomain;
import com.facebook.buck.model.FlavorDomainException;
import com.facebook.buck.model.Flavored;
import com.facebook.buck.model.ImmutableFlavor;
import com.facebook.buck.rules.BuildRule;
import com.facebook.buck.rules.BuildRuleParams;
import com.facebook.buck.rules.BuildRuleResolver;
import com.facebook.buck.rules.BuildRuleType;
import com.facebook.buck.rules.BuildRules;
import com.facebook.buck.rules.BuildTargetSourcePath;
import com.facebook.buck.rules.Description;
import com.facebook.buck.rules.Label;
import com.facebook.buck.rules.SourcePath;
import com.facebook.buck.rules.SourcePathResolver;
import com.facebook.buck.rules.TargetNode;
import com.facebook.buck.rules.coercer.Either;
import com.facebook.buck.util.HumanReadableException;
import com.facebook.infer.annotation.SuppressFieldNotInitialized;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Suppliers;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Sets;

import java.nio.file.Path;

import java.util.Map;
import java.util.Set;

public class AppleTestDescription implements Description<AppleTestDescription.Arg>, Flavored {

  public static final BuildRuleType TYPE = BuildRuleType.of("apple_test");

  private static final Logger LOG = Logger.get(AppleTestDescription.class);

  /**
   * Flavors for the additional generated build rules.
   */
  private static final Flavor LIBRARY_FLAVOR = ImmutableFlavor.of("apple-test-library");
  private static final Flavor BUNDLE_FLAVOR = ImmutableFlavor.of("apple-test-bundle");

  private static final Set<Flavor> SUPPORTED_FLAVORS = ImmutableSet.of(
      LIBRARY_FLAVOR, BUNDLE_FLAVOR);

  private static final Predicate<Flavor> IS_SUPPORTED_FLAVOR = Predicates.in(SUPPORTED_FLAVORS);

  private static final Set<Flavor> NON_LIBRARY_FLAVORS = ImmutableSet.of(
      CxxCompilationDatabase.COMPILATION_DATABASE,
      CxxDescriptionEnhancer.HEADER_SYMLINK_TREE_FLAVOR,
      CxxDescriptionEnhancer.EXPORTED_HEADER_SYMLINK_TREE_FLAVOR);

  private final AppleConfig appleConfig;
  private final AppleBundleDescription appleBundleDescription;
  private final AppleLibraryDescription appleLibraryDescription;
  private final FlavorDomain<CxxPlatform> cxxPlatformFlavorDomain;
  private final ImmutableMap<Flavor, AppleCxxPlatform> platformFlavorsToAppleCxxPlatforms;
  private final CxxPlatform defaultCxxPlatform;

  public AppleTestDescription(
      AppleConfig appleConfig,
      AppleBundleDescription appleBundleDescription,
      AppleLibraryDescription appleLibraryDescription,
      FlavorDomain<CxxPlatform> cxxPlatformFlavorDomain,
      Map<Flavor, AppleCxxPlatform> platformFlavorsToAppleCxxPlatforms,
      CxxPlatform defaultCxxPlatform) {
    this.appleConfig = appleConfig;
    this.appleBundleDescription = appleBundleDescription;
    this.appleLibraryDescription = appleLibraryDescription;
    this.cxxPlatformFlavorDomain = cxxPlatformFlavorDomain;
    this.platformFlavorsToAppleCxxPlatforms =
        ImmutableMap.copyOf(platformFlavorsToAppleCxxPlatforms);
    this.defaultCxxPlatform = defaultCxxPlatform;
  }

  @Override
  public BuildRuleType getBuildRuleType() {
    return TYPE;
  }

  @Override
  public Arg createUnpopulatedConstructorArg() {
    return new Arg();
  }

  @Override
  public boolean hasFlavors(ImmutableSet<Flavor> flavors) {
    return FluentIterable.from(flavors).allMatch(IS_SUPPORTED_FLAVOR) ||
        appleLibraryDescription.hasFlavors(flavors);
  }

  @Override
  public <A extends Arg> BuildRule createBuildRule(
      BuildRuleParams params,
      BuildRuleResolver resolver,
      A args) {
    String extension = args.extension.isLeft() ?
        args.extension.getLeft().toFileExtension() :
        args.extension.getRight();
    if (!AppleBundleExtensions.VALID_XCTOOL_BUNDLE_EXTENSIONS.contains(extension)) {
      throw new HumanReadableException(
          "Invalid bundle extension for apple_test rule: %s (must be one of %s)",
          extension,
          AppleBundleExtensions.VALID_XCTOOL_BUNDLE_EXTENSIONS);
    }
    boolean createBundle = Sets.intersection(
        params.getBuildTarget().getFlavors(),
        NON_LIBRARY_FLAVORS).isEmpty();
    Sets.SetView<Flavor> nonLibraryFlavors = Sets.difference(
        params.getBuildTarget().getFlavors(),
        NON_LIBRARY_FLAVORS);
    boolean addDefaultPlatform = nonLibraryFlavors.isEmpty();
    ImmutableSet.Builder<Flavor> extraFlavorsBuilder = ImmutableSet.builder();
    if (createBundle) {
      extraFlavorsBuilder.add(
          LIBRARY_FLAVOR,
          CxxDescriptionEnhancer.MACH_O_BUNDLE_FLAVOR);
    }
    if (addDefaultPlatform) {
      extraFlavorsBuilder.add(defaultCxxPlatform.getFlavor());
    }

    Optional<AppleBundle> testHostApp;
    Optional<SourcePath> testHostAppBinarySourcePath;
    if (args.testHostApp.isPresent()) {
      TargetNode<?> testHostAppNode = params.getTargetGraph().get(args.testHostApp.get());
      Preconditions.checkNotNull(testHostAppNode);

      if (testHostAppNode.getType() != AppleBundleDescription.TYPE) {
        throw new HumanReadableException(
            "Apple test rule %s has unrecognized test_host_app %s type %s (should be %s)",
            params.getBuildTarget(),
            args.testHostApp.get(),
            testHostAppNode.getType(),
            AppleBundleDescription.TYPE);
      }

      AppleBundleDescription.Arg testHostAppDescription = (AppleBundleDescription.Arg)
          testHostAppNode.getConstructorArg();

      testHostApp = Optional.of(
          appleBundleDescription.createBuildRule(
              params.copyWithChanges(
                  AppleBundleDescription.TYPE,
                  BuildTarget.builder(args.testHostApp.get())
                      .addAllFlavors(nonLibraryFlavors)
                      .build(),
                  Suppliers.ofInstance(
                      BuildRules.toBuildRulesFor(
                          args.testHostApp.get(),
                          resolver,
                          testHostAppNode.getDeclaredDeps())),
                  Suppliers.ofInstance(
                      BuildRules.toBuildRulesFor(
                          args.testHostApp.get(),
                          resolver,
                          testHostAppNode.getExtraDeps()))),
              resolver,
              testHostAppDescription));
      testHostAppBinarySourcePath = Optional.<SourcePath>of(
          new BuildTargetSourcePath(
              params.getProjectFilesystem(),
              testHostAppDescription.binary));
    } else {
      testHostApp = Optional.absent();
      testHostAppBinarySourcePath = Optional.absent();
    }

    BuildRule library = appleLibraryDescription.createBuildRule(
        params.copyWithChanges(
            AppleLibraryDescription.TYPE,
            BuildTarget.builder(params.getBuildTarget())
                .addAllFlavors(extraFlavorsBuilder.build())
                .build(),
            Suppliers.ofInstance(params.getDeclaredDeps()),
            Suppliers.ofInstance(params.getExtraDeps())),
        resolver,
        args,
        // For now, instead of building all deps as dylibs and fixing up their install_names,
        // we'll just link them statically.
        Optional.of(Linker.LinkableDepType.STATIC),
        testHostAppBinarySourcePath);
    if (!createBundle) {
      return library;
    }

    CxxPlatform cxxPlatform;
    try {
      cxxPlatform = cxxPlatformFlavorDomain
          .getValue(params.getBuildTarget().getFlavors())
          .or(defaultCxxPlatform);
    } catch (FlavorDomainException e) {
      throw new HumanReadableException(e, "%s: %s", params.getBuildTarget(), e.getMessage());
    }
    AppleCxxPlatform appleCxxPlatform =
        platformFlavorsToAppleCxxPlatforms.get(cxxPlatform.getFlavor());
    if (appleCxxPlatform == null) {
      throw new HumanReadableException(
          "%s: Apple test requires an Apple platform, found '%s'",
          params.getBuildTarget(),
          cxxPlatform.getFlavor().getName());
    }

    AppleBundleDestinations destinations =
        AppleBundleDestinations.platformDestinations(
            appleCxxPlatform.getAppleSdk().getApplePlatform());

    SourcePathResolver sourcePathResolver = new SourcePathResolver(resolver);
    ImmutableSet<AppleResourceDescription.Arg> resourceDescriptions =
        AppleResources.collectRecursiveResources(
            params.getTargetGraph(),
            ImmutableSet.of(params.getTargetGraph().get(params.getBuildTarget())));
    LOG.debug("Got resource nodes %s", resourceDescriptions);
    ImmutableSet.Builder<Path> resourceDirsBuilder = ImmutableSet.builder();
    AppleResources.addResourceDirsToBuilder(resourceDirsBuilder, resourceDescriptions);
    ImmutableSet<Path> resourceDirs = resourceDirsBuilder.build();

    ImmutableSet.Builder<SourcePath> resourceFilesBuilder = ImmutableSet.builder();
    AppleResources.addResourceFilesToBuilder(resourceFilesBuilder, resourceDescriptions);
    ImmutableSet<SourcePath> resourceFiles = resourceFilesBuilder.build();

    CollectedAssetCatalogs collectedAssetCatalogs =
        AppleDescriptions.createBuildRulesForTransitiveAssetCatalogDependencies(
            params,
            sourcePathResolver,
            appleCxxPlatform.getAppleSdk().getApplePlatform(),
            appleCxxPlatform.getActool());

    Optional<AppleAssetCatalog> mergedAssetCatalog = collectedAssetCatalogs.getMergedAssetCatalog();
    ImmutableSet<AppleAssetCatalog> bundledAssetCatalogs =
        collectedAssetCatalogs.getBundledAssetCatalogs();

    AppleBundle bundle = new AppleBundle(
        params.copyWithChanges(
            AppleBundleDescription.TYPE,
            BuildTarget.builder(params.getBuildTarget()).addFlavors(BUNDLE_FLAVOR).build(),
            // We have to add back the original deps here, since they're likely
            // stripped from the library link above (it doesn't actually depend on them).
            Suppliers.ofInstance(
                ImmutableSortedSet.<BuildRule>naturalOrder()
                    .add(library)
                    .addAll(mergedAssetCatalog.asSet())
                    .addAll(bundledAssetCatalogs)
                    .addAll(params.getDeclaredDeps())
                    .build()),
            Suppliers.ofInstance(params.getExtraDeps())),
        sourcePathResolver,
        args.extension,
        args.infoPlist,
        args.infoPlistSubstitutions.get(),
        Optional.of(library),
        destinations,
        resourceDirs,
        resourceFiles,
        appleCxxPlatform.getIbtool(),
        bundledAssetCatalogs,
        mergedAssetCatalog,
        ImmutableSortedSet.<BuildTarget>of());

    String platformName = appleCxxPlatform.getAppleSdk().getApplePlatform().getName();
    return new AppleTest(
        appleConfig.getXctoolPath(),
        appleCxxPlatform.getXctest(),
        appleCxxPlatform.getOtest(),
        appleConfig.getXctestPlatformNames().contains(platformName),
        platformName,
        Optional.<String>absent(),
        params.copyWithDeps(
            Suppliers.ofInstance(ImmutableSortedSet.<BuildRule>of(bundle)),
            Suppliers.ofInstance(ImmutableSortedSet.<BuildRule>of())),
        sourcePathResolver,
        bundle,
        testHostApp,
        extension,
        args.contacts.get(),
        args.labels.get());
  }

  @SuppressFieldNotInitialized
  public static class Arg extends AppleNativeTargetDescriptionArg implements HasAppleBundleFields {
    public Optional<ImmutableSortedSet<String>> contacts;
    public Optional<ImmutableSortedSet<Label>> labels;
    public Optional<Boolean> canGroup;
    public Optional<BuildTarget> testHostApp;

    // Bundle related fields.
    public Either<AppleBundleExtension, String> extension;
    public Optional<SourcePath> infoPlist;
    public Optional<ImmutableMap<String, String>> infoPlistSubstitutions;
    public Optional<String> xcodeProductType;
    public Optional<String> resourcePrefixDir;

    @Override
    public Either<AppleBundleExtension, String> getExtension() {
      return extension;
    }

    @Override
    public Optional<SourcePath> getInfoPlist() {
      return infoPlist;
    }

    @Override
    public Optional<String> getXcodeProductType() {
      return xcodeProductType;
    }

    public boolean canGroup() {
      return canGroup.or(false);
    }
  }
}
