package de.funkemt.midiplayerjs

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSName, ScalaJSDefined}


@js.native
@JSName("MidiPlayer.Player")
class Player(eventHandler: js.Function = null, buffer: js.Array[Any] = null) extends js.Object {

  def loadFile(path: String): Player = js.native
  def loadDataUri(dataUri: String): Player = js.native
  def play(): Player = js.native
  def pause(): Player = js.native
  def stop(): Player = js.native
  def setTempo(tempo: Int): Unit = js.native

  def on(eventName: String, handler: js.Function): Unit = js.native

}

// ToDo: Split in multiple and well defined Events
@ScalaJSDefined
trait MidiEvent extends js.Object {
  val name: js.UndefOr[String]
  val channel: Int
  val noteNumber: String
  val noteName: String
  val velocity: Float
  val value: js.UndefOr[Int]
}
