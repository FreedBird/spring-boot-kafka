#!/bin/bash

#端口号，根据此端口号确定PID
PORT=14002
PROJECT='api'
HOME='/data/app/'$PROJECT
LOGS=$HOME'/'$PROJECT'.log'

init_pid(){
	#查询出监听了PORT端口TCP协议的程序  
	#netstat -lnp|grep $PORT
	pid=`netstat -anp|grep $PORT|awk '{printf $7}'|cut -d/ -f1`
}

start(){
	echo "start $PROJECT start..."
	init_pid
	if [ -n "$pid" ]; then  
		echo "server already start, pid:$pid"  
		return 1
	fi
	#进入命令所在目录  
	cd $HOME
	# 把日志输出到HOME目录的server.log文件中   
	nohup java -jar $HOME/*.jar --spring.profiles.active=pro --server.port=$PORT> $LOGS 2>&1 &   #启动服务器 
    echo "start $PROJECT success..."  
}

stop(){
	echo "stop $PROJECT start..."
	init_pid
	if [ -z "$pid" ]; then  
		echo "not find program on port:$PORT"  
		return 1
	fi
	#结束程序，使用讯号2，如果不行可以尝试讯号9强制结束  
	kill -9 $pid
	rm -rf $pid
	echo "stop $PROJECT success..."
}

status(){
	init_pid
    if [ -n "$pid" ]; then  
		echo "server already start, pid:$pid"	
	else 
		echo "not find program on port:$PORT"
	fi
}

restart(){
    stop
    sleep 2
    start
}

case $1 in
    start)
		start
    ;;
    stop)
		stop
    ;;
    status)
		status
	;;
	restart)
		restart
	;;
    *)  echo 'You do not use command {start | stop | restart | status}'
    ;;
esac
exit 1

