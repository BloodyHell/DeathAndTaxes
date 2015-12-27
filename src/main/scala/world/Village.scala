package world

import akka.actor._


object Village {

  case class ConstructBuilding(building: String)
  case class BuildingConstructed(building: Props)

  def props(): Props = Props(new Village)
}

class Village extends Actor with ActorLogging {
  import Village._

  val builder = context.actorOf(Builder.props(self))

  def receive: Receive = {
    case ConstructBuilding(name) => builder ! ConstructBuilding(name)
    case BuildingConstructed(buildingProps) => context.actorOf(buildingProps)
    case _ => log.info("We received a message!")
  }
}

object Builder {

  sealed trait State

  object State {

    case object Idle extends State

    case object Building extends State

  }

  case object BuildingBuilt

  case class Data(building: Option[String] = None)

  def props(village: ActorRef): Props = Props(new Builder(village))
}

class Builder(village: ActorRef)
  extends Actor with FSM[Builder.State, Builder.Data] with ActorLogging {

  import Builder.State._
  import Builder._

  startWith(Idle, Data())

  when(State.Idle) {
    case Event(Village.ConstructBuilding(building), data) =>
      log.info(s"Going to build $building")
      goto(State.Building) using data.copy(building = Some(building))
  }

  when(State.Building) {
    case Event(BuildingBuilt, Data(Some(building))) =>
      createBuilding(building)
      goto(State.Idle) using Data(None)
  }

  onTransition {
    case State.Building -> State.Idle => log.info("Building finished, going back to being Idle!")
    case State.Idle -> State.Building => log.info("Wake up lazies! Work is to be done.")
  }

  private def createBuilding(building: String) {
    wait(5000)
    village ! Village.BuildingConstructed(world.Building.props(s"$building-${village.path.name}"))
  }
}

object Building {
  def props(name: String): Props = Props(new Building(name))
}

class Building(name: String) extends Actor with ActorLogging {

  override def receive: Actor.Receive = ???
}
