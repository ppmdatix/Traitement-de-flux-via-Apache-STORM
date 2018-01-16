package stormTP.operator

import org.apache.storm.task.{OutputCollector, TopologyContext}
import org.apache.storm.topology.{IRichBolt, OutputFieldsDeclarer}
import org.apache.storm.tuple.{Fields, Tuple}
import stormTP.stream.StreamEmiter
import stormTP.TupleUtil

class Exit3Bolt(private val port: Int, ip: String) extends IRichBolt {
  private var collector: OutputCollector = null
  val semit = new StreamEmiter(port, ip)

  override def execute(input: Tuple) = {
    val nbDevant = intValue(input, "nbDevant")
    val nbDerriere = intValue(input, "nbDerriere")
    val total = intValue(input, "nbTotal")
    val rang_intermediaire: String = (nbDevant + 1).toString
    if ( nbDevant + nbDerriere == total){
        val rang = rang_intermediaire.concat("ex")
    }
    else{
      val rang = rang_intermediaire
    }


    val id = "id: ".concat(input.getValueByField("id").toString)
    val top = " ,top: ".concat(input.getValueByField("top").toString)
    val nom = " ,nom: ".concat(input.getValueByField("nom").toString)
    val nbTotal = " ,nbTotal: ".concat(input.getValueByField("id").toString)
    val range = " ,rang: ".concat(rang)


    val result = id.concat(top.concat(nom.concat(range.concat(nbTotal))))

    semit.send(result)
    collector.ack(input)
  }

  override def getComponentConfiguration = null

  override def declareOutputFields(declarer: OutputFieldsDeclarer) = {
    declarer.declare(new Fields("json"))
  }

  override def prepare(stormConf: util.Map[_, _], context: TopologyContext, outputCollector: OutputCollector) = {
    collector = outputCollector
  }

  override def cleanup() = ()
}
