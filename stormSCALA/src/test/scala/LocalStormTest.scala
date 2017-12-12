import org.apache.storm.LocalCluster
import org.scalatest.{BeforeAndAfter, FunSuite}

/**
  * Classe de base à étendre pour réaliser des test avec un cluster storm local,
  * cf EmptyStormTest qui fait un test vide.
  */
class LocalStormTest extends FunSuite with BeforeAndAfter {
  var cluster: LocalCluster = null

  before {
    cluster = new LocalCluster()
  }

  after {
    cluster.shutdown()
  }
}
