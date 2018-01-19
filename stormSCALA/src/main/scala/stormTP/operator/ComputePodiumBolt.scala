package stormTP.operator

import java.util

import org.apache.storm.task.{OutputCollector, TopologyContext}
import org.apache.storm.topology.{IRichBolt, OutputFieldsDeclarer}
import org.apache.storm.tuple.{Fields, Tuple}

class ComputePodiumBolt extends IRichBolt { // TODO: à changer si besoin
  private var collector: OutputCollector = null

  override def prepare(stormConf: util.Map[_, _], context: TopologyContext, collector: OutputCollector) = {
    this.collector = collector
  }

  override def execute(input: Tuple) = {
    // TODO: à compléter
  }

  override def cleanup() = ()

  override def getComponentConfiguration = null

  override def declareOutputFields(declarer: OutputFieldsDeclarer) = {
    declarer.declare(new Fields("json")) // TODO: à modifier au besoin
  }
}
