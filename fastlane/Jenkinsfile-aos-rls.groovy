#!/usr/bin/env groovy

//
//  Jenkinsfile-aos-rls.groovy
//  TestingSamples
//  Created by Duncan Wallace 02/19/2022
//  Copyright © 2022. Duncwa LLC.  All rights reserved

pipeline {
    agent any

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
        stage('Build') {
            steps {
                echo 'Test PRA'
                sh 'bundle exec fastlane test_ios_pra'
            }
        }
        stage('Inspect') {
            steps {
                echo 'Inspecting..'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}
