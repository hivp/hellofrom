FROM delitescere/java

# define jar name
ENV JAR_NAME HelloFrom-1.0.jar

ADD build/libs/$JAR_NAME $JAR_NAME

EXPOSE 8080

# Launch the java app
ENTRYPOINT ["sh", "-c"]
CMD ["exec java -jar $JAR_NAME"]
