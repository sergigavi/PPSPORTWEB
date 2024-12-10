import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.5"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.23"
	kotlin("plugin.spring") version "1.9.23"
	kotlin("plugin.jpa") version "1.9.23"
}

group = "es.sergigavi"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_20
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-mail")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.mysql:mysql-connector-j")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("io.jsonwebtoken:jjwt-api:0.12.6")	// https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-api
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")	// https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-impl
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6") // https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-jackson
	implementation("org.springframework.boot:spring-boot-starter-security") // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-security
	testImplementation("org.springframework.security:spring-security-test")// https://mvnrepository.com/artifact/org.springframework.security/spring-security-test
	testImplementation("org.springframework.boot:spring-boot-starter-test")// https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-test
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.0")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "20"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
