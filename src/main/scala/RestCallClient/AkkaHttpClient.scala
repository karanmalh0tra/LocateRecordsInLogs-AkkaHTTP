package RestCallClient

import HelperUtils.{CreateLogger, ObtainConfigReference}
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.util.ByteString
import com.typesafe.config.Config
import org.slf4j.Logger

import scala.concurrent.Future
import scala.util.{Failure, Success}

object AkkaHttpClient {
  val logger: Logger = CreateLogger(classOf[AkkaHttpClient.type])
  val config: Config = ObtainConfigReference("REST") match {
    case Some(value) => value
    case None => throw new RuntimeException("Cannot obtain a reference to the config data.")
  }
  val URL: String = config.getString("REST.rest_url")
  val T: String = config.getString("REST.time")
  val dT: String = config.getString("REST.delta_time")

  implicit val system: ActorSystem = ActorSystem() // Akka actors
  import system.dispatcher // "thread pool"

  def sendRequest(): Unit = {
    val request = HttpRequest(
      method = HttpMethods.GET,
      uri = s"$URL?T=$T&dT=$dT"
    )
    val responseFuture: Future[HttpResponse] = Http().singleRequest(request)
    responseFuture
      .onComplete {
        case Success(res) =>
          val HttpResponse(statusCodes, headers, entity, _) = res
          logger.info(entity.toString)
          logger.info(statusCodes.toString)
          logger.info(headers.toString)
          entity.dataBytes.runFold(ByteString(""))(_ ++ _).foreach(body => {
            logger.info(body.utf8String)
          })
          system.terminate()
        case Failure(_) => sys.error("something wrong")
      }
  }

  def main(args: Array[String]): Unit = {
    sendRequest()
  }
}