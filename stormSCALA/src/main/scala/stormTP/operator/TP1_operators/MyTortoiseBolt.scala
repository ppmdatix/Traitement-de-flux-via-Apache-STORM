package stormTP.operator.test

import java.util

import org.apache.storm.task.{OutputCollector, TopologyContext}
import org.apache.storm.topology.{IRichBolt, OutputFieldsDeclarer}
import org.apache.storm.tuple.{Fields, Tuple, Values}
import stormTP.JSONUtils

class MyTortoiseBolt extends IRichBolt {
  private var collector: OutputCollector = null

  override def execute(input: Tuple) = {
    val n = input.getValueByField("json").toString
    val n_jvalue = JSONUtils.parseJson(n)
    val to = JSONUtils.getList(n_jvalue, "tortoises")
    val matortue = to.filter(p => JSONUtils.getInt(p, "id") == 3)
    val matortuejson = JSONUtils.runnerFromJSON(matortue(0))
    val nom = "Gaydon-Peseux"
    collector.emit(input,new Values(matortuejson.id:java.lang.Long,
      matortuejson.top:java.lang.Long, nom:java.lang.String,
      matortuejson.position:java.lang.Integer, matortuejson.nbDevant:java.lang.Integer,
      matortuejson.nbDerriere:java.lang.Integer,10:java.lang.Integer))
  }

  override def prepare(stormConf: util.Map[_, _], context: TopologyContext, collector: OutputCollector) = {
    this.collector = collector
  }

  override def cleanup() = ()

  override def getComponentConfiguration = null

  override def declareOutputFields(declarer: OutputFieldsDeclarer) = {
    declarer.declare(new Fields("id", "top", "nom", "position", "nbAvant", "nbApres", "nbTotal"))
  }
}