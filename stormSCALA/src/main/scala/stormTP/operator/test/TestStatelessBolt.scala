package stormTP.operator.test

import java.util

import org.apache.storm.task.{OutputCollector, TopologyContext}
import org.apache.storm.topology.{IRichBolt, OutputFieldsDeclarer}
import org.apache.storm.tuple.{Fields, Tuple, Values}

class TestStatelessBolt extends IRichBolt {
  private var collector: OutputCollector = null

  override def execute(input: Tuple) = {
    val n = input.getValueByField("json").toString
    collector.emit(input,new Values(n))
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
