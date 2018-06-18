package de.funkemt.soundfontplayer

import org.scalajs.dom.raw.AudioNode

import scala.scalajs.js

@js.native
trait SamplePlayer extends js.Object {
  val opts: Options = js.native

  def play(name: String, when: Double = 0, options: Options = null): AudioNode = js.native
}

@js.native
trait Options extends js.Object {
  val number: Int = js.native
}
