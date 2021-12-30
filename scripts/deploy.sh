#!/usr/bin/env bash

REPOSITORY=/home/ec2-user/app/step2
PROJECT_NAME=tire-erp-server

echo "> Build 파일 복사"
cp $REPOSITORY/zip/*.jar $REPOSITORY/

for ((i = 0; i < 3; i++)); do
  echo "> 현재 구동 중인 어플리케이션 pid 확인"
  # shellcheck disable=SC2009
  CURRENT_PID=$(ps -ef | grep $PROJECT_NAME | grep -v grep | awk '{print $2}')

  echo "> 현재 구동 중인 어플리케이션 pid: $CURRENT_PID"

  if [ -z "$CURRENT_PID" ]; then
    echo "> 현재 구동 중인 어플리케이션이 없으므로 종료하지 않습니다."
    sleep 5
    break
  else
    kill -15 "$CURRENT_PID"
    echo ">kill -15 $CURRENT_PID"
    sleep 10
  fi
done

echo "> 새 어플리케이션 배포"
JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)
echo "> JAR NAME: $JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"
chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"
nohup java -jar $JAR_NAME >$REPOSITORY/nohup.out 2>&1 &
