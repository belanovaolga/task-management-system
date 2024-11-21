plugins {
    java
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.6"
    id("org.openapi.generator") version "7.7.0"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.postgresql:postgresql:42.7.3")
    implementation("org.liquibase:liquibase-core")
    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")
    implementation("io.jsonwebtoken:jjwt-api:0.12.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.5")
    implementation ("io.swagger.core.v3:swagger-annotations:2.2.22")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")

    implementation("javax.annotation:javax.annotation-api:1.3.2")
    implementation("javax.ws.rs:jsr311-api:1.1.1")
    implementation("javax.validation:validation-api:2.0.1.Final")
    implementation("javax.servlet:servlet-api:2.5")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

openApiGenerate {
    generatorName.set("spring")
    configOptions.set(
        mapOf(
            "generateApis" to "true",
            "generateSupportingFiles" to "false",
            "interfaceOnly" to "true",
            "library" to "spring-boot",
            "useBeanValidation" to "true",
            "openApiNullable" to "false",
        )
    )
    inputSpec.set("$rootDir/openapi/openapi.yaml")
    outputDir.set("$buildDir/generated")

    modelPackage.set("com.example.task.management.system.model")
    apiPackage.set("com.example.task.management.system.api")
}

sourceSets {
    main {
        java {
            srcDirs("$buildDir/generated/src/main/java")
        }
    }
}

tasks.named("compileJava") {
    dependsOn(tasks.openApiGenerate)
}

tasks.withType<Test> {
    useJUnitPlatform()
}
