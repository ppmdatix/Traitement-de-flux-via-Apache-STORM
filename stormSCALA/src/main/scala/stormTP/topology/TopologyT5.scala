package stormTP.topology

import org.apache.storm.topology.TopologyBuilder
import org.apache.storm.topology.base.BaseWindowedBolt.Count
import org.apache.storm.{Config, StormSubmitter}
import stormTP.operator.{MasterInputStreamSpout}
import stormTP.operator.TP1_operators.{MyTortoiseBolt,SpeedBolt,Exit5Bolt}
//import stormTP.operator.TP1_operators.

object TopologyT5 {
  def main(args: Array[String]): Unit = {
    val nbExecutors = 1
    val portINPUT = 9001
    val portOUTPUT = 9002
    val ipmINPUT = "224.0.0." + args(0)
    val ipmOUTPUT = "225.0." + args(0) + "." + args(1)

    /*Création du spout*/
    val spout = new MasterInputStreamSpout(portINPUT, ipmINPUT);
    val builder = new TopologyBuilder();
    builder.setSpout("masterStream", spout);
    builder.setBolt("MyTortoiseBolt", new MyTortoiseBolt(), nbExecutors).shuffleGrouping("masterStream");
    builder.setBolt("SpeedBolt",   new SpeedBolt().withWindow(new Count(10), new Count(5)),nbExecutors).shuffleGrouping("MyTortoiseBolt");
    builder.setBolt("exit", new Exit5Bolt(portOUTPUT, ipmOUTPUT), nbExecutors).shuffleGrouping("SpeedBolt");
    val config = new Config();
    /*La topologie est soumise à STORM*/
    StormSubmitter.submitTopology("topo5", config, builder.createTopology());
  }

}
