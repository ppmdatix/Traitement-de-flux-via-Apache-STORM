package stormTP.operator.TP1_operators

import java.util

import org.apache.storm.task.{OutputCollector, TopologyContext}
import org.apache.storm.topology.OutputFieldsDeclarer
import org.apache.storm.topology.base.BaseWindowedBolt
import org.apache.storm.tuple.{Fields, Values}
import org.apache.storm.windowing.TupleWindow
import org.json4s.JsonDSL._
import org.json4s.native.JsonMethods.{compact, render}
import stormTP.{JSONUtils, TupleUtil}

class SpeedBolt extends BaseWindowedBolt {
  private var collector: OutputCollector = null

  override def execute(inputWindow: TupleWindow) = {

    if ( TupleUtil.longValue(inputWindow,"top") % 5 == 0) {
      val cpt = inputWindow.get().size()
      var head = inputWindow.get().slice(0,1).toList.headOption
      var tail = inputWindow.get().slice(9,10).toList.headOption
      var head_pos = TupleUtil.longValue(head, 'position')
      var tail_pos = TupleUtil.longValue(tail, 'position')
      var speed = ((tail_pos.toFloat-head_pos.toFloat)/(10.toFloat)).toString


      //var head_pos = TupleUtil.longValue(inputWindow.get().head()), 'position')
      //var tail = TupleUtil.longValue(inputWindow.get().tail()), 'position')
      val id = TupleUtil.longValue(head,"id")
      val top_1 = TupleUtil.longValue(head,"top").toString
      val top_2 = TupleUtil.longValue(tail,"top").toString
      val top = top_1.concat('-'.concat(top_2))
      val nom = TupleUtil.stringValue(input,"nom")

      collector.emit(input,new Values(id:java.lang.Long,
        nom:java.lang.String,top:java.lang.String,
        speed:java.lang.String))
    }
  }

  override def declareOutputFields(declarer: OutputFieldsDeclarer) = {
    declarer.declare(new Fields("id" ,"nom" ,"top" ,"speed"))
  }

  override def prepare(stormConf: util.Map[_, _], context: TopologyContext, collector: OutputCollector) = {
    this.collector = collector
  }
}
