package stormTP.topology

import org.apache.storm.topology.TopologyBuilder
import org.apache.storm.topology.base.BaseWindowedBolt.Count
import org.apache.storm.{Config, StormSubmitter}
import stormTP.operator.{MasterInputStreamSpout}
import stormTP.operator.TP1_operators.{MyTortoiseBolt,GiveRankBolt,Exit6Bolt,RankEvolutionBolt}
//import stormTP.operator.TP1_operators.

object TopologyT6 {
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
    builder.setBolt("GiveRankBolt",   new GiveRankBolt(),   nbExecutors).shuffleGrouping("MyTortoiseBolt");
    builder.setBolt("RankEvolutionBolt",   new RankEvolutionBolt().withTumblingWindow(new Count(10)).withMessageIdField("top"),   nbExecutors).shuffleGrouping("GiveRankBolt");

    /*Affectation à la topologie du bolt qui émet le flux de sortie, il prendra en input le bolt nofilter*/
    builder.setBolt("exit", new Exit6Bolt(portOUTPUT, ipmOUTPUT), nbExecutors).shuffleGrouping("RankEvolutionBolt");

    /*Création d'une configuration*/
    val config = new Config();
    /*La topologie est soumise à STORM*/
    StormSubmitter.submitTopology("topo6", config, builder.createTopology());
  }

}
