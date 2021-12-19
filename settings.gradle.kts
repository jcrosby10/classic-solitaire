dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        jcenter() // Warning: this repository is going to shut down soon
    }
}
rootProject.name = "Classic Solitaire"
include(":app")
include(":authentication")
include(":classic-solitaire-data")
include(":gamedata")
include(":ui")