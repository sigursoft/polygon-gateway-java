import org.springframework.boot.gradle.plugin.SpringBootPlugin
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
	java
	idea
	jacoco
    id("org.springframework.boot") version "3.3.5"
}

group = "com.sigursoft"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
	mavenCentral()
}

dependencies {
    implementation(platform(SpringBootPlugin.BOM_COORDINATES))
	// API
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	// Tests
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
    // JUnit 5
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    // Mockito
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.mockito:mockito-junit-jupiter")
    // Wiremock
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.wiremock.integrations.testcontainers:wiremock-testcontainers-module:1.0-alpha-14")
}

tasks.withType<Test> {
    jvmArgs("-Xshare:off", "-XX:+EnableDynamicAgentLoading")
    useJUnitPlatform()
	testLogging.events.addAll(listOf(TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED))
}
