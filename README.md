# LocateRecordsInLogs-AkkaHTTP
A distributed program for locating requested records in the log files using Akka-HTTP.
---
Name: Karan Malhotra
---

### Installations
+ Install [Simple Build Toolkit (SBT)](https://www.scala-sbt.org/1.x/docs/index.html)
+ Ensure you can create, compile and run Java and Scala programs.

### Development Environment
+ Windows 10
+ Java 11.0.11
+ Scala 2.13.6
+ SBT Script Version 1.5.5
+ Other dependencies exist in build.sbt and scalapb.sbt
+ IntelliJ IDEA Ultimate
+ AWS API Gateway
+ AWS Lambda

## Entire Setup of the Project:
### Setting up Log File Generator
- Create your own AWS Lambda Function, AWS API Gateway and an S3 Bucket.
- Refer this lambda function for a better understanding of the binary search analysis of the log file.
- Watch this tutorial(https://www.youtube.com/watch?v=MgQDeKwTnDQ&feature=youtu.be) to give S3 get object access to your Lambda function.
- Launch an EC2 Instance on AWS and SSH via AWS CLI. Store the key-val pair.
- After ssh via ec2-user, type `aws configure` and enter your key-val pair.
- Install java, sbt and git on the EC2 instance.
- Clone the Log Generator Repository(https://github.com/karanmalh0tra/LogFileGenerator).
- Change the configurations to enter your S3 Bucket and Key name. The code will always push the latest log file to the S3 Bucket.
- Perform `sbt clean compile test` to clean, compile and execute the test cases.
- `sbt run` will start generating logs and push it to your S3 Bucket.

### Steps to Run the Akka-HTTP Application
- Clone the github repo on your local machine.
- Open the command line at the root of the project.
- Enter your desired configs in application.conf.
- Enter `sbt clean compile test` and see if all the test executions pass.
- Enter `sbt run` and check the output for md5 hashed logs or a message stating the absence of logs in the timeframe.

## Test Cases
1. Tests if URL is https or not.
2. Check the format of Time
3. Check the format of deltaTime
4. Perform check of logs to see if the GET Request to the API Gateway is successful or not

## Overview
This project covers the Akka HTTP Client called made to the AWS API Gateway which triggers the Lambda Function.<br/>
The Lambda function checks in a binary search algorithm whether the logs in the given time range are present or not.<br/>
If the logs are present, the response returned is the md5 hash of logs with a statusCode of 200.<br/>
If no logs were present in the timerange, the response is a 400-level message with a message that logs were not present in the time range.

## Output
When logs are not present:
![image](https://user-images.githubusercontent.com/22276682/140573937-d8383fb0-b30f-48bd-9442-914f4016e1e4.png)
<br/>
When logs are present:
![image](https://user-images.githubusercontent.com/22276682/140574082-5e064c07-dc87-49a6-befc-66c27a7c1337.png)

Video Link:
