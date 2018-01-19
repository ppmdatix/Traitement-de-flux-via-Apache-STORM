package stormTP.operator.TP1_operators

import java.util

import org.apache.storm.task.{OutputCollector, TopologyContext}
import org.apache.storm.topology.OutputFieldsDeclarer
import org.apache.storm.topology.base.BaseWindowedBolt
import org.apache.storm.tuple.{Fields, Values, Tuple}
import org.apache.storm.windowing.TupleWindow
import org.json4s.JsonDSL._
import org.json4s.native.JsonMethods.{compact, render}
import stormTP.{JSONUtils, TupleUtil}

class SpeedBolt extends BaseWindowedBolt {
  private var collector: OutputCollector = null

  override def execute(inputWindow: TupleWindow) = {
    val tuples: util.List[Tuple] = inputWindow.get()
    var head = tuples.get(0)
    var tail = tuples.get(inputWindow.get().size().toInt-1.toInt)
    var head_pos = TupleUtil.intValue(head, "position")
    var tail_pos = TupleUtil.intValue(tail, "position")
    var speed = ((tail_pos.toFloat-head_pos.toFloat)/(inputWindow.get().size().toFloat)).toString

    val id = TupleUtil.longValue(head,"id")
    val top_1 = TupleUtil.longValue(head,"top").toString
    val top_2 = TupleUtil.longValue(tail,"top").toString
    val top = top_1.concat("-".concat(top_2))
    val nom = TupleUtil.stringValue(head,"nom")

    collector.emit(inputWindow.get(),new Values(id:java.lang.Long,
      nom:java.lang.String,top:java.lang.String,
      speed:java.lang.String))
  }

  override def declareOutputFields(declarer: OutputFieldsDeclarer) = {
    declarer.declare(new Fields("id" ,"nom" ,"top" ,"speed"))
  }

  override def prepare(stormConf: util.Map[_, _], context: TopologyContext, collector: OutputCollector) = {
    this.collector = collector
  }
}
