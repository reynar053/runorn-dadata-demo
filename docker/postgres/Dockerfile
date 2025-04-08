FROM postgres:15-alpine

ENV POSTGRES_DB=userdb
ENV POSTGRES_USER=admin
ENV POSTGRES_PASSWORD=secret

COPY init.sql /docker-entrypoint-initdb.d/

EXPOSE 5432