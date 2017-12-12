package stormTP.core

import org.json4s.JsonAST.{JArray, JObject, JString}
import org.json4s.JsonDSL._
import org.json4s.native.JsonMethods._

/**
  * Classe regroupant les fonctionnalités nécessaires au traitement des flux des coureurs
  */
class TortoiseManager(dossard: Long, nomsBinome: String) extends Serializable {

  /**
    * Permet de filtrer les informations concernant votre tortue
    *
    * @param input Une chaîne de caractère contenant un document comme montré dans l'énoncé en section 3.1
    * @return les information sur votre tortue
    */
  def filter(input: String): Runner = {
    val tortoise: Runner = null
    //TODO: compléter
    tortoise
  }

  /**
    * Permet de calculer le rang de votre tortue dans la course
    *
    * @param id
    * @param top
    * @param nom
    * @param nbDevant
    * @param nbDerriere
    * @param total
    * @return les information sur la tortue mises à jour avec le rang.
    */
  def computeRank(id: Long, top: Long, nom: String, nbDevant: Int, nbDerriere: Int, position: Int, total: Int): Runner = {
    val tortoise: Runner = null
    //TODO: compléter
    tortoise
  }

  /**
    * Permet de calculer les points bonus d'une tortue
    *
    * @param rang  le rang de la tortue
    * @param total le nombre total de participants
    * @return
    */
  def computePoints(rang: String, total: Int): Int = {
    //TODO: compléter
    -1
  }

  /**
    * Permet de calculer la vitesse d'une tortue
    *
    * @param topInit début de la plage
    * @param topFin  fin de la plage
    * @param posInit position initiale
    * @param posFin  position finale
    * @return la vitesse
    */
  def computeSpeed(topInit: Long, topFin: Long, posInit: Int, posFin: Int): Double = {
    //TODO: à compléter
    0.0
  }

  /**
    * Permet de calculer le rang moyen d'une tortue
    *
    * @param rangs la liste des rangs, sous forme de châine de caractères
    * @return le rang moyen
    */
  def giveAverageRank(rangs: Array[String]): Int = {
    //TODO: à compléter
    -1
  }

  /**
    * Permet de calculer l'évolution du rang moyen d'une tortue
    *
    * @param cavg rang courant
    * @param pavg rang précédent
    * @return une chaîne parmi les constantes CONST, PROG et REGR
    */
  def giveRankEvolution(cavg: Int, pavg: Int): String = {
    //TODO: à compléter
    ""
  }

  /**
    * Permet de calculer le podium
    *
    * @param input objet JSON correspondant aux observations de la course
    * @return objet JSON correspondant au podium
    */
  def getPodium(input: String): String = {
    // penser à utiliser les fonctions de JSONUtils
    val P1: Array[String] = null // TODO: changer la valeur
    val P2: Array[String] = null // TODO: changer la valeur
    val P3: Array[String] = null // TODO: changer la valeur
    val marcheP1 = JArray(P1.map(s => JObject(List(("nom", JString(s))))).toList)
    val marcheP2 = JArray(P2.map(s => JObject(List(("nom", JString(s))))).toList)
    val marcheP3 = JArray(P3.map(s => JObject(List(("nom", JString(s))))).toList)
    val json =
      ("top" -> -1L) ~ // TODO: changer la valeur
        ("marcheP1" -> marcheP1) ~
        ("marcheP2" -> marcheP2) ~
        ("marcheP3" -> marcheP3)
    compact(render(json))
  }


  /**
    * S'assure que le podium est complet
    *
    * @param runners
    * @return
    */
  def computePodium(runners: Array[Runner]): Array[Array[String]] = {
    val ranks = runners.groupBy(r => r.nbDevant)
    val rankSortedKeys = ranks.keys.toArray.sortWith((a, b) => a < b)
    rankSortedKeys.map(k => ranks(k).map(r => r.nom))
  }
}
