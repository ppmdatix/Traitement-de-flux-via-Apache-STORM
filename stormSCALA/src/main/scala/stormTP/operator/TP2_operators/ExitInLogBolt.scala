package stormTP.operator.TP2_operators

import java.util

import org.apache.storm.task.{OutputCollector, TopologyContext}
import org.apache.storm.topology.{IRichBolt, OutputFieldsDeclarer}
import org.apache.storm.tuple.{Fields, Tuple}
import org.slf4j.LoggerFactory
import stormTP.TupleUtil
import stormTP.stream.StreamEmiter
import org.json4s.JsonDSL._
import org.json4s.native.JsonMethods._


class ExitInLogBolt(private val port: Int, ip: String) extends IRichBolt {
  private val LOG = LoggerFactory.getLogger(getClass.getSimpleName)
  private var collector: OutputCollector = null
  val semit = new StreamEmiter(port, ip)

  override def execute(input: Tuple) = {
    val jsonRepr =
        ("top" -> TupleUtil.longValue(input, "top")) ~
          ("marcheP1" -> TupleUtil.listValue(input,"marcheP1")) ~
          ("marcheP2" -> TupleUtil.listValue(input,"marcheP2")) ~
          ("marcheP3" -> TupleUtil.listValue(input,"marcheP3"))
    semit.send(compact(render(jsonRepr)))
    collector.ack(input)
  }

  override def prepare(stormConf: util.Map[_, _], context: TopologyContext, collector: OutputCollector) = {
    this.collector = collector
  }

  override def cleanup() = ()

  override def getComponentConfiguration = null

  override def declareOutputFields(declarer: OutputFieldsDeclarer) = {
    declarer.declare(new Fields("json"))
  }
}
