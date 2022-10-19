plugins {
  id("maven-publish")
  id("java-library")
}

group = "com.example"
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}
repositories {
  mavenCentral()
}

dependencies {
  api("org.javamoney:moneta:1.1")
  api("com.fasterxml.jackson.core:jackson-databind:2.9.9")

  testImplementation(enforcedPlatform("org.junit:junit-bom:5.4.0")) // JUnit 5 BOM
  testImplementation("org.junit.jupiter:junit-jupiter")
  testImplementation("org.assertj:assertj-core:3.11.1")
}

tasks {
  test {
      useJUnitPlatform()
      reports {
          junitXml.isEnabled = true
          html.isEnabled = true
      }
  }
  val sourcesJar by creating(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
  }

  val javadocJar by creating(Jar::class) {
    dependsOn.add(javadoc)
    archiveClassifier.set("javadoc")
    from(javadoc)
  }

  artifacts {
    archives(sourcesJar)
    archives(javadocJar)
    archives(jar)
  }
}

publishing {
  publications {

    val mavenJava by creating(MavenPublication::class) {
      artifactId = "messages" // verify consistent name
      from(components["java"])
      artifact(tasks["sourcesJar"])
      artifact(tasks["javadocJar"])
    }

  }
  repositories {
    val repositoryPassword: String = System.getenv("CODEARTIFACT_AUTH_TOKEN") ?: ""
    maven {
      url = uri("https://yourartifactrepo.d.codeartifact.us-east-1.amazonaws.com/maven/private-maven/")
      credentials {
        username = "aws"
        password = repositoryPassword
      }
    }
  }
}
