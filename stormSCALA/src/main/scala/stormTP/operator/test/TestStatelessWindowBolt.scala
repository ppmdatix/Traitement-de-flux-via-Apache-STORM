package stormTP.operator.test

import java.util

import org.apache.storm.task.{OutputCollector, TopologyContext}
import org.apache.storm.topology.OutputFieldsDeclarer
import org.apache.storm.topology.base.BaseWindowedBolt
import org.apache.storm.tuple.{Fields, Values}
import org.apache.storm.windowing.TupleWindow
import org.json4s.JsonDSL._
import org.json4s.native.JsonMethods.{compact, render}

class TestStatelessWindowBolt extends BaseWindowedBolt {
  private var collector: OutputCollector = null

  override def execute(inputWindow: TupleWindow) = {
    val cpt = inputWindow.get().size()

    val json =
      ("test" -> "statelessWithWindow") ~
        ("nbNewTuples" -> cpt) ~
        ("totalNumberOfTuples" -> cpt)
    collector.emit(inputWindow.get(), new Values(compact(render(json))))
  }

  override def declareOutputFields(declarer: OutputFieldsDeclarer) = {
    declarer.declare(new Fields("json"))
  }

  override def prepare(stormConf: util.Map[_, _], context: TopologyContext, collector: OutputCollector) = {
    this.collector = collector
  }
}
