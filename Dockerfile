FROM openjdk:8-jdk-alpine as builder

#prepare gradle
COPY gradle gradle
COPY gradlew .
COPY settings.gradle .
COPY build.gradle .
RUN chmod +x ./gradlew
#download gradle
RUN ./gradlew --no-daemon

#resolve dependencies
#COPY build.gradle .
#RUN ./gradlew resolveDependencies --no-daemon

#build jar file
COPY src src
RUN ls src
RUN ./gradlew bootjar --no-daemon

FROM openjdk:8-jdk-alpine
RUN apk add --no-cache gcompat
COPY --from=builder build/libs/*.jar app.jar
ENTRYPOINT java -jar -Dspring.profiles.active=$SPRING_PROFILES_ACTIVE /app.jar
