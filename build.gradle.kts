plugins {
    java
    `java-library`
    `maven-publish`
    id("io.freefair.lombok") version "4.1.6"
    id("org.springframework.boot") version "2.1.13.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
}

group = "cn.wenkang365t"
version = "0.1.0"

java {
    withJavadocJar()
    withSourcesJar()
}

repositories {
    mavenLocal()
    mavenCentral()
}

val shiro = "1.5.1"
var jcasbinExtra = "0.1.0"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    api("org.apache.shiro:shiro-spring-boot-web-starter:${shiro}")
    api("cn.wenkang365t:jcasbin-extra:${jcasbinExtra}")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar>() {
    enabled = false
}

tasks.withType<Jar>() {
    enabled = true
}

tasks.withType<JavaCompile>() {
    options.encoding = "utf-8"
}

tasks.withType<Javadoc>() {
    options.encoding = "utf-8"
}

tasks.withType<Test>() {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            pom {
                name.set("shiro-jcasbin-spring-boot-starter")
                description.set("Apache Shiro and Casbin :: Support :: Spring Boot Web")
            }
        }
    }
}
