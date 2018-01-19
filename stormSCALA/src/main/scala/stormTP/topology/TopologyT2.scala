package stormTP.topology

import org.apache.storm.topology.TopologyBuilder
import org.apache.storm.{Config, StormSubmitter}
import stormTP.operator.{MasterInputStreamSpout}
import stormTP.operator.TP1_operators.{Exit2Bolt, MyTortoiseBolt}
object TopologyT2 {
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
    builder.setSpout("masterStream", spout);
    builder.setBolt("MyTortoiseBolt", new MyTortoiseBolt(), nbExecutors).shuffleGrouping("masterStream");
    builder.setBolt("exit", new Exit2Bolt(portOUTPUT, ipmOUTPUT), nbExecutors).shuffleGrouping("MyTortoiseBolt");

    /*Création d'une configuration*/
    val config = new Config();
    /*La topologie est soumise à STORM*/
    StormSubmitter.submitTopology("topoT2", config, builder.createTopology());
  }

}
