plugins {
  val indraVersion = "2.0.5"
  id("net.kyori.indra") version indraVersion
  id("net.kyori.indra.checkstyle") version indraVersion
  id("net.kyori.indra.license-header") version indraVersion
  id("net.kyori.indra.publishing") version indraVersion
  id("net.kyori.indra.publishing.sonatype") version indraVersion
}

group = "net.kyori"
version = "2.0.0-SNAPSHOT"
description = "A library for interacting with the GitHub API."

indra {
  javaVersions {
    target(8)
  }

  github("KyoriPowered", "igloo") {
    ci(true)
  }
  mitLicense()

  configurePublications {
    pom {
      developers {
        developer {
          id.set("kashike")
          timezone.set("America/Vancouver")
        }
      }
    }
  }
}

repositories {
  mavenLocal()
  mavenCentral()
}

dependencies {
  checkstyle("ca.stellardrift:stylecheck:0.1")
  compileOnlyApi("org.checkerframework:checker-qual:3.14.0")
  implementation(platform("com.fasterxml.jackson:jackson-bom:2.12.4"))
  implementation("com.fasterxml.jackson.core:jackson-databind")
  implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
  implementation("com.google.guava:guava:30.1.1-jre")
  implementation("com.google.http-client:google-http-client:1.39.1")
  implementation("com.google.http-client:google-http-client-apache-v2:1.39.1")
  implementation("io.jsonwebtoken:jjwt-api:0.11.2")
  runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.2")
  runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.2")
}

tasks.withType<Jar> {
  manifest.attributes("Automatic-Module-Name" to "net.kyori.igloo")
}
