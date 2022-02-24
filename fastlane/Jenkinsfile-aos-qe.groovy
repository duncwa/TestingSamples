#!/usr/bin/env groovy

//
//  Jenkinsfile-aos-pra.groovy
//  TestingSamples
//  Created by Duncan Wallace 02/19/2022
//  Copyright © 2022. Duncwa LLC.  All rights reserved

pipeline {
    agent { label "fastlane_pra" }

    options {
      ansiColor("xterm")
      timeout(time: 1, unit: "HOURS")
      buildDiscarder(logRotator(numToKeepStr: "10", artifactNumToKeepStr: "5"))
    }

    environment {
      BUILD_NUM = "${env.BUILD_ID}"
      SLACK_URL = credentials("s.slackwebhookurl")
      SLACK_CHANNEL = "${env.SLACK_CHANNEL}"
      TEST_TASK = ENV['TEST_TASK']
      PROJECT_DIR = ENV['PROJECT_DIR']
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

      stage("Parallel") {
        parallel {
          stage('Unit Tests IntentsAdvancedSample') {
            steps {
              echo 'Test QE IntentsAdvancedSample'
              sh 'bundle exec fastlane test_aos_qe_unit'
            }
            post {
              always { stash includes: "ui/espresso/IntentsAdvancedSample/app/build/**/*", name: "test_aos_qe_ias", allowEmpty: true }
            }
          }
          stage('Unit Tests BasicSample') {
            steps {
              echo 'Test QE BasicSample'
              sh 'bundle exec fastlane test_aos_qe_unit'
            }
            post {
              always { stash includes: "ui/espresso/BasicSample/app/build/**/*", name: "test_aos_qe_bs", allowEmpty: true }
            }
          }
        }
      }
    }

    post {
      always {
        script {
          try { unstash "test_aos_qe_ias" }  catch (e) { echo "Failed to unstash stash: " + e.toString() }
          try { unstash "test_aos_qe_bs" }  catch (e) { echo "Failed to unstash stash: " + e.toString() }

        }
        archiveArtifacts artifacts: "ui/espresso/IntentsAdvancedSample/app/build/**/*", fingerprint: true
        archiveArtifacts artifacts: "ui/espresso/BasicSample/app/build/**/*", fingerprint: true
      }

      success {
        sh "echo 'Build Successful' "
        sh "bundle exec fastlane post_qe_aos_slack_message run_time:${currentBuild.duration / 1000} status:${currentBuild.result}"
      }

      unstable {
        sh "echo 'Build Unstable' "
        sh "bundle exec fastlane post_qe_aos_slack_message run_time:${currentBuild.duration / 1000} status:${currentBuild.result}"
      }

      failure {
        sh "echo 'Build Failed' "
        sh "bundle exec fastlane post_qe_aos_slack_message run_time:${currentBuild.duration / 1000} status:${currentBuild.result}"
      }

    }
}
