fastlane documentation
----

# Installation

Make sure you have the latest version of the Xcode command line tools installed:

```sh
xcode-select --install
```

For _fastlane_ installation instructions, see [Installing _fastlane_](https://docs.fastlane.tools/#installing-fastlane)

# Available Actions

## Android

### android appcenter_upload_rls

```sh
[bundle exec] fastlane android appcenter_upload_rls
```

Upload RLS apk and mapping results to AppCenter

### android appcenter_upload_dev

```sh
[bundle exec] fastlane android appcenter_upload_dev
```

Upload DEV apk and mapping results to AppCenter

### android generate_dev_apk

```sh
[bundle exec] fastlane android generate_dev_apk
```

Run Develop Build for TestingSamples Android App

### android generate_rls_apk

```sh
[bundle exec] fastlane android generate_rls_apk
```

Run Release Build for TestingSamples Android App

### android upload_rls_apk

```sh
[bundle exec] fastlane android upload_rls_apk
```

Upload AAB files to Google Play Store

### android github_upload_rls

```sh
[bundle exec] fastlane android github_upload_rls
```

Upload apk results to AppCenter

### android test_aos_coverage

```sh
[bundle exec] fastlane android test_aos_coverage
```

Run linter for TestingSamples Android App

### android test_aos_lint

```sh
[bundle exec] fastlane android test_aos_lint
```

Run linter for TestingSamples Android App

### android test_aos_pra

```sh
[bundle exec] fastlane android test_aos_pra
```

Run unit tests for TestingSamples Android App

### android test_aos_qe_jira

```sh
[bundle exec] fastlane android test_aos_qe_jira
```

Run Unit Tests and UI Tests for TestingSamples and upload results to JIRA

### android test_aos_qe_basic

```sh
[bundle exec] fastlane android test_aos_qe_basic
```

Run unit tests for BasicSample Android App

### android test_aos_qe_instrumentation

```sh
[bundle exec] fastlane android test_aos_qe_instrumentation
```

Run emulator instrumentation tests for TestingSamples Android App

### android test_aos_qe_varies

```sh
[bundle exec] fastlane android test_aos_qe_varies
```

Run unit tests for any TestingSamples Android App

### android setup_emulator

```sh
[bundle exec] fastlane android setup_emulator
```

Startup the emulator

### android shutdown_emulator

```sh
[bundle exec] fastlane android shutdown_emulator
```

Shutdown the emulator

### android bex_aos_ins

```sh
[bundle exec] fastlane android bex_aos_ins
```

Perform Inspection of Artifacts using Bulkextractor

### android build_for_screengrab

```sh
[bundle exec] fastlane android build_for_screengrab
```

Build debug and test APK for screenshots

### android aos_danger

```sh
[bundle exec] fastlane android aos_danger
```

Run Danger for PRA Commit to Repo Comments

### android aos_danger_mbp

```sh
[bundle exec] fastlane android aos_danger_mbp
```

Run Danger for MBP PRA Commit to Repo Comments

### android post_dev_slack_message

```sh
[bundle exec] fastlane android post_dev_slack_message
```

Run Slack reporting for DEV

### android post_pra_slack_message

```sh
[bundle exec] fastlane android post_pra_slack_message
```

Run Slack reporting for PRA

### android post_qe_aos_con_slack_message

```sh
[bundle exec] fastlane android post_qe_aos_con_slack_message
```

Run Slack reporting for QE Connected

### android post_qe_aos_slack_message

```sh
[bundle exec] fastlane android post_qe_aos_slack_message
```

Run Slack reporting for QE TestDebug

### android post_rls_slack_message

```sh
[bundle exec] fastlane android post_rls_slack_message
```

Run Slack reporting for RLS

### android slack_test

```sh
[bundle exec] fastlane android slack_test
```

Run Slack Testing

----

This README.md is auto-generated and will be re-generated every time [_fastlane_](https://fastlane.tools) is run.

More information about _fastlane_ can be found on [fastlane.tools](https://fastlane.tools).

The documentation of _fastlane_ can be found on [docs.fastlane.tools](https://docs.fastlane.tools).
