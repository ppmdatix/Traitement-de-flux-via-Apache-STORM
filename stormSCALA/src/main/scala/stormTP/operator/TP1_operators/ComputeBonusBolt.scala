package stormTP.operator.TP1_operators

import java.util

import org.apache.storm.state.KeyValueState
import org.apache.storm.task.{OutputCollector, TopologyContext}
import org.apache.storm.topology.OutputFieldsDeclarer
import org.apache.storm.topology.base.BaseStatefulBolt
import org.apache.storm.tuple.{Fields, Tuple, Values}
import org.json4s.JsonDSL._
import org.json4s.native.JsonMethods._
import stormTP.TupleUtil

class ComputeBonusBolt extends BaseStatefulBolt[KeyValueState[String, Int]] {
  var kvState: KeyValueState[String, Int] = null
  var points: Int = 0
  var collector: OutputCollector = null

  private val POINTS = "points"

  override def execute(input: Tuple) = {
    if ( TupleUtil.longValue(input,"top") % 15 == 0) {
      val rang = TupleUtil.stringValue(input,"rang")
      var range = 0
      if (rang.length() > 2 ){
        range = rang.dropRight(2).toInt
      }else{
        range = rang.toInt
      }
      points = points + (10-range)
      kvState.put(POINTS, points)

      val id = TupleUtil.longValue(input,"id")
      val top = TupleUtil.longValue(input,"top")
      val nom = TupleUtil.stringValue(input,"nom")

      collector.emit(input,new Values(id:java.lang.Long,
        top:java.lang.Long, nom:java.lang.String,
        points.toString:java.lang.String))
    }
  }
  override def initState(state: KeyValueState[String, Int]) = {
    kvState = state
    points = kvState.get(POINTS, 0)
  }
  override def declareOutputFields(declarer: OutputFieldsDeclarer) = {
    declarer.declare(new Fields("id" ,"top" ,"nom" ,"points"))
  }
  override def prepare(stormConf: util.Map[_, _], context: TopologyContext, collector: OutputCollector) = {
    this.collector = collector
  }
}
