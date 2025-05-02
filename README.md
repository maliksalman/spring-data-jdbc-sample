# spring-data-jdbc-sample

A sample spring-boot application that shows off `spring-data-jdbc` capabilities. It also shows an example of integration with `liquibase` DB migrations.

## Building

JDK 17 or 21 is required to build/run (in the `build.gradle` 21 is set up by default): 

```
./gradlew clean build
```

## Running

### Run with H2 database

For an in-memory H2 database, just run the application:

```
java -jar build/libs/spring-data-jdbc-sample-1.0.jar 
```

### Run with MySQL database

To run with a local MySQL, run with `localmysql` spring profile

```
SPRING_PROFILES_ACTIVE=localmysql java -jar \
    build/libs/spring-data-jdbc-sample-1.0.jar
```

For the above to work, start a MySQL instance in docker like so:

```
docker run --name mysql \
    -e MYSQL_DATABASE=testdb \
    -e MYSQL_PASSWORD=secret \
    -e MYSQL_ROOT_PASSWORD=verysecret \
    -e MYSQL_USER=myuser \
    -d -p 13306:3306 \
    mysql:8
```

We can replace `mysql:8` with `mysql:5.7` as the docker image name to target a specific MySQL version. Running MySQL 5.7 on M1 mac requires an extra `platform` parameter since that version of docker image is only available for `amd64` platform:

```
docker run --name mysql \
    --platform linux/amd64 \
    -e MYSQL_DATABASE=testdb \
    -e MYSQL_PASSWORD=secret \
    -e MYSQL_ROOT_PASSWORD=verysecret \
    -e MYSQL_USER=myuser \
    -d -p 13306:3306 \
    mysql:5.7
```

If connecting to a different MySQL database, the DB connection properties defined in  [src/main/resources/application-localmysql.yml](src/main/resources/application-localmysql.yml) will have to be adjusted.

### Run with PostgresSQL database

To run with a local PostgresSQL, run with `localpg` spring profile

```
SPRING_PROFILES_ACTIVE=localpg java -jar \
    build/libs/spring-data-jdbc-sample-1.0.jar
```

For the above to work, start a PostgresSQL instance in docker like so:

```
docker run --name postgres \
    -e POSTGRES_PASSWORD=secret \
    -d -p 15432:5432 \
    postgres:15
```

If connecting to a different PostgresSQL database, the DB connection properties defined in [src/main/resources/application-localpg.yml](src/main/resources/application-localpg.yml) will have to be adjusted.

### Run with an Oracle database

To use an Oracle DB, run with `oracle` spring profile

```
SPRING_PROFILES_ACTIVE=oracle java -jar \
    build/libs/spring-data-jdbc-sample-1.0.jar
```

For the above to work, start an OracleXE instance in docker like so:

```
docker run --name oracle-db \
    -d -p 1521:1521 \
    -e ORACLE_PWD=super-secret-101 \
    container-registry.oracle.com/database/express:21.3.0-xe
```

If connecting to a different Oracle database, the DB connection properties defined in [src/main/resources/application-oracle.yml](src/main/resources/application-oracle.yml) will have to be adjusted.


## Misc

Creating a spring-boot OCI image (with a specific image name):

Running this as is will create an OCI image with a native-image binary inside the image. If you want a regular image with the JRE included, comment out the `org.graalvm.buildtools.native` plugin from the `build.gradle`

```
./gradlew clean bootBuildImage --imageName=maliksalman/spring-data-jdbc-sample:1.0
```

Running the docker container:

```
docker run --rm -p 8080:8080 maliksalman/spring-data-jdbc-sample:1.0
```

Compiling into native binary with GraalVM or NIK:

```
./gradlew clean nativeCompile
```

This will create a binary at `build/native/nativeCompile/spring-data-jdbc-sample`
