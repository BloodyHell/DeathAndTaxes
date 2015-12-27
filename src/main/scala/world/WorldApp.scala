package world

import akka.actor.ActorSystem

object WorldApp {

  def main(args: Array[String]): Unit = {

    val system = ActorSystem("world-system")

    system.log.info("System started")
    val village = system.actorOf(Village.props())

    village ! Village.ConstructBuilding("Kaas fabriek")
  }
}
