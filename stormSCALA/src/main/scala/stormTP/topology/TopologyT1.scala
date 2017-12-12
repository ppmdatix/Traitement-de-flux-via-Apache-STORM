package stormTP.topology

import org.apache.storm.topology.TopologyBuilder
import org.apache.storm.{Config, StormSubmitter}
import stormTP.operator.{ExitBolt, MasterInputStreamSpout, NothingBolt}

object TopologyT1 {
  def main(args: Array[String]): Unit = {
    val nbExecutors = 1
    val portINPUT = 9001
    val portOUTPUT = 9002
    val ipmINPUT = "224.0.0." + args(0)
    val ipmOUTPUT = "225.0." + args(0) + "." + args(1)

    /*Création du spout*/
    val spout = new MasterInputStreamSpout(portINPUT, ipmINPUT);
    /*Création de la topologie*/
    val builder = new TopologyBuilder();
    /*Affectation à la topologie du spout*/
    builder.setSpout("masterStream", spout);
    /*Affectation à la topologie du bolt qui ne fait rien, il prendra en input le spout localStream*/
    builder.setBolt("nofilter", new NothingBolt(), nbExecutors).shuffleGrouping("masterStream");
    /*Affectation à la topologie du bolt qui émet le flux de sortie, il prendra en input le bolt nofilter*/
    builder.setBolt("exit", new ExitBolt(portOUTPUT, ipmOUTPUT), nbExecutors).shuffleGrouping("nofilter");

    /*Création d'une configuration*/
    val config = new Config();
    /*La topologie est soumise à STORM*/
    StormSubmitter.submitTopology("topoT1", config, builder.createTopology());
  }

}
