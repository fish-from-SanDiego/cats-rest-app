rootProject.name = "tech-labs"
include("cats")
include("catsCommon")
include("catsServices")
include("catsDao")
include("catsPresentation")

project(":catsDao").projectDir = File("cats/dao")
project(":catsCommon").projectDir = File("cats/common")
project(":catsServices").projectDir = File("cats/services")
project(":catsPresentation").projectDir = File("cats/presentation")