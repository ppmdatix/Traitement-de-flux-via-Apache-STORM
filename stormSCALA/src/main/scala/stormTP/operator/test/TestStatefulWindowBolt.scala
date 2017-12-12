package stormTP.operator.test


import org.apache.storm.state.KeyValueState
import org.apache.storm.task.{OutputCollector, TopologyContext}
import org.apache.storm.topology.OutputFieldsDeclarer
import org.apache.storm.topology.base.BaseStatefulWindowedBolt
import org.apache.storm.tuple.{Fields, Values}
import org.apache.storm.windowing.TupleWindow
import org.json4s.JsonDSL._
import org.json4s.native.JsonMethods.{compact, render}
import org.slf4j.LoggerFactory


class TestStatefulWindowBolt extends BaseStatefulWindowedBolt[KeyValueState[String, Int]] {
  val LOG = LoggerFactory.getLogger(getClass)
  private var kvState: KeyValueState[String, Int] = null
  private var collector: OutputCollector = null
  private var sum: Int = 0

  override def execute(inputWindow: TupleWindow) = {
    val cpt = inputWindow.get().size()
    sum = sum + cpt
    kvState.put("sum", sum)

    val json =
      ("test" -> "statefulWithWindow") ~
        ("nbNewTuples" -> cpt) ~
        ("totalNumberOfTuples" -> sum)
    collector.emit(inputWindow.get(), new Values(compact(render(json))))
  }

  override def initState(state: KeyValueState[String, Int]) = {
    kvState = state
    sum = state.get("sum", 0)
    LOG.info("initState with state [{}] current sum [{}]", state, sum)
  }

  override def declareOutputFields(declarer: OutputFieldsDeclarer) = {
    declarer.declare(new Fields("json"))
  }

  override def prepare(stormConf: java.util.Map[_, _], context: TopologyContext, collector: OutputCollector) = {
    this.collector = collector
  }
}
