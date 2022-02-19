#!/usr/bin/env groovy

//
//  Jenkinsfile-aos-ins.groovy
//  TestingSamples
//  Created by Duncan Wallace 02/19/2022
//  Copyright © 2022. Duncwa LLC.  All rights reserved

pipeline {
    agent { label "fastlane_ins" }

    options {
      ansiColor("xterm")
      timeout(time: 1, unit: "HOURS")
      buildDiscarder(logRotator(numToKeepStr: "5", artifactNumToKeepStr: "5"))
    }

    environment {
      BUILD_NUM = "${env.BUILD_ID}"
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
              sh 'printenv'
          }
      }
      stage('Inspect for Sensitive Data') {
          steps {
            echo 'Run Inspection'
            sh 'bundle exec fastlane run_aos_ins'
          }
          post {
            always { stash includes: "fastlane/build/**/*", name: "run_ios_ins", allowEmpty: true }
          }
      }
    }

    post {
      always {
        script {
          try { unstash "run_ios_ins" }  catch (e) { echo "Failed to unstash stash: " + e.toString() }
        }
        archiveArtifacts artifacts: "fastlane/build/*dSYM.zip", fingerprint: true
        archiveArtifacts artifacts: "fastlane/build/*.ipa", fingerprint: true
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
