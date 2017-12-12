package stormTP.operator

import java.util

import org.apache.storm.task.{OutputCollector, TopologyContext}
import org.apache.storm.topology.{IRichBolt, OutputFieldsDeclarer}
import org.apache.storm.tuple.{Fields, Tuple}
import stormTP.stream.StreamEmiter

class ExitBolt(private val port: Int, ip: String) extends IRichBolt {
  private var collector: OutputCollector = null
  val semit = new StreamEmiter(port, ip)

  override def execute(input: Tuple) = {
    val data = input.getValueByField("json").toString
    semit.send(data)
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
