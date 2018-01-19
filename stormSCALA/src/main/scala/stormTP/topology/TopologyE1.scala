package stormTP.topology

import org.apache.storm.topology.TopologyBuilder
import org.apache.storm.{Config, StormSubmitter}
import stormTP.operator.{HareSpout}
import stormTP.operator.TP2_operators.{ComputePodiumBolt, ExitInLogBolt}
import stormTP.stream.StreamEmiter

object TopologyE1 {
  def main(args: Array[String]): Unit = {
    val nbExecutors = 1
    val portINPUT = 9001
    val portOUTPUT = 9002
    val ipmINPUT = "224.0.0." + args(0)
    val ipmOUTPUT = "225.0." + args(0) + "." + args(1)

    val spout = new HareSpout(System.currentTimeMillis())
    val builder = new TopologyBuilder
    builder.setSpout("localBigStream", spout)
    builder.setBolt("ComputePodiumBolt", new ComputePodiumBolt(), nbExecutors).shuffleGrouping("localBigStream")
    builder.setBolt("ExitInLogBolt", new ExitInLogBolt(portOUTPUT, ipmOUTPUT), nbExecutors).shuffleGrouping("ComputePodiumBolt")

    val config = new Config
    config.setDebug(true)
    config.setNumWorkers(1)
    StormSubmitter.submitTopology("topoE1-gaydon-peseux", config, builder.createTopology())
  }
}
