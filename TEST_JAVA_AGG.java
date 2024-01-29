//STEP 1: DEFINE THE DATA MODEL
//Create the classes that will represent the  entities. For example, an Account class that has an ID, a balance.

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity
public class Account {
    @Id
    private Long id;
    private BigDecimal balance;
}

//Step 2: IMPLEMENT THE TRANSFER SERVICE
//Create a service that handles transfer operations between accounts. Here, transferMoney  will take two account IDs and an amount to transfer.

import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class TransferService {
    public void transferMoney(Long sourceAccountId, Long targetAccountId, BigDecimal amount) {
        // Lógica para transferir dinero entre cuentas
    }
}

//Step 3: Implement the REST API
//A framework like Spring Boot to implement the REST API. Defines controllers that interact with the transfer service.

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transferencias")
public class TransferController {
    @Autowired
    private TransferService transferService;

    @PostMapping
    public ResponseEntity<String> transferMoney(@RequestBody TransferRequest request) {
        transferService.transferMoney(request.getSourceAccountId(), request.getTargetAccountId(), request.getAmount());
        return ResponseEntity.ok("Transferencia exitosa");
    }
}


//STEP 4: CONFIGURE MAVEN AND DOCKER

    //1) MAVEN: 
    <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.yourcompany</groupId>
    <artifactId>your-application</artifactId>
    <version>1.0.0</version>

    <properties>
        <java.version>11</java.version>
    </properties>

    <dependencies>
        <!-- Spring Boot dependencies -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!-- Other dependencies as needed -->
    </dependencies>

    <build>
        <plugins>
            <!-- Plugin to build a Fat-Jar -->
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>

            <!-- Docker plugin -->
            <plugin>
                <groupId>com.spotify</groupId>
                <artifactId>dockerfile-maven-plugin</artifactId>
                <version>1.4.13</version> <!-- Version may vary -->
                <executions>
                    <execution>
                        <id>default</id>
                        <goals>
                            <goal>build</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <imageName>your-docker-image-name</imageName>
                    <!-- Other configurations as needed -->
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>

    //2) DOCKER

FROM openjdk:11-jre-slim AS builder

WORKDIR /app
COPY pom.xml .
COPY src src
RUN mvn package

FROM openjdk:11-jre-slim
COPY --from=builder /app/target/your-application.jar /app.jar

EXPOSE 8080
CMD ["java", "-jar", "/app.jar"]

//STEP 5: Spring Data and PostgreSQL Configuration

spring.datasource.url=jdbc:postgresql://localhost:5432/your-database-name
spring.datasource.username=your-username
spring.datasource.password=your-password
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect






