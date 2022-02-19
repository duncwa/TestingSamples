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
      SLACK = "#cs-ghfollowers-jenkins"
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
      stage('Build and Upload IPA') {
          steps {
              echo 'Generate IPA'
              sh 'bundle exec fastlane generate_dev_ipa'
          }
          post {
            always { stash includes: "fastlane/*_output/**/*", name: "generate_dev_ipa", allowEmpty: true }
          }
      }
    }

    post {
      always {
        script {
          try { unstash "generate_dev_ipa" }  catch (e) { echo "Failed to unstash stash: " + e.toString() }
        }
        archiveArtifacts artifacts: "fastlane/*_output/**/*", fingerprint: true
      }

      success {
        sh "echo 'IPA Successful' "
        slackSend channel: SLACK, message: "IPA Generate Successful - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Link>)"
      }

      unstable {
        sh "echo 'IPA Unsuccessful' "
        slackSend channel: SLACK,  message: "IPA Generate Failed - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Link>)"

      }

      failure {
        sh "echo 'IPA Failed' "
        slackSend channel: SLACK,  message: "IPA Generate Failed- ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Link>)"

      }

    }
}
