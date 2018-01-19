package stormTP.operator.TP1_operators

import java.util

import org.apache.storm.task.{OutputCollector, TopologyContext}
import org.apache.storm.topology.{IRichBolt, OutputFieldsDeclarer}
import org.apache.storm.tuple.{Fields, Tuple}
import stormTP.TupleUtil
import stormTP.stream.StreamEmiter

class Exit5Bolt(private val port: Int, ip: String) extends IRichBolt {
  private var collector: OutputCollector = null
  val semit = new StreamEmiter(port, ip)

  override def execute(data: Tuple) = {
    val id = TupleUtil.longValue(data,"id")
    val nom = TupleUtil.stringValue(data,"nom")
    val top = TupleUtil.stringValue(data,"top")
    val speed = TupleUtil.stringValue(data,"speed")
    val runner = new stormTP.core.Runner(id: Long, nom: String, 0: Int, 0: Int, 0: Int, 0: Int, 0.toLong: Long)

    runner.speed= speed
    runner.top_string = top

    semit.send(runner.getJSON_V4())
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
