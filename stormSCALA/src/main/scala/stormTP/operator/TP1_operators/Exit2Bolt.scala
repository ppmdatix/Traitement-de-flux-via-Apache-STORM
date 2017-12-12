package stormTP.operator.TP1_operators

import java.util

import org.apache.storm.task.{OutputCollector, TopologyContext}
import org.apache.storm.topology.{IRichBolt, OutputFieldsDeclarer}
import org.apache.storm.tuple.{Fields, Tuple}
import stormTP.TupleUtil
import stormTP.stream.StreamEmiter

class Exit2Bolt(private val port: Int, ip: String) extends IRichBolt {
  private var collector: OutputCollector = null
  val semit = new StreamEmiter(port, ip)

  override def execute(data: Tuple) = {
    val id = TupleUtil.longValue(data,"id")
    val nom = TupleUtil.stringValue(data,"nom") // probleme probablement ici, en tout cas lié à nom !!
    val nbDevant = TupleUtil.intValue(data,"nbDevant")
    val nbDerriere = TupleUtil.intValue(data,"nbDerriere")
    val Int = TupleUtil.intValue(data,"Int")
    val total = TupleUtil.intValue(data,"total")
    val position = TupleUtil.intValue(data,"position")
    val top = TupleUtil.longValue(data,"top")
    val runner = new stormTP.core.Runner(id: Long, nom: String, nbDevant: Int, nbDerriere: Int, total: Int, position: Int, top: Long)
    semit.send(runner.getJSON_V1())
    collector.ack(data)
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
