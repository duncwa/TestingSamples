source "https://rubygems.org"

gem 'octokit'
gem 'gitlab'
gem 'fastlane'
gem 'aws-sdk-s3'
gem 'danger-gitlab'
gem 'jenkins_api_client'
gem 'xcpretty-json-formatter'
gem 'artifactory'
# gem 'trainer'
# gem 'fastlane-plugin-trainer'
gem 'cocoapods'
gem 'json'
gem 'nokogiri'
gem 'plist'
gem 'pry'
gem 'psych'
gem 'rubyzip'

gem 'danger-junit' #Lets you report your test suite problems back to the PR elegantly.
gem 'danger-commit_lint' #Use Danger to lint your commit messages.
# gem 'danger-code_style_validation' #Danger plugin for code style validation based on clang-format.
gem 'danger-ktlint' #A Danger Plugin to lint kotlin files using ktlint command line interface.
gem 'danger-android_lint' #A Danger plugin for Android Lint.

gem 'rspec'
gem 'rspec_junit_formatter'
gem 'rubocop'
gem 'rubocop-performance'
gem 'simplecov'
gem 'simplecov-json'

plugins_path = File.join(File.dirname(__FILE__), 'fastlane', 'Pluginfile')
eval_gemfile(plugins_path) if File.exist?(plugins_path)
