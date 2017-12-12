package stormTP.core

import java.io.{BufferedReader, InputStreamReader}

object TestData {

  def loadAsString(resourceName: String): String = {
    var data = new StringBuilder
    val input = new BufferedReader(
      new InputStreamReader(
        getClass.getResourceAsStream(resourceName)))
    var line: String = null
    do {
      line = input.readLine
      if (line != null) {
        data.append(line)
      }
    } while (line != null)
    data.toString()
  }

  val sampleData1: String = loadAsString("/sample-input1.json")
  val sampleData2: String = loadAsString("/sample-input2.json")
  val sampleData3: String = loadAsString("/sample-input3.json")

}
