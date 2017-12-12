package stormTP.operator

import java.util
import java.util.NoSuchElementException

import org.apache.storm.spout.SpoutOutputCollector
import org.apache.storm.task.TopologyContext
import org.apache.storm.topology.{IRichSpout, OutputFieldsDeclarer}
import org.apache.storm.tuple.{Fields, Values}
import org.slf4j.LoggerFactory
import stormTP.stream.StreamBuffer

class MasterInputStreamSpout(private val port: Int, private val ip: String) extends IRichSpout {
  val logger = LoggerFactory.getLogger(getClass)
  val sbuff = new StreamBuffer(port, ip)
  var indexTuple = 0
  var collector: SpoutOutputCollector = null

  override def nextTuple(): Unit = {
    sbuff.listenStream()
    try {
      val json = sbuff.readTuple()
      logger.info("{} is recieved !", json)
      collector.emit(new Values(json), indexTuple)
      indexTuple += 1
    } catch {
      case e: NoSuchElementException => {
        try {
          Thread.sleep(1000)
        } catch {
          case e: InterruptedException => logger.error("Error in managing sleep when there is no element", e)
        }
      }
      case e: Throwable => logger.error("Error in nextTuple", e)
    }
  }

  override def open(map: util.Map[_, _], topologyContext: TopologyContext, spoutOutputCollector: SpoutOutputCollector): Unit = {
    collector = spoutOutputCollector
  }

  override def close(): Unit =
    logger.info("StreamSimSpout is being closed")

  override def activate(): Unit =
    logger.info("StreamSimSpout is being activated")

  override def deactivate(): Unit =
    logger.info("StreamSimSpout is being deactivated")

  override def ack(o: scala.Any): Unit = ()

  override def fail(o: scala.Any): Unit = ()

  override def declareOutputFields(outputFieldsDeclarer: OutputFieldsDeclarer): Unit = {
    outputFieldsDeclarer.declare(new Fields("json"))
  }

  override def getComponentConfiguration: util.Map[String, AnyRef] = null
}
