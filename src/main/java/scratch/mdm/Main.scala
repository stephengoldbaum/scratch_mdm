package scratch.mdm


import org.finos.morphir.datamodel.{*, given}
import org.finos.morphir.datamodel.*
import org.finos.morphir.ir.conversion.ToMorphirValue
import org.finos.morphir.naming.*
import org.finos.morphir.ir.conversion.*
import org.finos.morphir.ir.json.MorphirJsonDecodingSupport.*
import org.finos.morphir.ir.json.MorphirJsonEncodingSupport.*
import org.finos.morphir.ir.json.MorphirJsonFileSupport.*
import org.finos.morphir.datamodel.{Data, Deriver}
import org.finos.morphir.ir.json.{MorphirJsonEncodingSupport, MorphirJsonFileSupport}
import zio.json.*


object Main extends App {
  case class Foo(name: String, age: Int)

  val foo = Foo("foo", 1)

  given rootName: org.finos.morphir.datamodel.GlobalDatamodelContext with {
    override def value = root / "scratch" % "Foo"
  }

  val fooDeriver: Deriver[Foo] = Deriver.gen[Foo]

  val model = fooDeriver.derive(foo)
  val concept = fooDeriver.concept

//  val ir = ToMorphirType.summon[Concept].toMorphirTypedValue(concept)

//  println(modelConcept.toJson)

  val modelIR = ToMorphirValue.summon[Data].typed.toMorphirValue(model)
  val json = modelIR.toJsonPretty
  println(json)
}