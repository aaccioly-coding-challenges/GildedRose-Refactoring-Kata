plugins {
    id("java")
    id("com.adarshr.test-logger") version "4.0.0"
    jacoco
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.11.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.assertj:assertj-core:3.26.3")
}


group = "com.gildedrose"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()

    maxHeapSize = "1G"

    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks.register<JavaExec>("texttest") {
    mainClass = "com.gildedrose.TexttestFixture"
    classpath = sourceSets.getByName("test").runtimeClasspath
    args(listOf("30"))
}

val textTestTask = tasks.named("texttest", JavaExec::class).get()

jacoco {
    applyTo(textTestTask)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
}

tasks.register<JacocoReport>("jacocoTextTestReport") {
    dependsOn(textTestTask)
    executionData(textTestTask)
    sourceSets(sourceSets.main.get())
}

// task to combine all coverage reports
tasks.register<JacocoReport>("jacocoCombinedReport") {
    dependsOn(tasks.test)
    dependsOn(textTestTask)
    executionData(
        fileTree(layout.buildDirectory) {
            setIncludes(setOf("jacoco/*.exec"))
        }
    )
    sourceSets(sourceSets.main.get())
}

// common configuration for all jacoco tasks
tasks.withType<JacocoReport> {
    reports {
        csv.required = true
        xml.required = true
    }
}

tasks.register("jacocoAllReports") {
    dependsOn(tasks.jacocoTestReport)
    dependsOn(tasks.named("jacocoTextTestReport"))
    dependsOn(tasks.named("jacocoCombinedReport"))
}

