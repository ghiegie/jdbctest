plugins {
    kotlin("jvm") version "1.9.21"
}

group = "org.jdbcconnectiontest"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    implementation("com.microsoft.sqlserver:mssql-jdbc:12.5.0.jre11-preview")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}