import utils.Database

object server extends cask.MainRoutes {
    @cask.get("/:container")
    def getContainer(container: String): String = {
        Database.getContainer(container)
    }

    @cask.get("/:container/:resource")
    def getResource(container: String, resource: String): String = {
        Database.getContainerResource(container, resource)
    }

    initialize()
}
