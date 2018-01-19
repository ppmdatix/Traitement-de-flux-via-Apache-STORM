package stormTP.operator.TP1_operators

import java.util

import stormTP.TupleUtil
import org.apache.storm.state.KeyValueState
import org.apache.storm.task.{OutputCollector, TopologyContext}
import org.apache.storm.topology.OutputFieldsDeclarer
import org.apache.storm.topology.base.BaseStatefulWindowedBolt
import org.apache.storm.tuple.{Fields, Values,Tuple}
import org.apache.storm.windowing.TupleWindow
import org.json4s.JsonDSL._
import org.json4s.native.JsonMethods.{compact, render}
import org.slf4j.LoggerFactory

class RankEvolutionBolt extends BaseStatefulWindowedBolt[KeyValueState[String, Int]] {
  private var kvState: KeyValueState[String, Int] = null
  private var collector: OutputCollector = null
  private var LAST_RANG: String = "last_rang"
  private var last_rang: Int = 0
  override def execute(inputWindow: TupleWindow) = {

    val tuples: util.List[Tuple] = inputWindow.get()
    var head = tuples.get(0)
    var tail = tuples.get(inputWindow.get().size()-1)
    var tail_rang = TupleUtil.stringValue(tail, "rang")
    var progression: String = ""

    var tail_rang_int: Int = 0
    if (tail_rang.length() > 2 ){
      tail_rang_int = tail_rang.dropRight(2).toInt
    }else{
      tail_rang_int = tail_rang.toInt
    }
    if(tail_rang_int<last_rang){
      progression = "En progression."
    }else if(tail_rang_int==last_rang){
      progression = "Constant."
    }else{
      progression = "En régression."
    }
    val id = TupleUtil.longValue(head,"id")
    val top_1 = TupleUtil.longValue(head,"top").toString
    val top_2 = TupleUtil.longValue(tail,"top").toString
    val top = top_1.concat("-".concat(top_2))
    val nom = TupleUtil.stringValue(head,"nom")

    last_rang = tail_rang_int+0
    kvState.put(LAST_RANG, last_rang)

    collector.emit(inputWindow.get(),new Values(id:java.lang.Long,
      top:java.lang.String, nom:java.lang.String,
      progression:java.lang.String))
    }
  override def initState(state: KeyValueState[String, Int]) = {
    kvState = state
    last_rang = kvState.get(LAST_RANG, 0)
  }
  override def declareOutputFields(declarer: OutputFieldsDeclarer) = {
    declarer.declare(new Fields("id" ,"top" ,"nom" ,"progression"))
  }
  override def prepare(stormConf: java.util.Map[_, _], context: TopologyContext, collector: OutputCollector) = {
    this.collector = collector
  }
}
