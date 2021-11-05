import HelperUtils.{CreateLogger, ObtainConfigReference}
import RestCallClient.AkkaHttpClient
import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory

object Orchestrator{
  val logger = CreateLogger(classOf[Orchestrator])

  def runSimulation: Unit = {
    logger.info("Starting with SimulationOne")
    AkkaHttpClient
  }

}
class Orchestrator