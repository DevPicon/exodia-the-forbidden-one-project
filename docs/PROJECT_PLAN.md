# Project Plan

This file is the single source of truth for delivery status in **Jetpack Compose Custom Drawing Samples**. Process and completion rules are defined in [`GOVERNANCE.md`](../GOVERNANCE.md).

## Status summary

| ID | Priority | Status | Story | Completion evidence |
|---|---|---|---|---|
| PROD-001 | P0 | COMPLETED | Align the product identity and public documentation | `c200507` |
| DRAW-004 | P0 | COMPLETED | Make the stat-comparison sample discoverable | `c6321c6` |
| DRAW-005 | P0 | COMPLETED | Add the scratch-card overlay sample | `de7aca4` |
| DRAW-003 | P1 | COMPLETED | Make the workout timer behavior reliable | `0dd4d0f` |
| QUALITY-002 | P1 | IN_PROGRESS | Enforce local Kotlin quality checks before commit | — |
| QUALITY-001 | P1 | READY | Enforce build, tests, and lint in CI | — |
| UX-001 | P1 | BACKLOG | Make samples adaptive across screen configurations | — |
| A11Y-001 | P1 | BACKLOG | Add meaningful semantics to Canvas samples | — |
| MAINT-001 | P2 | BACKLOG | Modernize tooling and remove obsolete resources | — |

## Existing sample inventory

This inventory describes the current repository; it does not override story status or the definition of done.

| Sample | Current state | Known gap |
|---|---|---|
| Basic Canvas | Reachable | Focused UI tests and Canvas semantics remain pending. |
| Doughnut Chart | Reachable and interactive | Fixed sizing and Canvas accessibility require follow-up. |
| Workout Timer | Reachable, interactive, and lifecycle-safe | Canvas semantics remain pending. |
| Stat Comparison | Reachable and interactive | Canvas semantics remain pending. |
| Scratch Card | Reachable and verified on device | Completed by DRAW-005. |

## Stories and tasks

### PROD-001 — Align the product identity and public documentation

- **Priority:** P0
- **Status:** COMPLETED
- **Implementation commit:** `c200507`

#### Definition

As a developer exploring Jetpack Compose graphics, I want the app and repository to identify themselves as **Jetpack Compose Custom Drawing Samples** so that their purpose and boundaries are immediately clear.

#### Scope

- Update the visible app name and relevant titles.
- Rewrite the README around the sample-gallery purpose.
- Remove claims about free drawing, authentication, persistence, and other unsupported product capabilities.
- Link the README to governance and this project plan.

#### Acceptance criteria

- Product naming is consistent in the app, README, and governance.
- README describes only behavior present in the repository.
- Out-of-scope capabilities are not presented as planned product features.
- Existing package/application identifiers remain unchanged unless separately approved.

#### Test cases

1. Build the debug application and verify the launcher/app title.
2. Review README claims against every reachable sample.
3. Verify English and Spanish app names where localized.

#### Completion evidence

- Unit tests, lint, debug assembly, and Android-test compilation passed.
- APK inspection confirmed the product label for default and Spanish resources.
- APK inspection confirmed that `la.devpicon.android.mydrawingsapplication` remained unchanged.
- Public documentation and product identity were committed in `c200507`.

### DRAW-004 — Make the stat-comparison sample discoverable

- **Priority:** P0
- **Status:** COMPLETED
- **Implementation commit:** `c6321c6`

#### Definition

As a developer using the sample catalog, I want to open the stat-comparison visualization from Home so that no implemented sample is hidden behind an inert action.

#### Scope

- Add a dedicated screen and navigation route for `StatComparison`.
- Replace the inert Home action with real navigation.
- Add localized labels, a preview, and controls that demonstrate different values.
- Define valid input behavior for zero and negative totals.

#### Acceptance criteria

- The Home action opens the stat-comparison screen.
- Back navigation returns to Home.
- Valid values render without invalid coordinates.
- Invalid values are rejected or represented by a documented fallback.
- The sample has focused navigation and rendering-contract tests.

#### Test cases

1. Select the sample from Home and verify its screen title.
2. Change both team values and verify the visualization updates.
3. Exercise zero-total and invalid-value behavior.
4. Navigate back to Home.

#### Completion evidence

- The Home action, localized screen title, value controls, and back navigation are available.
- Five unit tests passed, including proportion, zero-total, overflow, and negative-input coverage.
- All eight instrumented tests passed on the Android 16 `Medium_Phone_API_36.1` emulator.
- `lintDebug`, `assembleDebug`, and Android-test compilation passed.
- Espresso was updated to `3.7.0` to support input injection on Android 16.
- The implementation and tests were committed in `c6321c6`.

### DRAW-005 — Add the scratch-card overlay sample

- **Priority:** P0
- **Status:** COMPLETED
- **Implementation commit:** `de7aca4`

#### Definition

As a developer learning Jetpack Compose custom drawing, I want a scratch-card sample where a touch gesture removes an opaque overlay so that I can understand how to reveal another composable underneath it.

#### Delivered scope

- Reusable `ScratchCard` composable with a content slot.
- Isolated offscreen overlay using `BlendMode.Clear`.
- Rounded scratch paths driven by observable gesture state.
- Demonstration screen, reset action, navigation, localization, preview, and focused Compose UI tests.

#### Acceptance evidence

- Unit tests, Android-test compilation, lint, and debug assembly passed before commit.
- The sample was installed and visually verified on an authorized Samsung device.
- Source and tests were committed in `de7aca4`.

### DRAW-003 — Make the workout timer behavior reliable

- **Priority:** P1
- **Status:** COMPLETED
- **Implementation commit:** `0dd4d0f`

#### Definition

