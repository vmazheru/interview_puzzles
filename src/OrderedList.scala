import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Stack

/*

In a fictional document management system, input documents are structured with weight and headings. 
Lowest weighted headings are the most important. This is a sample document:

H1 All About Birds
H2 Kinds of Birds
H3 The Finch
H3 The Swan
H2 Habitats
H3 Wetlands

From this document we would like to produce an outline using nested ordered lists in HTML, which would like this when rendered:

1. All about Birds
    1. Kinds of Birds
         1. The Finch
         2. The Swan
	 2. Habitats
    			1. Wetlands


*/

object Solution1 {
  
  case class Heading(weight: Int, text: String)
  case class Node(heading: Heading, children: List[Node])

  def main(args: Array[String]) {
    //val headings: Iterator[Heading] = scala.io.Source.stdin.getLines.flatMap(parse)
    
    val headings: Iterator[Heading] = List[Heading](
        Heading(1, "All About Birds"),
        Heading(2, "Kinds of Birds"),
        Heading(3, "The Finch"),
        Heading(3, "The Swan"),
        Heading(2, "Habitats"),
        Heading(3, "Wetlands"),
        Heading(4, "Warm Wetlands"),
        Heading(4, "Cold Wetlands"),
        Heading(1, "All About Humans"),
        Heading(2, "To be continued")
        ).iterator
    
    val outline: Node = toOutline(headings)
    val html: String = toHtml(outline).trim
    println(html)
  }

  def toOutline(headings: Iterator[Heading]): Node = {
    val root = Heading(0, "")
    var curr = root
    var next = headings.next
    var movingUp = false
    
    def moveNext(): Boolean = {
      if (movingUp) return true;
      curr = next
      if (!headings.hasNext) {
        next = null
        return false
      }
      next = headings.next
      true
    }
    
    def collectChildren(children: ListBuffer[Node]) {
      val hasMore = moveNext()
      movingUp = false
      
      if (hasMore && next != null) {
        if (curr.weight < next.weight) {
          val n = Node(curr, getChildren())
          children += n
          if (movingUp && curr.weight == children.head.heading.weight || 
             (next != null && curr.weight == next.weight + 1)) {
            collectChildren(children)
          } else {
            moveNext()
            movingUp = true
            return
          }
        } else if (curr.weight == next.weight) {
          children += Node(curr, List())
          collectChildren(children)
        } else {
          children += Node(curr, List())
          return
        }
      } else {
        if (curr != null) {
          children += Node(curr, List())
        }
        return
      }
    }
    
    def getChildren(): List[Node] = {
      val children = new ListBuffer[Node]
      collectChildren(children)
      children.toList
    }
    
    Node(root, getChildren())
  }
  
/** Parses a line of input.
    This implementation is correct for all predefined test cases. */
  def parse(record: String): Option[Heading] = {
    val H = "H(\\d+)".r
    record.split(" ", 2) match {
      case Array(H(level), text) =>
        scala.util.Try(Heading(level.toInt, text.trim)).toOption
      case _ => None
    }
  }

  /** Converts a node to HTML.
    This implementation is correct for all predefined test cases. */
  def toHtml(node: Node): String = {
    val childHtml = node.children match {
      case Nil => ""
      case children =>
        "<ol>" + children.map(
          child => "<li>" + toHtml(child) + "</li>"
        ).mkString("\n") + "</ol>"
    }
    val heading =
      if (node.heading.text.isEmpty) ""
      else node.heading.text + "\n"
    heading + childHtml
  }
}
