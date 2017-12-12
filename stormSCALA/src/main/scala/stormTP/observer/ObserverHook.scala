package stormTP.observer

import java.util

import org.apache.storm.hooks.BaseTaskHook
import org.apache.storm.hooks.info._
import org.apache.storm.task.TopologyContext
import org.slf4j.LoggerFactory
import stormTP.stream.StreamEmiter

class ObserverHook() extends BaseTaskHook with Serializable {
  private val logger = LoggerFactory.getLogger(getClass)
  logger.info("Initialization of the sample hook")
  var context: TopologyContext = null
  var initTimestamp = 0L
  var nbT = 0

  override def prepare(conf: util.Map[_, _], newContext: TopologyContext): Unit = {
    context = newContext
    logger.info("This method is automatically when a Hook is enabled (activation of the topology)")
    logger.info("To attach this Hook to any component (spout/bolt), " +
      "simply add the following line in the prepare() method of the component")
    logger.info("context.addTaskHook(new SampleHook())")
    logger.info("it works without any other configuration #Magic")
    val taskId = 0
    context.getComponentId(taskId)
  }

  override def cleanup(): Unit = {
    logger.info("This method is automatically called each time the Hook is turned off " +
      "(deactivation of the the topology of reaffectation of executors)")
  }

  override def emit(info: EmitInfo): Unit = {
    logger.info("This method is called automatically each time a component emits a new tuple (acked or not)")
    val outTasks = info.outTasks // Returns identifiers of destination tasks
    val stream = info.stream // Returns the name of the stream where the tuple was emitted on
    val taskId = info.taskId //Returns the taskId of the emitting task
    val values = info.values //Returns the emitted tuple under the form of a list, the order matchs with the declared fields
    if (this.initTimestamp == 0) this.initTimestamp = System.currentTimeMillis
    val st = new StreamEmiter(9003, "226.0.0.198")
    this.nbT += values.size
    st.send(values.size + " / " + this.nbT + " tuple(s) emited")
  }

  override def spoutAck(info: SpoutAckInfo): Unit = {
    logger.info("This method is called automatically each time a spout acks a tuple " +
      "(i.e. a tuple reaches an exit bolt without failure)")
    val taskId = info.spoutTaskId // Returns the taskId of the acking spout
    val latency = info.completeLatencyMs // Returns the complete time spent by the tuple within the topology
    val messageId = info.messageId // Returns the id of the tuple (the one defined at the emission from the spout)
  }

  override def spoutFail(info: SpoutFailInfo): Unit = {
    logger.info("This method is called automatically each time a tuple failed to be processed within a given duration" +
      " (per default 30sec)")
    val taskId = info.spoutTaskId // Returns the taskId of the acking spout
    val latency = info.failLatencyMs // Returns the complete time spent by the tuple within the topology
    val messageId = info.messageId // Returns the id of the tuple (the one defined at the emission from the spout)
    val st = new StreamEmiter(9003, "226.0.0.198")
    st.send("Tuple failed in spout after " + (this.initTimestamp - System.currentTimeMillis) + " ms")
  }

  override def boltExecute(info: BoltExecuteInfo): Unit = {
    logger.info("This method is called automatically each time a tuple is processed " +
      "(it does not include time spent in pending queues)")
    val taskId = info.executingTaskId // Returns the taskId of the executing bolt
    val latency = info.executeLatencyMs // Returns the execution time of the tuple
    val tuple = info.tuple // Returns the executed tuple
  }

  override def boltAck(info: BoltAckInfo): Unit = {
    logger.info("This method is called automatically each time a bolt acks a tuple " +
      "(i.e. an explicit call to the method collector.ack(Tuple)")
    val taskId = info.ackingTaskId // Returns the taskId of the acking bolt
    val latency = info.processLatencyMs // Returns the processing time of the tuple (execution + emission of the associated result)
    val tuple = info.tuple // Returns the acked tuple
  }

  override def boltFail(info: BoltFailInfo): Unit = {
    logger.info("This method is called automatically each time a bolt failed a tuple " +
      "(i.e. the tuple exceeds its Time-To-Live within the current operator)")
    val taskId = info.failingTaskId // Returns the taskId of the acking bolt
    val latency = info.failLatencyMs // Returns the processing time of the tuple (execution + emission of the associated result) at failure
    val tuple = info.tuple // Returns the failed tuple
    val st = new StreamEmiter(9003, "226.0.0.198")
    st.send("Tuple " + tuple.getLongByField("id") + " failed after " + (this.initTimestamp - System.currentTimeMillis) + " ms")
  }
}
