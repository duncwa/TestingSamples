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

### android generate_rls_apk

```sh
[bundle exec] fastlane android generate_rls_apk
```

Run Release Build for TestingSamples Android App

### android generate_dev_apk

```sh
[bundle exec] fastlane android generate_dev_apk
```



### android test_aos_pra

```sh
[bundle exec] fastlane android test_aos_pra
```

Run unit tests for TestingSamples Android App

### android test_aos_qe

```sh
[bundle exec] fastlane android test_aos_qe
```

Run emulator instrumentation tests for TestingSamples Android App

### android test_aos_qe_jira

```sh
[bundle exec] fastlane android test_aos_qe_jira
```

Run Unit Tests and UI Tests for TestingSamples and upload results to JIRA

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

### android run_aos_ins

```sh
[bundle exec] fastlane android run_aos_ins
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

### android slack_test

```sh
[bundle exec] fastlane android slack_test
```

Run Slack Testing

### android slack_test_local

```sh
[bundle exec] fastlane android slack_test_local
```

Run Slack Testing

### android post_pra_slack_message

```sh
[bundle exec] fastlane android post_pra_slack_message
```

Run Slack reporting for PRA

### android post_dev_slack_message

```sh
[bundle exec] fastlane android post_dev_slack_message
```

Run Slack reporting for DEV

### android post_rls_slack_message

```sh
[bundle exec] fastlane android post_rls_slack_message
```

Run Slack reporting for RLS

### android post_qe_aos_slack_message

```sh
[bundle exec] fastlane android post_qe_aos_slack_message
```

Run Slack reporting for QE

----

This README.md is auto-generated and will be re-generated every time [_fastlane_](https://fastlane.tools) is run.

More information about _fastlane_ can be found on [fastlane.tools](https://fastlane.tools).

The documentation of _fastlane_ can be found on [docs.fastlane.tools](https://docs.fastlane.tools).