As a developer studying time-based Compose drawing, I want the timer sample to implement start, pause, resume, reset, and completion consistently so that it demonstrates a correct state model.

#### Scope

- Define the timer state and action contract.
- Preserve meaningful state through configuration changes.
- Use a monotonic time source and a testable state holder.
- Keep rendering separate from timer orchestration.

#### Acceptance criteria

- Pause preserves elapsed and remaining time.
- Resume continues from the paused position.
- Reset restores time, progress, and steps consistently.
- Rotation does not silently restart an active or paused timer.
- Boundary values cannot crash the progress visualization.

#### Test cases

1. Start, pause, wait, and verify remaining time does not change.
2. Resume and verify countdown continuity.
3. Reset during active and paused states.
4. Recreate the screen and verify state restoration.
5. Exercise minimum steps and duration inputs.

#### Completion evidence

- A saveable, testable state holder uses monotonic elapsed time and preserves active or paused state.
- Pause, resume, reset, multi-step completion, restoration, and minimum boundaries are deterministic.
- All 11 unit tests passed, including six focused `WorkoutTimerState` tests.
- All 12 instrumented tests passed on the Android 16 `Medium_Phone_API_36.1` emulator, including four focused timer tests.
- `lintDebug`, `assembleDebug`, and Android-test compilation passed.
- A later optional `installDebug` attempt was blocked by insufficient AVD storage; connected test execution had already passed.
- The implementation and tests were committed in `0dd4d0f`.

### QUALITY-002 — Enforce local Kotlin quality checks before commit

- **Priority:** P1
- **Status:** IN_PROGRESS

#### Definition

As a contributor, I want Detekt and ktlint to run through a versioned Lefthook pre-commit hook so that Kotlin quality regressions are rejected before a commit is created.

#### Scope

- Add reproducible Gradle tasks for Detekt and ktlint checks.
- Add a versioned `lefthook.yml` pre-commit configuration.
- Install Lefthook locally without committing generated `.git/hooks` files.
- Document hook installation, manual checks, and formatting commands.
- Preserve existing source debt through explicit baselines if correcting it would broaden this story.

#### Acceptance criteria

- `./gradlew detekt ktlintCheck` succeeds on the accepted baseline.
- `lefthook run pre-commit` executes both checks and succeeds on compliant code.
- A deliberate Kotlin violation makes the pre-commit hook fail.
- The hook never modifies staged files automatically.
- A fresh clone has documented commands to install and run the hook.

#### Test cases

1. Run Detekt and ktlint directly through Gradle.
2. Install the hook and inspect the generated `pre-commit` entry.
3. Run the pre-commit hook against the current repository.
4. Introduce a temporary violation, verify rejection, and remove the probe.
5. Run the default project verification set after integrating the plugins.

### QUALITY-001 — Enforce build, tests, and lint in CI

- **Priority:** P1
- **Status:** READY

#### Definition

As a maintainer, I want every proposed change to compile and run automated quality checks so that broken samples do not reach `stable`.

#### Scope

- Run unit tests, lint, and debug assembly in GitHub Actions.
- Trigger checks for pull requests and pushes to `stable`.
- Keep the debug APK artifact.
- Ensure step names describe the commands they actually run.

#### Acceptance criteria

- CI fails when unit tests, lint, or compilation fail.
- Pull requests receive the same checks as `stable` pushes.
- The APK artifact is uploaded only after successful verification.

#### Test cases

1. Run the workflow successfully on the current baseline.
2. Confirm a deliberately failing test causes the job to fail before merging that test.
3. Confirm the debug APK artifact is available on success.

### UX-001 — Make samples adaptive across screen configurations

- **Priority:** P1
- **Status:** BACKLOG

#### Definition

As a developer evaluating samples, I want every screen to remain usable across compact displays, landscape, split screen, dark mode, and increased font scale.

#### Initial scope

- Audit fixed dimensions and clipping.
- Define responsive constraints for each sample.
- Add representative previews or screenshot coverage.

#### Initial test cases

1. Render each screen at compact width.
2. Render in landscape and dark mode.
3. Verify controls remain reachable with increased font scale.

### A11Y-001 — Add meaningful semantics to Canvas samples

- **Priority:** P1
- **Status:** BACKLOG

#### Definition

As a user of accessibility services, I want interactive and informative Canvas samples to expose their meaning and state so that they are not purely visual.

#### Initial scope

- Define semantics for charts, timer progress, scratch interaction, and custom drawings.
- Localize descriptions and state announcements.
- Add semantics-focused Compose tests.

#### Initial test cases

1. Inspect each interactive Canvas node through the semantics tree.
2. Verify changing values update their state descriptions.
3. Confirm actions have localized accessible names.

### MAINT-001 — Modernize tooling and remove obsolete resources

- **Priority:** P2
- **Status:** BACKLOG

#### Definition

As a maintainer, I want dependencies, SDK configuration, and resources to remain supportable without mixing maintenance changes into sample features.

#### Initial scope

- Plan staged dependency and target-SDK upgrades.
- Remove unused resources and tracked IDE-specific files where safe.
- Resolve lint warnings that are not covered by higher-priority stories.

#### Initial test cases

1. Run the full verification set after each upgrade group.
2. Compare lint results before and after cleanup.
3. Build and install the debug APK on a supported device.

## Completion log

| Date | Story | Implementation commit | Ledger update |
|---|---|---|---|
| 2026-08-06 | DRAW-005 | `de7aca4` | Recorded during governance consolidation. |
| 2026-08-06 | PROD-001 | `c200507` | Product identity and public documentation aligned. |
| 2026-08-06 | DRAW-004 | `c6321c6` | Stat-comparison sample verified and made discoverable. |
| 2026-08-06 | DRAW-003 | `0dd4d0f` | Workout timer behavior and restoration verified. |
