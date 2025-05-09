FROM eclipse-temurin:17-jdk-jammy

# install prerequisites
RUN apt-get update && \
    apt-get install -y curl unzip util-linux && \
    rm -rf /var/lib/apt/lists/*

ARG WILDFLY_VERSION=33.0.2.Final
ENV WILDFLY_VERSION=${WILDFLY_VERSION}

# Download & unzip WildFly once, then clone into two folders
RUN mkdir -p /opt && \
    cd /opt && \
    curl -L https://github.com/wildfly/wildfly/releases/download/${WILDFLY_VERSION}/wildfly-${WILDFLY_VERSION}.zip \
      -o wildfly.zip && \
    unzip wildfly.zip && rm wildfly.zip && \
    mv wildfly-${WILDFLY_VERSION} wildfly-base && \
    cp -r wildfly-base wf1 && \
    cp -r wildfly-base wf2

# Deploy each WAR into its own folder
COPY client/target/client-1.0-SNAPSHOT.war \
     /opt/wf1/standalone/deployments/client.war
COPY server/target/server-1.0-SNAPSHOT.war \
     /opt/wf2/standalone/deployments/server.war

# Entrypoint script to launch both servers
COPY docker-entrypoint.sh /usr/local/bin/
RUN chmod +x /usr/local/bin/docker-entrypoint.sh

ENTRYPOINT ["docker-entrypoint.sh"]
