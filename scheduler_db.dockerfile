FROM mysql:8.0.31-debian
COPY ./src/main/resources/sql/createDatabase.sql /usr/local/createDatabase.sql
WORKDIR /usr/local

EXPOSE 3306:3306

CMD mysql -u $MYSQL_USER < ./createDatabase.sql
