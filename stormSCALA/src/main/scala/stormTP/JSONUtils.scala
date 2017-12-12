package stormTP

import org.json4s.DefaultFormats
import org.json4s.JsonAST.{JArray, JValue}
import org.json4s.native.JsonMethods.parse
import stormTP.core.Runner


object JSONUtils {
  implicit val formats = DefaultFormats

  /**
    * Lit le JSon contenu dans une chaîne de caractères
    *
    * @param json la chaîne contenant le JSON
    * @return la structure représentant le JSon en mémoire
    */
  def parseJson(json: String): JValue = parse(json)

  /**
    * Extrait le champ donné sous forme d'un Long
    *
    * @param jValue la structure représentant le JSon en mémoire
    * @param entry  le nom du champs JSON
    * @return la valeur sous forme de long
    */
  def getLong(jValue: JValue, entry: String): Long = {
    (jValue \ entry).extract[Long]
  }

  /**
    * Extrait le champ donné sous forme d'un Int
    *
    * @param jValue la structure représentant le JSon en mémoire
    * @param entry  le nom du champ JSON
    * @return la valeur sous forme d'Int
    */
  def getInt(jValue: JValue, entry: String): Int = {
    (jValue \ entry).extract[Int]
  }

  /**
    * Extrait le champ donné sous forme d'un String
    *
    * @param jValue la structure représentant le JSon en mémoire
    * @param entry  le nom du champs JSON
    * @return la valeur sous forme de String
    */
  def getString(jValue: JValue, entry: String): String = {
    (jValue \ entry).extract[String]
  }

  /**
    * Extrait le champ donné sous forme d'une liste de structures Json
    *
    * @param jValue la structure représentant le JSon en mémoire
    * @param entry  le nom du champs JSON
    * @return la valeur sous forme d'une liste de structures Json
    */
  def getList(jValue: JValue, entry: String): List[JValue] = {
    (jValue \ entry) match {
      case JArray(l) => l
      case _ => null
    }
  }

  /**
    * Extrait un Runner à partir d'une structure JSON en mémoire
    *
    * @param jValue la structure représentant le JSON en mémoire
    * @return le runner
    */
  def runnerFromJSON(jValue: JValue): Runner = {
    jValue.extract[Runner]
  }

  /**
    * Extrait un Runner à partir d'une chaîne de caractère correspondant à un objet JSON
    *
    * @param json chaîne de caractère représentant le runner
    * @return le runner
    */
  def runnerFromJSON(json: String): Runner = {
    parse(json).extract[Runner]
  }

  /**
    * Lit une structure JSON à partir d'une chaîne de caractères
    */
  def jValueFromJSON(json: String): JValue =
    parseJson(json)

}
