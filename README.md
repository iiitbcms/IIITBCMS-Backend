[![CircleCI](https://circleci.com/gh/iiitbcms/IIITB-CMS-Backend.svg?circle-token=82792b994f6b5a5e5baf538ed6d2f2b50ea144b7)](https://app.circleci.com/pipelines/github/iiitbcms/IIITB-CMS-Backend)

![Maven](https://github.com/iiitbcms/IIITBCMS-Backend/actions/workflows/mvn-verify.yml/badge.svg)
![Lint](https://github.com/iiitbcms/IIITBCMS-Backend/actions/workflows/linter.yml/badge.svg)
![AWS deployment](https://github.com/iiitbcms/IIITBCMS-Backend/actions/workflows/deploy.yml/badge.svg)

![Repo Size](https://img.shields.io/github/repo-size/iiitbcms/IIITBCMS-Backend)
![Commits](https://img.shields.io/github/commit-activity/w/iiitbcms/IIITBCMS-Backend)
![Last Commit](https://img.shields.io/github/last-commit/iiitbcms/IIITBCMS-Backend/main)
![Open Issues](https://img.shields.io/github/issues-raw/iiitbcms/IIITBCMS-Backend)
![Pull Requests](https://img.shields.io/github/issues-pr-raw/iiitbcms/IIITBCMS-Backend)
![visitor badge](https://visitor-badge.glitch.me/badge?page_id=iiitbcms.IIITBCMS-Backend&left_text=Visitors)

![Forks](https://img.shields.io/github/forks/iiitbcms/IIITBCMS-Backend?style=social)
![Stars](https://img.shields.io/github/stars/iiitbcms/IIITBCMS-Backend?style=social)
![Watchers](https://img.shields.io/github/watchers/iiitbcms/IIITBCMS-Backend?style=social)

# IIITB CMS Backend
Contains backend code for IIITB CMS

### Running the project
#### Clean the target directory
`mvn clean`
#### Install the dependencies
`mvn install`
#### Compile the Java source classes
`mvn compiler:compile`
#### Compile the test classes
`mvn compiler:testCompile`
#### Build the project and package
`mvn build package`

***

### API Documentation
Check the API docs at [http://localhost:8080/swagger-ui.html#/](http://localhost:8080/swagger-ui.html#/) after running the project.

***
### Mailbox
As of now, I have used a pseudo mail box, `mailtrap.io` for account validation after signup. Credentials for that need to be changed in the `application.properties` file.

***

# For IIITBSoC
Queries welcome at [akshath.kaushal@iiitb.ac.in](mailto:akshath.kaushal@iiitb.ac.in)    
Check the front end repository [here](https://github.com/akshathkaushal/IIITB-CMS-Frontend)
