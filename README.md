# Parser

This application parses an exact replica of a log file into mysql database.
It is capable of checking if certain ip address(es) make more than an allowed certain number of requests for the given duration and tags those accordingly. 

## Prerequisites
You will a running instance of [MySQL][1] installed.

Create schema `parser`

[1]: https://dev.mysql.com/downloads/installer/

## Running

To start the parser application, navigate to dir containing the `parser-0.0.1.jar` and `access.log` files (or navigate as needed to locate files) as shown in terminals:

    java -jar parser-0.0.1.jar --accesslog=./access.log --startDate=2017-01-01.00:00:00 --duration=hourly --threshold=50

Runs on port `8080` or as specified in terminal

## Resources
#### SQL
Files `query.sql` and `schema.sql` contains solution to MySQL questions and the basic two table schema respectively.

Note: Application is capable of generating all schemas including those needed for the batch job execution on startup 

#### Source Code
Written in Java using Spring Boot

#### Extension
Command argument could be passed to run job as JobParameter after duration specified should the log file continually update. 

Scaling and Parallel Processing of the log file in cases of several MBs or more of data 

## License

Copyright © 2019 Simon Okwori