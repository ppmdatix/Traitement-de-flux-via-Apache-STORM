package stormTP.core

import org.json4s.JsonDSL._
import org.json4s.native.JsonMethods._

/**
  * Classe représentant un coureur 'Tortue' ou 'Lièvre'
  *
  * @param id         identifiant du coureur
  * @param nom        nom du coureur
  * @param nbDevant   nombre de coureurs se trouvant devant le coueur courant dans le classement
  * @param nbDerriere nombre de coureurs se trouvant derrière le coureur courant dans le classement
  * @param total      nombre de coureurs en lice pour la course courante
  * @param position   numéro de cellule sur la piste
  * @param top        numéro d'observation
  */

case class Runner(id: Long, nom: String, nbDevant: Int, nbDerriere: Int, total: Int, position: Int, top: Long) {
  var rang: String = ""

  def getJSON_V1(): String = {
    val jsonRepr =
      ("id" -> id) ~
        ("top" -> top) ~
        ("nom" -> nom) ~
        ("position" -> position) ~
        ("nbDevant" -> nbDevant) ~
        ("nbDerriere" -> nbDerriere) ~
        ("total" -> total)
    compact(render(jsonRepr))
  }

  def getJSON_V2(): String = {
    val jsonRepr =
      ("id" -> id) ~
        ("top" -> top) ~
        ("nom" -> nom) ~
        ("rang" -> rang) ~
        ("total" -> total)
    compact(render(jsonRepr))
  }
}
