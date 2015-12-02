package sangria.marshalling

import sangria.util.tag

import scala.annotation.implicitNotFound

@implicitNotFound("Type ${Node} cannot be used as a GraphQL input. Please consider defining an implicit instance of `InputUnmarshaller` for it.")
trait InputUnmarshaller[Node] {
  def getRootMapValue(node: Node, key: String): Option[Node]

  def isMapNode(node: Node): Boolean
  def getMapValue(node: Node, key: String): Option[Node]
  def getMapKeys(node: Node): Traversable[String]

  def isArrayNode(node: Node): Boolean
  def getListValue(node: Node): Seq[Node]

  def isDefined(node: Node): Boolean
  def isScalarNode(node: Node): Boolean
  def isEnumNode(node: Node): Boolean
  def isVariableNode(node: Node): Boolean

  // Scalar values are Scala String, Int, Double, Boolean and Enum values defined in the schema
  def getScalarValue(node: Node): Any
  def getVariableName(node: Node): String

  def render(node: Node): String
}

object InputUnmarshaller {
  def mapVars(args: Map[String, Any]) = tag[ScalaInput](args)
  def mapVars(args: (String, Any)*) = tag[ScalaInput](args.toMap)

  def emptyMapVars = tag[ScalaInput](Map.empty[String, Any])

  implicit def scalaInputUnmarshaller[T] = scalaMarshalling.scalaInputUnmarshaller[T]
}