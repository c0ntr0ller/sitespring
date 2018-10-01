#!/usr/bin/env bash

gradle clean bootJar

echo 'Copy files...'

scp -i ~/.ssh/id_rsa \
    ~/Java/bek/springboot/sitespring/build/libs/sitespring-0.1.0.jar \
    bek@172.16.0.203:/home/bek/sitespring

echo 'Restart server...'

ssh -i ~/.ssh/id_rsa bek@172.16.0.203 << EOF

pgrep -f sitespring-0.1.0.jar| xargs kill -9
nohup java -jar /home/bek/sitespring/sitespring-0.1.0.jar > log.txt &

EOF

echo 'Bye!'