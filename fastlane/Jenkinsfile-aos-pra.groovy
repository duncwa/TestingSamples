#!/usr/bin/env groovy

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
    DANGER_GITLAB_API_CREDS = "s.gitlabtoken"
    DANGER_GITLAB_API_TOKEN = "${env.DANGER_GITLAB_API_CREDS_PSW}"
    DANGER_GITLAB_HOST="https://gitlab.com/duncwa/timefighter"
    DANGER_GITLAB_API_BASE_URL="https://gitlab.com/duncwa/timefighter/api/v4"

    ghprbPullId = "${env.PULL_REQ_NUM}"
    PULL_REQ_NUM = "${env.PULL_REQ_NUM}"
    BUILD_NUM = "${env.BUILD_ID}"
    PR_NUM = "${env.PULL_REQ_NUM}"
    PR_URL = "https://gitlab.com/duncwa/timefighter/-/merge_requests/${env.PULL_REQ_NUM}"
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
        always { stash includes: "app/build/**/*", name: "test_aos_pra", allowEmpty: true }
      }
    }
  }

  post {
    always {
      script {
        try { unstash "test_aos_pra" }  catch (e) { echo "Failed to unstash stash: " + e.toString() }
      }
      sh "bundle exec fastlane aos_danger"
      archiveArtifacts artifacts: "app/build/**/*", fingerprint: true
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
