FROM openjdk:8-jdk-slim
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} app.jar

# 性能调优集合 CPU、内存等实际情况参考 https://wiki.printf.cn/%E6%80%A7%E8%83%BD%E4%BC%98%E5%8C%96/performance/performance-overview/
ENTRYPOINT ["java","-Xmx7800m -Xms7800 -Xmn4g -Xss128k  -XX:ParallelGC-Threads=4 -XX:+ConcMarkSweepGC -XX:+UseParNewGC -XX:SurvivorRatio=8:1 -XX:TargetSurvivor-Ratio=90 -XX:MaxTenuringThreshold=30", "-jar", "/app.jar"]
