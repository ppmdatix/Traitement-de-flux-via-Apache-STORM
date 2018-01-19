package stormTP.operator.TP2_operators

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
    val to = JSONUtils.getList(n_jvalue, "rabbits")
    val rabbit1 = to.filter(p => JSONUtils.getInt(p, "nbDevant") == 0)
    val t1 = rabbit1.size
    val rabbit2 = to.filter(p => JSONUtils.getInt(p, "nbDevant") ==  t1)
    val t2 = rabbit2.size
    val rabbit3 = to.filter(p => JSONUtils.getInt(p, "nbDevant") == t1 + t2)
    val t3 = rabbit3.size
    val top = JSONUtils.getLong(rabbit1(0),"top")

    val marcheP1 = rabbit1.map(a => ("nom", JSONUtils.getString(a,"nom")))
    val marcheP2 = rabbit2.map(a => ("nom", JSONUtils.getString(a,"nom")))
    val marcheP3 = rabbit3.map(a => ("nom", JSONUtils.getString(a,"nom")))




    collector.emit(input,new Values(top:java.lang.Long,
      marcheP1:List[(String, String)], marcheP2:List[(String, String)], marcheP3:List[(String, String)]))
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