package stormTP.operator.TP1_operators

import java.util

import org.apache.storm.task.{OutputCollector, TopologyContext}
import org.apache.storm.topology.{IRichBolt, OutputFieldsDeclarer}
import org.apache.storm.tuple.{Fields, Tuple,Values}
import stormTP.TupleUtil

class GiveRankBolt extends IRichBolt {
  private var collector: OutputCollector = null

  override def prepare(stormConf: util.Map[_, _], context: TopologyContext, collector: OutputCollector) = {
    this.collector = collector
  }

  override def execute(input: Tuple) = {
    val nbDevant = TupleUtil.intValue(input, "nbDevant")
    val nbDerriere = TupleUtil.intValue(input, "nbDerriere")
    val total = TupleUtil.intValue(input, "nbTotal")
    val rang_intermediaire: String = (nbDevant + 1).toString
    val id = TupleUtil.longValue(input, "id")
    val nom = TupleUtil.stringValue(input,"nom")
    val top = TupleUtil.longValue(input,"top")
    val rang = if ( nbDevant + nbDerriere != (total-1)){
      rang_intermediaire.concat("ex")
    }
    else{
      rang_intermediaire
    }

    collector.emit(input,new Values(id:java.lang.Long,
      top:java.lang.Long, nom:java.lang.String,
      rang:java.lang.String, total:java.lang.Integer))

  }

  override def cleanup() = ()

  override def getComponentConfiguration = null

  override def declareOutputFields(declarer: OutputFieldsDeclarer) = {
    declarer.declare(new Fields("id" ,"top" ,"nom" ,"rang" ,"nbTotal"))
  }
}
