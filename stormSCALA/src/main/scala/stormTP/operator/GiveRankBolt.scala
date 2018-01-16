package stormTP.operator

import java.util

import org.apache.storm.task.{OutputCollector, TopologyContext}
import org.apache.storm.topology.{IRichBolt, OutputFieldsDeclarer}
import org.apache.storm.tuple.{Fields, Tuple}

class GiveRankBolt extends IRichBolt {
  private var collector: OutputCollector = null

  override def prepare(stormConf: util.Map[_, _], context: TopologyContext, collector: OutputCollector) = {
    this.collector = collector
  }

  override def execute(input: Tuple) = {
    val nbDevant = intValue(input, "nbDevant")
    val nbDerriere = intValue(input, "nbDerriere")
    val total = intValue(input, "nbTotal")
    val rang_intermediaire: String = (nbDevant + 1).toString
    val id = intValue(input, "id")
    val nom = input.getValueByField("nom").toString
    val top = input.getValueByField("top").toString
    if ( nbDevant + nbDerriere == total){
      val rang = rang_intermediaire.concat("ex")
    }
    else{
      val rang = rang_intermediaire
    }

    collector.emit(input,new Values(id:java.lang.Long,
      top:java.lang.Long, nom:java.lang.String,
      rang:java.lang.Integer, total:java.lang.Integer,
      ))

  }

  override def cleanup() = ()

  override def getComponentConfiguration = null

  override def declareOutputFields(declarer: OutputFieldsDeclarer) = {
    declarer.declare(new Fields("id" ,"top" ,"nom" ,"rang" ,"nbTotal"))
  }
}
