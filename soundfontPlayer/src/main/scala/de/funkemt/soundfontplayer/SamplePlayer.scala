package de.funkemt.soundfontplayer

import org.scalajs.dom.raw.AudioNode

import scala.scalajs.js

@js.native
trait SamplePlayer extends js.Object {
  def play(name: String, when: Double = 0, options: js.Object = null): AudioNode = js.native
}
