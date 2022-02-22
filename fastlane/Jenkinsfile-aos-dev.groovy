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
      ghprbPullId = "${env.PULL_REQ_NUM}"
      BUILD_NUM = "${env.BUILD_ID}"
      SLACK_URL = credentials("s.slackwebhookurl")
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
        sh "echo 'APK Successful' "
        slackSend channel: SLACK, message: "APK Generate Successful - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Link>)"
      }

      unstable {
        sh "echo 'APK Unsuccessful' "
        slackSend channel: SLACK,  message: "APK Generate Failed - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Link>)"

      }

      failure {
        sh "echo 'APK Failed' "
        slackSend channel: SLACK,  message: "APK Generate Failed- ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Link>)"

      }

    }
}
