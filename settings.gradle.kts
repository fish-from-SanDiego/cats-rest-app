rootProject.name = "tech-labs"
include("lab-1")
include("cats")
include("cats:common")
findProject(":cats:common")?.name = "catsCommon"
include("cats:services")
findProject(":cats:services")?.name = "catsServices"
include("cats:dao")
findProject(":cats:dao")?.name = "catsDao"
include("cats:presentation")
findProject(":cats:presentation")?.name = "catsPresentation"
