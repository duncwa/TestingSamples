#!/usr/bin/env groovy

//
//  Jenkinsfile-aos-dev.groovy
//  TestingSamples
//  Created by Duncan Wallace 02/19/2022
//  Copyright © 2022. Duncwa LLC.  All rights reserved

pipeline {
    agent { label "fastlane_pra" }

    options {
      ansiColor("xterm")
      timeout(time: 1, unit: "HOURS")
      buildDiscarder(logRotator(numToKeepStr: "5", artifactNumToKeepStr: "5"))
    }

    environment {
      APPCENTER_TOKEN_DLO = credentials('s.appcenterduncwa_full')
      BUILD_NUM = "${env.BUILD_ID}"
      SLACK_URL = credentials('s.slackwebhookurl')
      SLACK_CHANNEL = "${env.SLACK_CHANNEL}"
    }

    stages {
      stage('Setup') {
          steps {
              echo 'Install Bundle Ruby Gems'
              sh 'bundle install'
              sh 'pwd'
              sh 'echo $PATH'
              sh 'rvm list'
          }
      }
      stage('Build and Upload APK') {
          steps {
              echo 'Generate APK'
              sh 'bundle exec fastlane generate_dev_apk'
              sh 'bundle exec fastlane appcenter_upload_dev'
          }
          post {
            always { stash includes: "ui/espresso/BasicSample/app/build/**/*", name: "generate_dev_apk", allowEmpty: true }
          }
      }
    }

    post {
      always {
        script {
          try { unstash "generate_dev_apk" }  catch (e) { echo "Failed to unstash stash: " + e.toString() }
        }
        archiveArtifacts artifacts: "ui/espresso/BasicSample/app/build/**/*", fingerprint: true
      }

      success {
        sh "echo 'Build Successful' "
        sh "bundle exec fastlane post_dev_slack_message run_time:${currentBuild.duration / 1000} status:${currentBuild.result}"
      }

      unstable {
        sh "echo 'Build Unstable' "
        sh "bundle exec fastlane post_dev_slack_message run_time:${currentBuild.duration / 1000} status:${currentBuild.result}"

      }

      failure {
        sh "echo 'Build Failed' "
        sh "bundle exec fastlane post_dev_slack_message run_time:${currentBuild.duration / 1000} status:${currentBuild.result}"
      }
    }
}
