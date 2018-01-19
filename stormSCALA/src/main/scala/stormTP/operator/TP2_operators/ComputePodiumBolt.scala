package stormTP.operator

import java.util

import org.apache.storm.task.{OutputCollector, TopologyContext}
import org.apache.storm.topology.{IRichBolt, OutputFieldsDeclarer}
import org.apache.storm.tuple.{Fields, Tuple, Values}
import stormTP.{JSONUtils, TupleUtil}



class ComputePodiumBolt extends IRichBolt {
  private var collector: OutputCollector = null

  override def execute(input: Tuple) = {
    val n = input.getValueByField("json").toString
    val n_jvalue = JSONUtils.parseJson(n)
    val to = JSONUtils.getList(n_jvalue, "tortoises")
    val tortues1 = to.filter(p => JSONUtils.getInt(p, "nbDevant") == 0)
    val t1 = tortues1.size()
    val tortues2 = to.filter(p => JSONUtils.getInt(p, "nbDevant") ==  t1)
    val t2 = tortues2.size()
    val tortues3 = to.filter(p => JSONUtils.getInt(p, "nbDevant") == t1 + t2)
    val t3 = tortues3.size()

    val top = JSONUtils.getLong(tortues1(0),"top")

    val marcheP1 = tortues1.map(a => ("nom", JSONUtils.getLong(a,"nom")))
    val marcheP2 = tortues2.map(a => ("nom", JSONUtils.getLong(a,"nom")))
    val marcheP3 = tortues3.map(a => ("nom", JSONUtils.getLong(a,"nom")))




    collector.emit(input,new Values(top:java.lang.Long,
      marcheP1:util.List[Tuple], marcheP2:v, marcheP1:util.List[Tuple]))
  }

  override def prepare(stormConf: util.Map[_, _], context: TopologyContext, collector: OutputCollector) = {
    this.collector = collector
  }

  override def cleanup() = ()

  override def getComponentConfiguration = null

  override def declareOutputFields(declarer: OutputFieldsDeclarer) = {
    declarer.declare(new Fields("top", "marcheP1", "marcheP2", "marcheP3"))
  }
}