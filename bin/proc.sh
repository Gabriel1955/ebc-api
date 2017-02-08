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

#----------------------------------------
ps -ef|grep "APP_SN=$APP_SN "|grep -v "grep"