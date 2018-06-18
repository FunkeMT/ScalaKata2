package de.funkemt.soundfontplayer

import org.scalajs.dom.raw.AudioContext

import scala.scalajs.js

@js.native
object Soundfont extends js.Object {
  def instrument(ac: AudioContext, name: String, options: js.Object = null): js.Promise[SamplePlayer] = js.native

  def nameToUrl(name: String, sf: String, format: String): String = js.native
}