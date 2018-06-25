package com.scalakata

import midiPlayerJs.{MidiEvent, Player}
import soundfontPlayer.{SamplePlayer, Soundfont}
import org.scalajs.dom
import org.scalajs.dom.raw.{AudioContext, HTMLElement}

import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Success
import scala.util.Failure

object Scalala {
  val DOM_MIN_TIMEOUT_VALUE: Double = 4 // 4ms
  val SOUNDFONT_REPO_URI_PREFIX = "https://raw.githubusercontent.com/gleitz/midi-js-soundfonts/gh-pages/FluidR3_GM/"
  val SOUNDFONT_REPO_URI_SUFFIX = "-mp3.js"

  val audioContext = new AudioContext
  val player = new Player()

  var instrumentsMap = Map[Int, SamplePlayer]()
  var channelsMap = Map[Int, Int]()

  var futureList = new ListBuffer[Future[SamplePlayer]]()



  /**
    * Build Soundfont Map
    *
    * for complete list see:
    * http://gleitz.github.io/midi-js-soundfonts/MusyngKite/names.json
    */
  var soundfontMap = Map[Int, String]()
  soundfontMap += (0 -> "acoustic_grand_piano")
  soundfontMap += (13 -> "marimba")
  soundfontMap += (25 -> "acoustic_guitar_nylon")
  soundfontMap += (57 -> "trumpet")
  soundfontMap += (115 -> "steel_drums")
//  soundfontMap += (14 -> "xylophone")
//  soundfontMap += (20 -> "church_organ")
//  soundfontMap += (33 -> "acoustic_bass")
//  soundfontMap += (41 -> "violin")
//  soundfontMap += (43 -> "cello")
//  soundfontMap += (59 -> "tuba")
//  soundfontMap += (61 -> "french_horn")
//  soundfontMap += (68 -> "soprano_sax")
//  soundfontMap += (69 -> "oboe")
//  soundfontMap += (72 -> "clarinet")
//  soundfontMap += (74 -> "flute")


  /**
    * DOM-Elements
    */
  val progressBar = dom.document.getElementById("play-bar-progress")
  val stateButton = dom.document.getElementById("state")


  /**
    * MidiPlayerJS Event
    * ''midiEvent''
    *
    * lookup instruments-ID and play current note with
    * pre-loaded instrument
    */
  player.on("midiEvent", (event: MidiEvent) => {
    if (event.name.isDefined && event.name.equals("Note on") && event.velocity > 0) {
      var instrumentID = channelsMap.get(event.channel).get
      var instrument = instrumentsMap.get(instrumentID).get
      instrument.play(event.noteName, audioContext.currentTime)
      progressBar.asInstanceOf[HTMLElement].style.width = (s"${100 - player.getSongPercentRemaining()}%")
    } else if (event.name.isDefined && event.name.equals("Program Change") && event.value.isDefined) {
      // ToDo: Workaround
      channelsMap += (event.channel -> event.value.get)
    } else if (event.name.isDefined && event.name.equals("Set Tempo") && event.data.isDefined) {
      player.setTempo(event.data.get)
    }
    //dom.console.log(event)
  })

  /**
    * MidiPlayerJS Event
    * ''endOfFile''
    *
    * repeat file after ending
    */
  player.on("endOfFile", () => {
    dom.console.log("endOfFile")
    scalajs.js.timers.setTimeout(DOM_MIN_TIMEOUT_VALUE) {
      player.stop().play()
      ()
    }
  })



  /**
    * Load asynchronously Soundfont-Files (mp3)
    *
    * IMPORTANT: Has to be finished before continuing
    */
  def loadSoundfonts() = {
    stateButton.setAttribute("data-glyph", "clock")
    stateButton.classList.add("disabled")
    println("Loading Soundfonts ...")

    for ((key, value) <- soundfontMap) {
      futureList += Soundfont.instrument(
        audioContext,
        SOUNDFONT_REPO_URI_PREFIX + value + SOUNDFONT_REPO_URI_SUFFIX,
        scalajs.js.Dynamic.literal("number" -> key)
      ).toFuture
    }
    val futureOfList = Future.sequence(futureList.toList)

    futureOfList onComplete {
      case Success(x) => {
        for (obj <- x) {
          instrumentsMap += (obj.opts.number -> obj)
        }
        println("Loading Soundfonts: Completed.")
        stateButton.setAttribute("data-glyph", "media-play")
        stateButton.classList.remove("disabled")
      }
      case Failure(ex) => {
        val errMsg = "Error while Loading Soundfonts! " + ex
        println(errMsg)
        stateButton.setAttribute("data-glyph", "warning")
        stateButton.setAttribute("title", errMsg)
      }
    }
  }

  /**
    * Load data-string and play file
    *
    * @param dataUri String Base64 encoded midiFile
    * @return
    */
  def loadDataAndPlay(dataUri: String) = {
    player.stop()

    player.loadDataUri(dataUri)

    player.setTempo(40)

    player.play()
  }

  /**
    * Stop runnig playback
    *
    * @return
    */
  def stop() = {
    player.stop()
  }

  /**
    * Play currently loaded file again
    *
    * NOTE: No evaluation of new data
    * just play the current data if available
    *
    * @return
    */
  def play() = {
    player.stop().play()
  }
}
