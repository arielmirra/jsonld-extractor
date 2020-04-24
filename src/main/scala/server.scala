object server extends cask.MainRoutes {
  @cask.get("/container")
  def hello(): String = {
    "Hello World!"
  }

  @cask.get("/container/:resource")
  def doThing(resource: String): String = {
    s"the resource is $resource"
  }

  initialize()
}