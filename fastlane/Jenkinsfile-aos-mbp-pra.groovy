#!/usr/bin/env groovy

//  Jenkinsfile-ios-mbp-pra.groovy
//  TimeFighter
//  Created by Duncan Wallace 12/16/2021
//  Copyright © 2021. Duncwa LLC.  All rights reserved

pipeline {
  agent { label "fastlane_pra" }

  options {
    ansiColor("xterm")
    timeout(time: 1, unit: "HOURS")
    buildDiscarder(logRotator(numToKeepStr: "10", artifactNumToKeepStr: "5"))
  }

  environment {
    DANGER_CREDS = credentials("s.gitlabtoken_uns")
    DANGER_GITLAB_USERNAME = "${env.DANGER_CREDS_USR}"
    DANGER_GITLAB_PASSWORD = "${env.DANGER_CREDS_PSW}"

    //DANGER_GITLAB_HOST="https://gitlab.com/duncwa/timefighter"
    //DANGER_GITLAB_API_BASE_URL="https://gitlab.com/api/v4"

    CHANGE_ID = "${env.GITLABMERGEREQUESTID}"
    BUILD_NUM = "${env.BUILD_ID}"
    PR_NUM = "${env.GITLABMERGEREQUESTIID}"
    PR_URL = "https://gitlab.com/duncwa/timefighter/-/merge_requests/${env.CHANGE_ID}"
    GIT_URL_1 = "https://gitlab.com/duncwa/timefighter"
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
    stage('Build and Test PRA') {
      steps {
        echo 'Test PRA'
        sh 'bundle exec fastlane test_aos_pra'
      }
      post {
        always { stash includes: "app/build/reports/**/*", name: "test_ios_pra", allowEmpty: true }
      }
    }
  }

  post {
    always {
      script {
        try { unstash "test_aos_pra" }  catch (e) { echo "Failed to unstash stash: " + e.toString() }
      }
      sh "bundle exec fastlane aos_danger_mbp"
      archiveArtifacts artifacts: "app/build/reports/**/*", fingerprint: true
    }

    success {
      sh "echo 'Build Successful' "
      sh "bundle exec fastlane post_pra_slack_message run_time:${currentBuild.duration / 1000} status:${currentBuild.result}"
    }

    unstable {
      sh "echo 'Build Unstable' "
      sh "bundle exec fastlane post_pra_slack_message run_time:${currentBuild.duration / 1000} status:${currentBuild.result}"
    }

    failure {
      sh "echo 'Build Failed' "
      sh "bundle exec fastlane post_pra_slack_message run_time:${currentBuild.duration / 1000} status:${currentBuild.result}"
    }
  }
}
