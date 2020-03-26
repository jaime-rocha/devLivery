pipeline {
	agent {label 'master_linux'}
	
	stages{
		stage('Android lint') {
			steps{
				sh './gradlew lint'
				script{
				    androidLint(
				        unstableTotalAll: '100', 
				        failedTotalAll: '120'
				    )
			    }
			}
		}
		
		stage('Jacoco Report')
		{
		    steps{
		        sh './gradlew jacocoTestReport'
		    }
		}
		
		stage('Sonarqube Analysis')
		{
		    steps{
		        sh './gradlew --info sonarqube'
		    }
			post{
    			success{
    			     echo "Build is ${currentBuild.result}"
    			     echo 'Sending report by email...'
    			     emailext(
    			         to: 'jaime.rocha@avantica.net',
    			         subject: "Code Review: Job ${currentBuild.result} - '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
    			         body: 'Hello,\n\n Please refer following report from code review stage.\n ${JELLY_SCRIPT,template="email-report-success"}'
    			     )
    			 }
    			 unstable{
    			     echo "Build is ${currentBuild.result}"
    			     echo 'Sending report by email...'
    			     emailext(
    			         to: 'jaime.rocha@avantica.net',
    			         subject: "Code Review: Job ${currentBuild.result} - '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
    			         body: 'Hello,\n\n Please refer following report from code review stage.\n ${JELLY_SCRIPT,template="email-report"}'
    			     )
    			 }    
    			 failure{
    			    echo "Build is ${currentBuild.result}"
    			     echo 'Sending report by email...'
    			     emailext(
    			         to: 'jaime.rocha@avantica.net',
    			         subject: "Code Review: Job ${currentBuild.result} - '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
    			         body: 'Hello,\n\n Please refer following report from code review stage.\n ${JELLY_SCRIPT,template="email-report"}'	 
    			     )
    			 }    
		    }
		}
		
		stage('Build') {
			steps{
				sh './gradlew clean'
				sh './gradlew build'
			}
		}
		
		stage('Upload to Nexus'){
		    steps{
		        sh './gradlew upload'
		    }
		}
		
		stage('Delivery to TestFairy'){
			steps{
				sh 'curl https://upload.testfairy.com/api/upload -F api_key="b22184448b5de9b79cea5855a749d4cef1d3177b" -F file=@"${WORKSPACE}/app/build/outputs/apk/debug/chambaReporter-debug.apk"'
			}
		}
	}
	post{
	    always{
			emailext(
			    to: 'jaime.rocha@avantica.net',
    			subject: '$DEFAULT_SUBJECT',
    			body: '$DEFAULT_CONTENT'
    		)
	    }
	}
}