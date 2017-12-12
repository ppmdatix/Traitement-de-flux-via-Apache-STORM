package stormTP.core

import org.scalatest.FunSuite

class TortoiseManagerTest extends FunSuite {

  /**
    * Test pour la question 1.
    */
  test("testTortoiseFilter") {
    val dossard = 1
    val nomsBinome = "Toto-Titi"
    val tm = new TortoiseManager(dossard, nomsBinome)
    val input = TestData.sampleData1
    val output = "{\"id\":1,\"top\":896,\"nom\":\"" + nomsBinome + "\",\"position\":189,\"nbDevant\":6,\"nbDerriere\":3,\"total\":10}"
    val result = tm.filter(input).getJSON_V1()
    assert(result == output)
  }


  /**
    * Test1 pour question 2
    */
  test("test1TortoisecomputeRank") {
    val dossard = 1
    val nomsBinome = "Toto-Titi"
    val tm = new TortoiseManager(dossard, nomsBinome)
    val input = "{\"id\":1,\"top\":896,\"nom\":\"" + nomsBinome + "\",\"position\":189,\"nbDevant\":6,\"nbDerriere\":3,\"total\":10}"
    val output = "{\"id\":1,\"top\":896,\"nom\":\"" + nomsBinome + "\",\"rang\":\"7\",\"nbTotal\":10}"
    val result = tm.computeRank(1, 896, nomsBinome, 6, 3, 189, 10).getJSON_V2()
    assert(result == output)
  }

  /**
    * Test2 pour question 2
    */
  test("test2TortoisecomputeRank") {
    val dossard = 1
    val nomsBinome = "Toto-Titi"
    val tm = new TortoiseManager(dossard, nomsBinome)
    val input = "{\"id\":3,\"top\":3,\"nom\":\"" + nomsBinome + "\",\"position\":2,\"nbDevant\":1,\"nbDerriere\":4,\"total\":10}"
    val output = "{\"id\":3,\"top\":3,\"nom\":\"" + nomsBinome + "\",\"rang\":\"2ex\",\"nbTotal\":10}"
    val result = tm.computeRank(3, 3, nomsBinome, 1, 4, 189, 10).getJSON_V2
    assert(result == output)
  }

  /**
    * Test1 pour question 3
    */
  test("test1TortoisecomputePoints") {
    val dossard = 1
    val nomsBinome = "Toto-Titi"
    val tm = new TortoiseManager(dossard, nomsBinome)
    val input = "(\"4\", 10)"
    val output = tm.computePoints("4", 10)
    val result = 6
    assert(result == output)
  }

  /**
    * Test2 pour question 3
    */
  test("test2TortoisecomputePoints") {
    val dossard = 1
    val nomsBinome = "Toto-Titi"
    val tm = new TortoiseManager(dossard, nomsBinome)
    val input = "(\"2ex\", 10)"
    val output = tm.computePoints("2ex", 10)
    val result = 8
    assert(result == output)
  }

  /**
    * Test pour question 4
    */
  test("testTortoiseSpeed") {
    val dossard = 1
    val nomsBinome = "Toto-Titi"
    val tm = new TortoiseManager(dossard, nomsBinome)
    val input = "(20, 35, 10, 17)"
    val output = tm.computeSpeed(20, 35, 10, 17)
    val result = 0.46
    assert(Math.abs(result - output) < 0.01)
  }

  /**
    * Test1 pour question 5
    */
  test("test1TortoiseRankEvolution") {
    val dossard = 1
    val nomsBinome = "Toto-Titi"
    val tm = new TortoiseManager(dossard, nomsBinome)
    val input = "2, 2"
    val output = tm.giveRankEvolution(2, 2)
    val result = Constants.CONST
    assert(result == output)
  }

  /**
    * Test2 pour question 5
    */
  test("test2TortoiseRankEvolution") {
    val dossard = 1
    val nomsBinome = "Toto-Titi"
    val tm = new TortoiseManager(dossard, nomsBinome)
    val input = "1, 3"
    val output = tm.giveRankEvolution(1, 3)
    val result = Constants.PROG
    assert(result == output)
  }

  /**
    * Test3 pour question 5
    */
  test("test3TortoiseRankEvolution") {
    val dossard = 1
    val nomsBinome = "Toto-Titi"

    val tm = new TortoiseManager(dossard, nomsBinome)

    val input = "6, 2"


    System.out.println("@Test test3TortoiseRankEvolution(" + input + ")")

    System.out.println("input: " + input)

    val output = tm.giveRankEvolution(6, 2)
    val result = Constants.REGR


    System.out.println("output: " + output)
    System.out.println("result: " + result)
    System.out.println()

    assert(result == output)
  }

  /**
    * Test4 pour question 5
    */
  test("testTortoiseAverageRank") {
    val dossard = 1
    val nomsBinome = "Toto-Titi"

    val tm = new TortoiseManager(dossard, nomsBinome)

    val input = new Array[String](6)
    input(0) = "2"
    input(1) = "1ex"
    input(2) = "3"
    input(3) = "4ex"
    input(4) = "2ex"
    input(5) = "1"


    System.out.println("@Test testGiveAverageRank()")

    System.out.println("input: " + input)

    val output = tm.giveAverageRank(input)
    val result = 2


    System.out.println("output: " + output)
    System.out.println("result: " + result)
    System.out.println()

    assert(result == output)
  }


  /**
    * Test pour partie 4
    */
  test("testPodium") {
    val input = TestData.sampleData2
    val input2 = TestData.sampleData3
    val output = "{\"top\":896,\"marcheP1\":[{\"nom\":\"Basil\"}],\"marcheP2\":[{\"nom\":\"Coco\"}],\"marcheP3\":[{\"nom\":\"Panpan\"}]}"
    val output2 = "{\"top\":123,\"marcheP1\":[{\"nom\":\"Coco\"}],\"marcheP2\":[{\"nom\":\"LapinBlanc\"},{\"nom\":\"Panpan\"}],\"marcheP3\":[{\"nom\":\"Jojo\"}]}"
    val output2b = "{\"top\":123,\"marcheP1\":[{\"nom\":\"Coco\"}],\"marcheP2\":[{\"nom\":\"Panpan\"},{\"nom\":\"LapinBlanc\"}],\"marcheP3\":[{\"nom\":\"Jojo\"}]}"
    System.out.println("@Test testPodium()")

    System.out.println("input: " + input)
    val tm = new TortoiseManager(1, "toto")
    val result = tm.getPodium(input)


    System.out.println("output: " + output)
    System.out.println("result: " + result)
    System.out.println()

    assert(result == output)

    System.out.println("@Test2 testPodium()")

    System.out.println("input: " + input2)

    val result2 = tm.getPodium(input2)


    System.out.println("output: " + output2)
    System.out.println("result: " + result2)
    System.out.println()

    assert(result2 == output2 || result2 == output2b)
  }

}
