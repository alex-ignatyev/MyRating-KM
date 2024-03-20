//TODO Разобраться что тут происходит
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.BOOLEAN

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.cocoapods)
    alias(libs.plugins.compose)
    alias(libs.plugins.libres)
    alias(libs.plugins.buildConfig)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    android()

    ios()
    iosSimulatorArm64()

    cocoapods {
        version = "1.0.0"
        summary = "Multiplatform app built with Kotlin and Compose"
        homepage = "https://github.com/alex-ignatyev/MyRating-KMP"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")

        framework {
            baseName = "shared"
            isStatic = true
        }
        extraSpecAttributes["resources"] = "['src/commonMain/resources/**', 'src/iosMain/resources/**']"
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                implementation(compose.runtime)

                implementation(libs.compose.imageLoader)
                //implementation(libs.compose.imageLoaderBlur)
                implementation(libs.compose.icons)

                implementation(libs.insetsx)

                implementation(libs.decompose.core)
                implementation(libs.decompose.compose)

                implementation(libs.kviewmodel.core)
                implementation(libs.kviewmodel.compose)

                implementation(libs.odyssey.core)
                implementation(libs.odyssey.compose)

                implementation(libs.voyager.core)
                implementation(libs.voyager.bottomSheet)
                implementation(libs.voyager.tab)
                implementation(libs.voyager.transitions)

                implementation(libs.klock.common)

                implementation(libs.kodein)
                implementation(libs.koin)

                implementation(libs.libres)

                implementation(libs.napier)

                implementation(libs.ktor.core)
                implementation(libs.ktor.negotiation)
                implementation(libs.ktor.serialization)
                implementation(libs.ktor.json)
                implementation(libs.ktor.logging)

                implementation(libs.multiplatformSettings)

                implementation(libs.kstore)

                implementation(libs.kotlin.serialization)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation("com.google.android.material:material:1.9.0")
                implementation("com.google.accompanist:accompanist-systemuicontroller:0.30.1")

                implementation(compose.preview)
                implementation(compose.uiTooling)

                implementation(libs.sqldelight.android)
                implementation(libs.ktor.android)
            }
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by getting {
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation(libs.ktor.ios)
                implementation(libs.sqldelight.ios)
            }
        }
    }
}

buildkonfig {
    packageName = "com.my_rating.shared"

    defaultConfigs {
        buildConfigField(BOOLEAN, "isMocked", "true")
    }
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("")
            schemaOutputDirectory.set(file("src/commonMain/sqldelight/data/schema"))
            migrationOutputDirectory.set(file("src/commonMain/sqldelight/migrations"))
        }
    }
}

libres {
    generatedClassName = "AppRes"
    generateNamedArguments = true
    baseLocaleLanguageCode = "ru"
}

android {
    compileSdk = 34
    namespace = "com.my_rating.shared"

    defaultConfig {
        minSdk = 24
    }

    buildFeatures.compose = true
    composeOptions.kotlinCompilerExtensionVersion = "1.5.10"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
