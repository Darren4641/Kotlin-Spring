import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.3.1"
    id("io.spring.dependency-management") version "1.1.5"
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.spring") version "1.9.24"
//    kotlin("jvm") version "1.8.22"
//    kotlin("plugin.spring") version "1.8.22"
//    kotlin("plugin.jpa") version "1.8.22"
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
    // spring web
    implementation("org.springframework.boot:spring-boot-starter-web")
    // spring jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    // validation
    implementation("org.springframework.boot:spring-boot-starter-validation")
    // kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // annotation processor
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

//    // mariaDB
//    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
     //mysql
    runtimeOnly("com.mysql:mysql-connector-j")

    // gson
    implementation("com.google.code.gson:gson:2.10.1")

    //jackson-module-kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // h2 DB
    runtimeOnly("com.h2database:h2")
    testImplementation("com.h2database:h2")

    //spring security
    implementation("org.springframework.boot:spring-boot-starter-security")

    //spring security oauth2
    implementation ("org.springframework.boot:spring-boot-starter-oauth2-client")

    //JWT
    implementation("io.jsonwebtoken:jjwt-api:0.11.2")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.2")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.2")

    // kotest
    testImplementation("io.kotest:kotest-runner-junit5:5.5.5")
    testImplementation("io.kotest:kotest-assertions-core:5.5.5")
    testImplementation("io.kotest:kotest-extensions-spring:4.4.3")
    testImplementation("io.mockk:mockk:1.13.5")
}


kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
