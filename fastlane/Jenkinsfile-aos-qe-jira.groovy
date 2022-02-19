#!/usr/bin/env groovy

//
//  Jenkinsfile-android-pra.groovy
//  BasicSample
//  Created by Duncan Wallace 09/27/2020
//  Copyright © 2020. Duncwa LLC.  All rights reserved

pipeline {
    agent { label "fastlane_pra" }

    options {
      ansiColor("xterm")
      timeout(time: 1, unit: "HOURS")
      buildDiscarder(logRotator(numToKeepStr: "10", artifactNumToKeepStr: "5"))
    }

    environment {
      JIRA_CREDS = credentials("s.jiraxray")
      JIRA_SERVER_USERNAME = "${env.DANGER_CREDS_USR}"
      JIRA_SERVER_PASSWORD = "${env.DANGER_CREDS_PSW}"
      BUILD_NUM = "${env.BUILD_ID}"
      SLACK_CHANNEL = "${env.SLACK_CHANNEL}"
    }

    stages {
      stage('Setup') {
          steps {
              echo 'Install Bundle Ruby Gems'
              sh 'bundle install'
              sh 'pwd'
              sh 'rvm list'
              sh 'printenv'
          }
      }
      stage('instrumentation Tests') {
          steps {
              echo 'Test QE'
              sh 'bundle exec fastlane test_aos_qe_jira'
          }
          post {
            always { stash includes: "*/build/**/*", name: "test_aos_qe_jira", allowEmpty: true }
          }
      }
    }

    post {
      always {
        script {
          try { unstash "test_aos_qe_jira" }  catch (e) { echo "Failed to unstash stash: " + e.toString() }
        }
        archiveArtifacts artifacts: "*/build/**/*", fingerprint: true
      }

      success {
        sh "echo 'Build Successful' "
        sh "bundle exec fastlane post_qe_slack_message run_time:${currentBuild.duration / 1000} status:${currentBuild.result}"
      }

      unstable {
        sh "echo 'Build Unstable' "
        sh "bundle exec fastlane post_qe_slack_message run_time:${currentBuild.duration / 1000} status:${currentBuild.result}"
      }

      failure {
        sh "echo 'Build Failed' "
        sh "bundle exec fastlane post_qe_slack_message run_time:${currentBuild.duration / 1000} status:${currentBuild.result}"
      }

    }
}
