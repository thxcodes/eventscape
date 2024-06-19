FROM openjdk:17

# Definir diretório de trabalho
WORKDIR /app

# Copiar a aplicação para o contêiner
COPY . /app

# Verificar a instalação do Java
RUN java -version
