plugins {
    java
    `java-library`
    `maven-publish`
    signing
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

val NEXUS_USERNAME: String by project;
val NEXUS_PASSWORD: String by project;

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()
            from(components["java"])
            pom {
                url.set("https://github.com/fanlide/shiro-jcasbin-spring-boot-starter")
                name.set(project.name)
                description.set("Apache Shiro and Casbin :: Support :: Spring Boot Web")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("./LICENSE")
                    }
                }
                developers {
                    developer {
                        id.set("mufeng")
                        name.set("慕枫")
                        email.set("javakang@qq.com")
                    }
                }
                scm {
                    connection.set("https://github.com/fanlide/shiro-jcasbin-spring-boot-starter.git")
                    url.set(pom.url)
                }
            }
        }
        repositories {
            mavenLocal {
                url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2")
                credentials {
                    username = NEXUS_USERNAME
                    password = NEXUS_PASSWORD
                }
            }
            mavenCentral {
                url = uri("https://oss.sonatype.org/content/repositories/snapshots")
                credentials {
                    username = NEXUS_USERNAME
                    password = NEXUS_PASSWORD
                }
            }
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}
