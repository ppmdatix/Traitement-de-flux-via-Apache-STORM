package stormTP.operator.test

import java.util

import org.apache.storm.state.KeyValueState
import org.apache.storm.task.{OutputCollector, TopologyContext}
import org.apache.storm.topology.OutputFieldsDeclarer
import org.apache.storm.topology.base.BaseStatefulBolt
import org.apache.storm.tuple.{Fields, Tuple, Values}
import org.json4s.JsonDSL._
import org.json4s.native.JsonMethods._

class TestStatefulBolt extends BaseStatefulBolt[KeyValueState[String, Int]] {
  var kvState: KeyValueState[String, Int] = null
  var sum: Int = 0
  var collector: OutputCollector = null

  private val SUM = "sum"

  override def execute(input: Tuple) = {
    sum = sum + 1
    kvState.put(SUM, sum)
    val json =
      ("test" -> "stateful") ~
        ("nbNewTuples" -> 1) ~
        ("totalNumberOfTuples" -> sum)
    collector.emit(input, new Values(compact(render(json))))
  }

  override def initState(state: KeyValueState[String, Int]) = {
    kvState = state
    sum = kvState.get(SUM, 0)
  }

  override def declareOutputFields(declarer: OutputFieldsDeclarer) = {
    declarer.declare(new Fields("json"))
  }

  override def prepare(stormConf: util.Map[_, _], context: TopologyContext, collector: OutputCollector) = {
    this.collector = collector
  }
}
