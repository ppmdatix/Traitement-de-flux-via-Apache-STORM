package stormTP

import org.apache.storm.tuple.Tuple

object TupleUtil {

  /**
    * Extrait une valeur d'un tuple sous la forme d'un Int Scala
    * @param t le tuple
    * @param field le nom du champ à extraire
    * @return la valeur
    */
  def intValue(t: Tuple, field: String): Int = {
    t.getValueByField(field).asInstanceOf[java.lang.Integer].intValue()
  }

  /**
    * Extrait une valeur d'un tuple sous la forme d'un Long Scala
    * @param t le tuple
    * @param field le nom du champ à extraire
    * @return la valeur
    */
  def longValue(t: Tuple, field: String): Long = {
    t.getValueByField(field).asInstanceOf[java.lang.Long].longValue()
  }

  /**
    * Extrait une valeur d'un tuple sous la forme d'un String Scala
    * @param t le tuple
    * @param field le nom du champ à extraire
    * @return la valeur
    */
  def stringValue(t: Tuple, field: String): String = {
    t.getValueByField(field).toString
  }

}
