package stormTP.operator

import java.util

import org.apache.storm.task.{OutputCollector, TopologyContext}
import org.apache.storm.topology.{IRichBolt, OutputFieldsDeclarer}
import org.apache.storm.tuple.{Fields, Tuple}
import org.slf4j.LoggerFactory

class ExitInLogBolt extends IRichBolt {
  private val LOG = LoggerFactory.getLogger(getClass.getSimpleName)
  private var collector: OutputCollector = null

  override def execute(input: Tuple) = {
    val n = input.getValueByField("json").toString
    LOG.info("[ExitInLOG] {}", n)
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
