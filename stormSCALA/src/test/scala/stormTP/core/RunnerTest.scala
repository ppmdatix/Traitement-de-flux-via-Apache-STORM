package stormTP.core

import org.json4s.JsonAST.{JInt, JObject}
import org.json4s.native.JsonMethods
import org.scalatest.FunSuite
import stormTP.JSONUtils

class RunnerTest extends FunSuite {

  test("Runner.jsonV1 sould return JSON Data") {
    val r = Runner(1L, "a", 2, 3, 5, 4, 32L)
    val json = r.getJSON_V1
    println(json)
    val jsonParsed = JsonMethods.parse(json)
    jsonParsed match {
      case JObject(l) => {
        assert(l.contains(("id", JInt(1L))))
        assert(JSONUtils.getLong(jsonParsed, "id") == 1L)
      }
      case _ => fail("should be an object")
    }
  }

  test("Serialization / deserialization as a case class") {
    val r = Runner(1L, "a", 2, 3, 5, 4, 32L)
    val json = r.getJSON_V1
    val r2 = JSONUtils.runnerFromJSON(json)
    assert(r == r2)
  }

}
