FROM amazoncorretto:11
COPY build/libs/module-user-0.0.1-SNAPSHOT.jar module-user-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-DSpring.profiles.active-prod","-Dmailgun.key=${MAILGUN_KEY}","-Dmailgun.owner-email=${MAILGUN_OWNER_EMAIL}","-jar","module-user-0.0.1-SNAPSHOT.jar"]
