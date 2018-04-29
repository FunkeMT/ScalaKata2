package com.scalakata

trait Api{
  def eval(request: EvalRequest): EvalResponse
  def typeAt(request: TypeAtRequest): Option[TypeAtResponse]
  def autocomplete(request: CompletionRequest): List[CompletionResponse]
  def evalDsl(request: EvalRequest): EvalDslResponse
}

object Util {
  val nl = '\n'
  val prelude = 
    """|import com.scalakata._
       |
       |@instrument class Playground {
       |  """.stripMargin
    
  def wrap(code: String): String = prelude + code + nl + "}"
}