dir=$(pwd)
cd /common/application/sync/syncTool
nohup java -Dlogging.config=config/log4j2.xml  -classpath :lib/* com.buzhishi.synctool.SyncToolApplication 1>/dev/null 2>&1 &
cd $dir