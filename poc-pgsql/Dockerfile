FROM postgres:latest

# Instala dos2unix
RUN apt-get update && apt-get install -y dos2unix && rm -rf /var/lib/apt/lists/*

# Copia el script de inicialización al contenedor
COPY ./init /docker-entrypoint-initdb.d

# Convierte el script a formato Unix
RUN dos2unix /docker-entrypoint-initdb.d/*