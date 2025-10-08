FROM eclipse-temurin:21.0.8_9-jre-noble

RUN mkdir /opt/app

WORKDIR /opt/app

COPY dists/product-api-21-v4.jar app.jar

CMD ["java", "-jar", "app.jar"]