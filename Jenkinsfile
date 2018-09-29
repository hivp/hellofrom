node {
   stage('Get source code') {
      echo 'getting source code'
      git url: 'https://github.com/hivp/hellofrom.git', branch: 'master'
   }
   stage('Build with Gradle') {
      if(isUnix()) {
        echo 'is Unix'
        sh './gradlew clean build'
      } else {
        echo 'is not Unix'
        bat 'gradlew.bat clean build'
      }
   }
}
