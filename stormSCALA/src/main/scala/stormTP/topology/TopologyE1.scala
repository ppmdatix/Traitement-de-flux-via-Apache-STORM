package stormTP.topology

import org.apache.storm.topology.TopologyBuilder
import org.apache.storm.{Config, StormSubmitter}
import stormTP.operator.{ComputePodiumBolt, ExitInLogBolt, HareSpout}

object TopologyE1 {
  def main(args: Array[String]): Unit = {
    val nbExecutors = 1

    val spout = new HareSpout(System.currentTimeMillis())

    /*
     * Declaration of the linear topology
     */
    val builder = new TopologyBuilder
    builder.setSpout("localBigStream", spout)
    builder.setBolt("podium", new ComputePodiumBolt, nbExecutors).shuffleGrouping("localBigStream")
    builder.setBolt("exit", new ExitInLogBolt, nbExecutors).shuffleGrouping("podium")

    /*
     * Configuration of metadata of the topology
     */
    val config = new Config
    config.setDebug(true)
    config.setNumWorkers(1)

    /*
	   * Call to the topology submitter for storm
	   */
    StormSubmitter.submitTopology("topoE1", config, builder.createTopology())
  }
}
