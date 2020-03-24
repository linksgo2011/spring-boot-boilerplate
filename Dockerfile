FROM openjdk:8-jdk-slim
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} app.jar

# 性能调优集合 CPU、内存等实际情况

ENTRYPOINT ["java","-Xmx7800m -Xms7800 -Xmn4g -Xss128k  -XX:ParallelGC-Threads=4 -XX:+ConcMarkSweepGC -XX:+UseParNewGC -XX:SurvivorRatio=8:1 -XX:TargetSurvivor-Ratio=90 -XX:MaxTenuringThreshold=30", "-jar", "/app.jar"]
