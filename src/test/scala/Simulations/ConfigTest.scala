package Simulations
import RestCallClient.AkkaHttpClient.{config => configfile}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spray.json._

class ConfigTest extends AnyFlatSpec with Matchers{
  behavior of "Configurations"
  it should "check whether the url starts from https" in {
    configfile.getString("REST.rest_url") should startWith("https://")
  }
  it should "check if time is in correct format" in {
    configfile.getString("REST.time").split(':')  should have length 3
  }
  it should "check if delta time is in correct format" in {
    configfile.getString("REST.delta_time").split(':')  should have length 3
  }

  "Lambda API" should "perform check of logs" in {
    val T = configfile.getString("time")
    val dT = configfile.getString("delta_time")
    val responseAWS = scala.io.Source.fromURL(configfile.getString("aws_url")+"?T="+T+"&dT="+dT)
    val json = responseAWS.mkString.parseJson.asJsObject
    val ans = json.fields("isPresent").toString()
    assert(json.fields("isPresent").toString() == "\"True\"")
  }

}
