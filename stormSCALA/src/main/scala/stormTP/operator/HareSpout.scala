package stormTP.operator

import java.util

import org.apache.storm.spout.SpoutOutputCollector
import org.apache.storm.task.TopologyContext
import org.apache.storm.topology.{IRichSpout, OutputFieldsDeclarer}
import org.apache.storm.tuple.{Fields, Values}
import org.apache.storm.utils.Time
import org.slf4j.LoggerFactory

class HareSpout(private val initTimestamp: Long) extends IRichSpout {
  private val logger = LoggerFactory.getLogger(getClass)
  var msgId = 0L
  val rabbits: Array[String] = new Array[String](10)
  rabbits(0) = "RogerRabbit"
  rabbits(1) = "BugsBunny"
  rabbits(2) = "Panpan"
  rabbits(3) = "Caerbannog"
  rabbits(4) = "Oswald"
  rabbits(5) = "Jojo"
  rabbits(6) = "Coco"
  rabbits(7) = "JudyHopps"
  rabbits(8) = "LapinBlanc"
  rabbits(9) = "Basil"
  val stream = new stormTP.core.Stream(rabbits)
  var collector: SpoutOutputCollector = null

  override def nextTuple(): Unit = {
    val msg = stream.getMessage(msgId)
    msgId += 1
    collector.emit(new Values(msg), msgId)
    //Utils.sleep(100)
    try {
      Time.sleep(10)
    } catch {
      case e: Throwable => logger.error("Error in nextTuple", e)
    }
  }

  override def open(map: util.Map[_, _], topologyContext: TopologyContext, spoutOutputCollector: SpoutOutputCollector): Unit = {
    collector = spoutOutputCollector
  }

  override def close(): Unit = ()

  override def activate(): Unit = ()

  override def deactivate(): Unit = ()

  override def ack(o: scala.Any): Unit = ()

  override def fail(o: scala.Any): Unit = {
    logger.info("[PodiumFail] Failure (msg num: {}) after {} ms")
  }

  override def declareOutputFields(outputFieldsDeclarer: OutputFieldsDeclarer): Unit = {
    outputFieldsDeclarer.declare(new Fields("json"));
  }

  override def getComponentConfiguration: util.Map[String, AnyRef] = null
}
