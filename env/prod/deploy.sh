## the ansible is working on this server deploy by shell manually

scp ./bootstrap/out/libs/bootstrap-1.0.0.jar ubuntu@140.143.4.135:/var/application/spring-boot-boilerplate-0.0.1-SNAPSHOT.jar

ssh ubuntu@140.143.4.135 "netstat -apn | grep 8080 | grep LISTEN | awk '{print $7}' | sed 's/[^0-9]*//g' | xargs kill -15 "

ssh ubuntu@140.143.4.135 'nohup java -jar /var/application/spring-boot-boilerplate-0.0.1-SNAPSHOT.jar -Dspring.profiles.active=prod > log.txt &'
