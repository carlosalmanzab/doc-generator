plugins {
	java
	id("org.springframework.boot") version "3.2.3"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.formatosprellieminares.api"
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
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.apache.poi:poi:5.2.3")
	implementation("org.apache.poi:poi-ooxml:5.2.3")
	implementation("org.projectlombok:lombok:1.18.30")
	implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	annotationProcessor("org.projectlombok:lombok:1.18.30")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
	
	// Mostrar información detallada de los tests
	testLogging {
		events("passed", "skipped", "failed")
	}
}

// Tarea para ejecutar todos los tests
tasks.register<Test>("runAllTests") {
	group = "verification"
	description = "Ejecuta todos los tests"
	
	useJUnitPlatform()
	testLogging {
		events("passed", "skipped", "failed")
	}
}

// Tarea para ejecutar tests de controladores
tasks.register<Test>("runControllerTests") {
	group = "verification"
	description = "Ejecuta solo los tests de controladores"
	
	useJUnitPlatform()
	filter {
		includeTestsMatching("*Controller*Test")
	}
	testLogging {
		events("passed", "skipped", "failed")
	}
}

// Tarea para ejecutar tests de servicios
tasks.register<Test>("runServiceTests") {
	group = "verification"
	description = "Ejecuta solo los tests de servicios"
	
	useJUnitPlatform()
	filter {
		includeTestsMatching("*Service*Test")
	}
	testLogging {
		events("passed", "skipped", "failed")
	}
}

// Tarea para ejecutar tests de integración
tasks.register<Test>("runIntegrationTests") {
	group = "verification"
	description = "Ejecuta solo los tests de integración"
	
	useJUnitPlatform()
	filter {
		includeTestsMatching("*Integration*Test")
		includeTestsMatching("*EndToEnd*Test")
	}
	testLogging {
		events("passed", "skipped", "failed")
	}
}

// Tarea para ejecutar tests de modelos
tasks.register<Test>("runModelTests") {
	group = "verification"
	description = "Ejecuta solo los tests de modelos"
	
	useJUnitPlatform()
	filter {
		includeTestsMatching("*model.*Test")
	}
	testLogging {
		events("passed", "skipped", "failed")
	}
}

// Tarea para ejecutar un test específico (usar con -PtestClass=NombreDelTest)
tasks.register<Test>("runSingleTest") {
	group = "verification"
	description = "Ejecuta un test específico (usar con -PtestClass=NombreDelTest)"
	
	useJUnitPlatform()
	
	doFirst {
		if (project.hasProperty("testClass")) {
			val className = project.property("testClass").toString()
			println("Ejecutando test específico: $className")
			filter {
				includeTestsMatching("*$className*")
			}
		} else {
			println("Por favor especifica la clase de test usando -PtestClass=NombreDelTest")
			throw GradleException("No se especificó la clase de test")
		}
	}
	
	testLogging {
		events("passed", "skipped", "failed")
	}
}

// Tarea para ejecutar un método de test específico (usar con -PtestMethod=NombreDelMetodo)
tasks.register<Test>("runSingleMethod") {
	group = "verification"
	description = "Ejecuta un método de test específico (usar con -PtestMethod=NombreDelMetodo)"
	
	useJUnitPlatform()
	
	doFirst {
		if (project.hasProperty("testMethod")) {
			val methodName = project.property("testMethod").toString()
			println("Ejecutando método de test específico: $methodName")
			filter {
				includeTestsMatching("*$methodName*")
			}
		} else {
			println("Por favor especifica el método de test usando -PtestMethod=NombreDelMetodo")
			throw GradleException("No se especificó el método de test")
		}
	}
	
	testLogging {
		events("passed", "skipped", "failed")
	}
}
