#!/bin/sh
# Environment Variable Prerequisites
# ---------------------------------------
#
# Required Items
#
# APP_SN            unique sign on host
#
# JAVA_OPTS         java options
#
# MAIN_CLASS        main class
#
# GC_PRINT_ENABLE   enable gc print
#
# GC_LOG_PATH       gc print log path, relative under APP_HOME/logs,
#                   default value is APP_SN
#
# Optional Items
#
# LANG              language, default use environment config
#
# JAVA_HOME         Java Development Kit installation path, default use environment config
#
# Automatic Identification Items
#
# BIN_HOME          app startup shell path
#
# APP_HOME          app installation path
#
# CLASSPATH         app class path, default is APP_HOME + APP_HOME/lib/*jar + APP_HOME/classes
#
# ---------------------------------------

# ----- user config area -----
APP_SN=ebcapi
JAVA_OPTS="-Xss1024k -Xms2048m -Xmx2048m -Xmn1500m -XX:PermSize=64m -XX:MaxPermSize=128m"
MAIN_CLASS="com.tunyun.product.ebc.ebcapi.startup.MainBootstrap"
GC_PRINT_ENABLE=true
GC_LOG_PATH=$APP_SN

# ----- automatic identification area, no manual work ----- 
# find BIN_HOME
BIN_HOME=`dirname "$0"`
BIN_HOME=`cd "$BIN_HOME"; pwd`

# find APP_HOME
find_APP_HOME(){
  # find /bin in BIN_HOME
  bin_index=`echo ""|awk '{print index("'"$BIN_HOME"'","/bin")}'`
  if [ $bin_index != 0 ]; then
    # found /bin
    endindex=`expr $bin_index - 1`
    APP_HOME=`echo ""|awk '{print substr("'"$BIN_HOME"'",1,"'"$endindex"'")}'`
  else
    echo "$BIN_HOME don't contain bin folder"
    exit 1
  fi
  echo $APP_HOME
}
APP_HOME=`find_APP_HOME`

# classpath
CLASSPATH=$APP_HOME
for i in `ls $APP_HOME/lib/*.jar`
do
  CLASSPATH=$CLASSPATH:$i
done
CLASSPATH=$CLASSPATH:$APP_HOME/classes

# jdk special config
TEMP_FILE=java_version.log
ORACLE_KEY=HotSpot
IBM_KEY=IBM
$JAVA_HOME/bin/java -version 2>$TEMP_FILE
ORACLE_INFO=`cat $TEMP_FILE |grep $ORACLE_KEY`
IBM_INFO=`cat $TEMP_FILE |grep $IBM_KEY`
if [ ! -z "$ORACLE_INFO" ]; then
   echo "JDK Vendor is Oracle"
   JAVA_OPTS="$JAVA_OPTS -XX:+UseParallelGC"
   if [ $GC_PRINT_ENABLE == true ]; then
      JAVA_OPTS="$JAVA_OPTS -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:$APP_HOME/logs/$GC_LOG_PATH/gc_`date +%Y%m%d.%H%M%S`.log"
   fi
elif [ ! -z "$IBM_INFO" ]; then
   echo "JDK Vendor is IBM"
   JAVA_OPTS="$JAVA_OPTS -Xgcpolicy:gencon"
   if [ $GC_PRINT_ENABLE == true ]; then
      JAVA_OPTS="$JAVA_OPTS -verbose:gc -Xverbosegclog:$APP_HOME/logs/$GC_LOG_PATH/gc_`date +%Y%m%d.%H%M%S`.log"
   fi
else
   echo "JDK Vendor Unknow! Use Oracle config."
   JAVA_OPTS="$JAVA_OPTS -XX:+UseParallelGC"
   if [ $GC_PRINT_ENABLE == true ]; then
      JAVA_OPTS="$JAVA_OPTS -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:$APP_HOME/logs/$GC_LOG_PATH/gc_`date +%Y%m%d.%H%M%S`.log"
   fi
fi
rm $TEMP_FILE

# ----- export to env -----
export APP_SN
export BIN_HOME
export APP_HOME
export LANG
export JAVA_HOME
export JAVA_OPTS
export CLASSPATH
export MAIN_CLASS