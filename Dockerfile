FROM openjdk:17

ARG MAVEN_VERSION=3.8.5

RUN apt-get update && \
    apt-get install -y wget && \
    wget https://downloads.apache.org/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz && \
    tar xzvf apache-maven-$MAVEN_VERSION-bin.tar.gz -C /opt && \
    ln -s /opt/apache-maven-$MAVEN_VERSION /opt/maven && \
    ln -s /opt/maven/bin/mvn /usr/bin/mvn && \
    rm apache-maven-$MAVEN_VERSION-bin.tar.gz && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

ENV MAVEN_HOME /opt/maven
ENV PATH $MAVEN_HOME/bin:$PATH

RUN mvn -version
RUN java -version

# Definir diretório de trabalho
WORKDIR /app

# Copiar a aplicação para o contêiner
COPY . /app

# Comando para verificar a instalação do Java
RUN java -version
