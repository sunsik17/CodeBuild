FROM amazoncorretto:11
COPY build/libs/module-user-0.0.2.jar module-user-0.0.2.jar
ENTRYPOINT ["java","-DSpring.profiles.active-prod","-Dmailgun.key=${MAILGUN_KEY}","-Dmailgun.owner-email=${MAILGUN_OWNER_EMAIL}","-jar","module-user-0.0.2.jar"]
