# Dog of The Dow Back-End Side Application
Dod_watcher is the back-end side of our Dog of the Dow Mobile application that help customer to invest their money in stock market using strategies like Dog of The Dow.  
User can use this application as the stock wallet and keep track of their investment.  
![](https://github.com/hialan-org/dod_mobile/workflows/Testing%20Dev/badge.svg) 
![](https://github.com/hialan-org/dod_mobile/workflows/Testing%20Prod/badge.svg) 
![](https://github.com/hialan-org/dod_mobile/workflows/Release/badge.svg)
![](https://img.shields.io/github/issues/hialan-org/dod_web_admin?color=orange)

## Features
- Pull stock prices from 3rd party IEX API daily. 
- Manage user stocks.
- Calculating user profits daily. 
- Tracking user investment.
- Give suggestion how to invest user money using Dog of The Dow.

## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.  
See deployment for notes on how to deploy the project on a live system.

### Prerequisites
Download and install Java SDK (At least version 8):  
`https://www.oracle.com/java/technologies/javase-downloads.html`  
Create and AWS account
`https://aws.amazon.com/premiumsupport/knowledge-center/create-and-activate-aws-account/`
Download and install Sam CLI:
`https://docs.aws.amazon.com/es_es/serverless-application-model/latest/developerguide/serverless-sam-cli-install-windows.html`
`https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/serverless-sam-cli-install-mac.html`
Install Docker. Note: Docker is only a prerequisite for testing your application locally.

### How to run project
To run the project locally in your machine, follow these steps:
- Clone the repo
- In the project directory, compile: `gradlew shadowJar`
- In the project directory, run: `sam local start-api --template sam-local.yaml`
- The back-end will be running on http://127.0.0.1:3000/

### Development
To start enhance or fix a bug, follow these steps:
- Fork the repo
- Create a new branch (git checkout -b new-features)
- Make the appropriate changes in the files
- Add changes to reflect the changes made
- Commit your changes (git commit -am 'Add new features')
- Push to the branch (git push origin new-features)
- Create a Pull Request

## Technologies
Project is created with:  
- [Java](https://www.java.com/en/) - Java is a general-purpose programming language that is class-based, object-oriented, and designed to have as few implementation dependencies as possible.
- [Micronaut](https://micronaut.io/) - A modern, JVM-based, full-stack framework for building modular, easily testable microservice and serverless applications. 
- [Hibernate ORM](https://hibernate.org/orm/) - Hibernate ORM enables developers to more easily write applications whose data outlives the application process. As an Object/Relational Mapping (ORM) framework, Hibernate is concerned with data persistence as it applies to relational databases (via JDBC).
- [AWS](https://aws.amazon.com/) - Amazon Web Services is a cloud computing platform which provides services such as compute power, database storage, content delivery and many other functions. It is flexible, scalable, and reliable and due to this many companies are implementing it in their work. 
- [AWS Lambda](https://aws.amazon.com/lambda/) - AWS Lambda is Amazon’s serverless compute service. You can run your code on it without having to manage servers or even containers. It’ll automatically scale depending on how much work you feed into it. When you do run Lambdas in the cloud, you’ll only pay for what you use. 
- [OAuth2](https://oauth.net/2/) - OAuth 2.0 is the industry-standard protocol for authorization. OAuth 2.0 focuses on client developer simplicity while providing specific authorization flows for web applications, desktop applications, mobile phones. 
- [Amazon Cloud Watch](https://aws.amazon.com/cloudwatch/) - Cloud Watch is a component of Amazon Web Services (AWS) that provides monitoring for AWS resources and the customer applications running on the Amazon infrastructure. On the other hand, our team used Cloud Watch for scheduling daily jobs

## To do list
- Load&Endurance tests for all back-end APIs

### run the app local do this:
-   ./gradlew shadowJar
-   sam local start-api --template sam-local.yaml


