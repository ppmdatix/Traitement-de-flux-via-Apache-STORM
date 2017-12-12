package stormTP.operator

import java.util

import org.apache.storm.task.{OutputCollector, TopologyContext}
import org.apache.storm.topology.{IRichBolt, OutputFieldsDeclarer}
import org.apache.storm.tuple.{Fields, Tuple, Values}

class NothingBolt extends IRichBolt {
  var collector: OutputCollector = null

  override def execute(tuple: Tuple): Unit = {
    val n = tuple.getValueByField("json").toString
    collector.emit(tuple, new Values(n))
  }

  override def declareOutputFields(outputFieldsDeclarer: OutputFieldsDeclarer): Unit = {
    outputFieldsDeclarer.declare(new Fields("json"))
  }

  override def prepare(map: util.Map[_, _], topologyContext: TopologyContext, outputCollector: OutputCollector): Unit = {
    collector = outputCollector
  }

  override def getComponentConfiguration: util.Map[String, AnyRef] = null

  override def cleanup(): Unit = ()
}
