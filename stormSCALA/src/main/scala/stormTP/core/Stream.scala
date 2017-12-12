package stormTP.core

import scala.util.Random

class Stream(private val names: Array[String]) extends Serializable {
  private val nbCells = 254
  private val nbRunners: Int = names.length
  private var runnersPos = new Array[Int](nbRunners)
  private var runners = new Array[String](nbRunners)

  def getMessage(top: Long): String = {
    "{ \"rabbits\":[ \n" +
      getNewRow(top).reduce(_ + ", \n" + _) +
      "\n ] }"
  }

  def getNewRow(top: Long): Array[String] = {
    var tmp = ""
    val alea = new Random(System.currentTimeMillis())
    // affectation des nouvelles positions (soit +1 soit rien)
    for (i <- 0 until nbRunners) {
      if (runnersPos(i) < nbCells) {
        runnersPos(i) = runnersPos(i) + alea.nextInt(10) % nbCells
      }
    }
    // affectation des rangs
    for (i <- 0 until nbRunners) {
      if (runnersPos(i) < nbCells) {
        val cptAfter = runnersPos.count(pos => pos > runnersPos(i))
        val cptBefore = runnersPos.count(pos => pos < runnersPos(i))
        val rabbit = Runner(i, names(i), cptBefore, cptAfter, nbRunners, runnersPos(i), top)
        runners(i) = rabbit.getJSON_V1()
      }
    }
    runners
  }
}
