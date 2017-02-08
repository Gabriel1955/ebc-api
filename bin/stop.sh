#!/bin/sh
# ---------------------------------------
# find BIN_HOME
BIN_HOME=`dirname "$0"`
BIN_HOME=`cd "$BIN_HOME"; pwd`

# ---------------------------------------
# use setenv.sh
SETENV="$BIN_HOME"/setenv.sh
if [ -r "$SETENV" ]; then
  . "$SETENV"
else
  echo "Cannot find $SETENV"
  echo "This file is needed to run this program"
  exit 1
fi

# ---------------------------------------
# kill old process
APP_PROCESS_NO=`ps -ef|grep "APP_SN=$APP_SN "|grep -v "grep"|awk '{print $2}'`
while [ -n "$APP_PROCESS_NO" ] ; do
  echo "$APP_SN process is running, process No. $APP_PROCESS_NO , stop ..."
  kill -9 $APP_PROCESS_NO
  sleep 1
  APP_PROCESS_NO=`ps -ef|grep "APP_SN=$APP_SN "|grep -v "grep"|awk '{print $2}'`
done
echo "$APP_SN process is stopped."