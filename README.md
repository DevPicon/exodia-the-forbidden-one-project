# Jetpack Compose Custom Drawing Samples

An Android sample gallery for experimenting with custom drawing, animation, gestures, and visualizations in Jetpack Compose.

The repository is a technical playground and reference application. Each sample isolates a drawing technique behind a small, navigable demonstration instead of building a general-purpose drawing product.

## Sample catalog

| Sample | What it demonstrates | Current state |
|---|---|---|
| Basic Canvas | `Canvas`, `drawRect`, and `drawText` with a fixed shape and label | Available |
| Doughnut Chart | Animated arcs, measured text, bitmap placement, and an interactive percentage slider | Available |
| Workout Timer | Lifecycle-safe countdown state with pause, resume, reset, and Canvas step progress | Available |
| Scratch Card | Gesture-driven paths and `BlendMode.Clear` on an isolated graphics layer | Available |
| Stat Comparison | Animated proportional lines, a neutral zero state, and interactive value controls | Available |

Delivery status, acceptance criteria, and pending work live exclusively in the [project plan](docs/PROJECT_PLAN.md).

## Project rules

- [GOVERNANCE.md](GOVERNANCE.md) defines the product boundaries, sample contract, Definition of Done, verification policy, and commit workflow.
- [docs/PROJECT_PLAN.md](docs/PROJECT_PLAN.md) is the source of truth for stories, tasks, priorities, status, and completion evidence.

Every new sample should be independently navigable, reusable, localized in English and Spanish, previewable, tested around its primary behavior, and documented with its relevant rendering constraints.

## Getting started

### Requirements

- Android Studio with Android SDK 34 installed.
- JDK 17.
- An Android device or emulator running API 24 or newer.

### Build and run

1. Clone the repository:

   ```shell
   git clone https://github.com/DevPicon/exodia-the-forbidden-one-project.git
   cd exodia-the-forbidden-one-project
   ```

2. Install Lefthook and activate the versioned pre-commit hook:

   ```shell
   brew install lefthook
   lefthook install
   ```

3. Open the project in Android Studio and run the `app` configuration, or build the debug APK from the terminal:

   ```shell
   ./gradlew assembleDebug
   ```

4. With an authorized Android device connected, install the debug build:

   ```shell
   ./gradlew installDebug
   ```

## Verification

Run the default project checks with:

```shell
./gradlew testDebugUnitTest lintDebug assembleDebug
./gradlew compileDebugAndroidTestKotlin
./gradlew detekt ktlintCheck
```

The pre-commit hook runs `detekt` and `ktlintCheck` without changing files. To apply ktlint formatting intentionally, run `./gradlew ktlintFormat`, review the changes, and commit them normally. You can execute the hook manually with `lefthook run pre-commit`.

### Versioned CI artifacts

The Android `versionName` is an intentional project value updated in `app/build.gradle.kts`. Local builds use a deterministic fallback `versionCode`; CI passes GitHub's workflow run number through `-PbuildNumber` so every published APK embeds its automatic build number.

Pull requests run the complete verification set without publishing. Successful pushes and manual workflow runs on `stable` upload a debug APK named `jetpack-compose-custom-drawing-samples-<version>-build-<number>`, retained by GitHub Actions for 30 days. These installable debug artifacts are development snapshots, not signed production releases.

When an authorized device or emulator is available, run the Compose UI tests with:

```shell
./gradlew connectedDebugAndroidTest
```

## Project structure

- `app/src/main/java/.../draw/`: reusable drawing and visualization composables.
- `app/src/main/java/.../composable/screen/`: navigable demonstration screens.
- `app/src/androidTest/`: Compose UI and Android integration tests.
- `docs/PROJECT_PLAN.md`: delivery ledger and completion evidence.

## Contributing

Before implementation:

1. Select or define a story in the project plan.
2. Confirm its scope, acceptance criteria, and test cases.
3. Move it to `IN_PROGRESS`.

A code story is marked `COMPLETED` only after its implementation has been committed and the ledger records that commit hash.

## Contact

For questions, contact Armando on Instagram at [@devpicon](https://instagram.com/devpicon).
