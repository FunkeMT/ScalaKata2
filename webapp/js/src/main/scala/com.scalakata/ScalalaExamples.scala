package com.scalakata

object ScalalaExamples {
  val EXAMPLE_1 = """musician my_pianist
    | instrument Piano
    | plays c,c,g,g.dot,a,a,g,f,f,e,e,d,d,c.dot
    |
    |play with tempo 60
    | my_pianist
  """.stripMargin

  val EXAMPLE_2 = """musician my_pianist
    | instrument Piano
    | plays
    | -a,-a,-a,-f/2,c/2,
    | -a,-f/2,c/2,-a.dot,
    | e,e,e,f/2,c/2,
    | a,-a/2,-a/2,a,a.flat/2,g/2,
    | g.flat/4,f/4,g.flat/4,-h.flat/4,e.flat,d/2,d.flat/2
    |
    |play with tempo 80
    | my_pianist
  """.stripMargin

  val EXAMPLE_3 = """musician piano_1
    | instrument Piano
    | plays -a,e,d,d/2,c/2,d,d/2,e/2,d/4,c/4,-a/4,-g/4
    |
    |musician piano2
    | instrument Piano
    | plays chord(--a,--c.sharp,-e)
    |
    |musician piano3
    | instrument Piano
    | plays --f
    |
    |musician piano4
    | instrument Piano
    | plays -a,c,+a.dot
    |
    |
    |play with tempo 70
    | piano_1 at 1,
    | piano_1 at 105,
    | piano2 at 1,
    | piano3 at 105,
    | piano4 at 224
  """.stripMargin

  val EXAMPLE_4 = """musician piano_example
    | instrument Piano
    | plays c,d,e,f,g,a,h
    |
    |musician marimba_example
    | instrument Marimba
    | plays c,d,e,f,g,a,h
    |
    |musician bass_example
    | instrument Bass
    | plays c,d,e,f,g,a,h
    |
    |musician guitar_example
    | instrument Guitar
    | plays c,d,e,f,g,a,h
    |
    |musician trumpet_example
    | instrument Trumpet
    | plays c,d,e,f,g,a,h
    |
    |play with tempo 80
    | guitar_example
  """.stripMargin

  def getExample(number: Int) = {
    number match {
      case 1 => EXAMPLE_1
      case 2 => EXAMPLE_2
      case 3 => EXAMPLE_3
      case 4 => EXAMPLE_4
      case _ => EXAMPLE_1
    }
  }
}
