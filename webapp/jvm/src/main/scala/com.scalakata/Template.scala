package com.scalakata

object Template {
  import scalatags.Text.all._
  import scalatags.Text.tags2.{title, noscript, nav}

  def echo(code: String) = {
    "<!DOCTYPE html>" +
    html(
      head(
        meta(charset:="utf-8"),
        link(rel:="stylesheet", href:="/assets/lib/codemirror/addon/dialog/dialog.css"),
        link(rel:="stylesheet", href:="/assets/lib/codemirror/addon/fold/foldgutter.css"),
        link(rel:="stylesheet", href:="/assets/lib/codemirror/addon/hint/show-hint.css"),
        link(rel:="stylesheet", href:="/assets/lib/codemirror/addon/scroll/simplescrollbars.css"),
        link(rel:="stylesheet", href:="/assets/lib/codemirror/lib/codemirror.css"),
        link(rel:="stylesheet", href:="/assets/lib/codemirror/theme/mdn-like.css"),
        link(rel:="stylesheet", href:="/assets/lib/open-iconic/font/css/open-iconic.css"),
        link(rel:="stylesheet", href:="/assets/main.css")
      ),
      body(style := "margin:0")(
        raw(code),
        script(src := "/assets/lib/iframe-resizer/js/iframeResizer.contentWindow.min.js")
      )
    )
  }

  def txt(prod: Boolean) = {
    val client = if(prod) "client-opt.js" else "client-fastopt.js"

    "<!DOCTYPE html>" +
    html(
      head(
        title("Scala Kata"),
        base(href:="/"),
        meta(charset:="utf-8"),
        meta(name:="description", content:= "Interactive Playground for the Scala Programming Language"),
        link(rel:="icon", `type`:="image/png", href:="/assets/favicon.ico"),
        link(rel:="stylesheet", href:="/assets/lib/codemirror/addon/dialog/dialog.css"),
        link(rel:="stylesheet", href:="/assets/lib/codemirror/addon/fold/foldgutter.css"),
        link(rel:="stylesheet", href:="/assets/lib/codemirror/addon/hint/show-hint.css"),
        link(rel:="stylesheet", href:="/assets/lib/codemirror/addon/scroll/simplescrollbars.css"),
        link(rel:="stylesheet", href:="/assets/lib/codemirror/lib/codemirror.css"),
        link(rel:="stylesheet", href:="/assets/lib/codemirror/theme/mdn-like.css"),
        link(rel:="stylesheet", href:="/assets/lib/open-iconic/font/css/open-iconic.css"),
        link(rel:="stylesheet", href:="/assets/lib/drawer/css/drawer.min.css"),


        link(rel:="stylesheet", href:="/assets/main.css")
      ),
      body(`class` := "cm-s-solarized cm-s-dark")(
        div(`id` := "code", cls := "drawer drawer--right")(
          noscript("No Javscript, No Scala!"),
          div(cls := "play-bar")(
            div(id := "play-bar-progress", cls := "play-bar-progress")
          ),
          textarea(id := "scalakata", style := "display: none;"),
          ul(`class` := "menu")(
            li(id := "state", `class` := "oi", "data-glyph".attr := "media-play"),
            li(id := "stop", "title".attr := "Stop", `class` := "oi", "data-glyph".attr := "media-stop"),
            li(id := "theme", "title".attr := "toggle theme (F2)", `class` := "oi not-used", "data-glyph".attr := "sun"),
            li(id := "help", "title".attr := "help (F1)", `class` := "oi not-used", "data-glyph".attr := "question-mark"),
            li(id := "share", "title".attr := "share (F7)", `class` := "oi not-used", "data-glyph".attr := "share-boxed"),
            li(id := "rooms", "title".attr := "rooms list", `class` := "oi drawer-toggle not-used", "data-glyph".attr := "list")
          ),
          div(id := "shared"),
          nav(id := "react-room-list", cls := "drawer-nav", role := "navigation")
        ),

        script(src:="/assets/lib/codemirror/lib/codemirror.js"),

        script(src:="/assets/lib/codemirror/addon/comment/comment.js"),
        script(src:="/assets/lib/codemirror/addon/dialog/dialog.js"),
        script(src:="/assets/lib/codemirror/addon/edit/closebrackets.js"),
        script(src:="/assets/lib/codemirror/addon/edit/matchbrackets.js"),
        script(src:="/assets/lib/codemirror/addon/fold/brace-fold.js"),
        script(src:="/assets/lib/codemirror/addon/fold/foldcode.js"),
        script(src:="/assets/lib/codemirror/addon/hint/show-hint.js"),
        script(src:="/assets/lib/codemirror/addon/runmode/runmode.js"),
        script(src:="/assets/lib/codemirror/addon/scroll/scrollpastend.js"),
        script(src:="/assets/lib/codemirror/addon/scroll/simplescrollbars.js"),
        script(src:="/assets/lib/codemirror/addon/search/match-highlighter.js"),
        script(src:="/assets/lib/codemirror/addon/search/search.js"),
        script(src:="/assets/lib/codemirror/addon/search/searchcursor.js"),
        script(src:="/assets/lib/codemirror/addon/mode/simple.js"),
        script(src:="/assets/lib/codemirror/keymap/sublime.js"),
        script(src:="/assets/lib/codemirror/mode/clike/clike.js"),

        script(src:="/assets/lib/pagedown/Markdown.Converter.js"),
        script(src:="/assets/lib/pagedown/Markdown.Sanitizer.js"),
        script(src:="/assets/lib/pagedown/Markdown.Extra.js"),

        script(src:="/assets/lib/midi-player-js/browser/midiplayer.js"),
        script(src:="/assets/lib/soundfont-player/dist/soundfont-player.js"),

        script(src:="/assets/lib/iframe-resizer/js/iframeResizer.min.js"),

        script(src:="/assets/lib/jquery/jquery.min.js"),
        script(src:="/assets/lib/iscroll/build/iscroll.js"),
        script(src:="/assets/lib/drawer/js/drawer.min.js"),

        script(src:="/assets/lib/react/react-with-addons.min.js"),
        script(src:="/assets/lib/react/react-dom.min.js"),

        script("""
              CodeMirror.defineSimpleMode('simplemode', {
              // The start state contains the rules that are intially used
              start: [
                // Rules are matched in the order in which they appear, so there is
                // no ambiguity between this one and the one above
                {regex: /(?:MUSICIAN|INSTRUMENT|PLAYS|LOOP|CHORD|WITH|TEMPO|AT|PLAY)\b/,
                token: 'keyword'},

                {regex: /[A-Z$][a-z$]+/, token: 'variable-3'},

                {regex: /0x[a-f\d]+|[-+]?(?:\.\d+|\d+\.?\d*)(?:e[-+]?\d+)?/i,
                token: 'number'},


                // indent and dedent properties guide autoindentation
                {regex: /[\{\[\(]/, indent: true},
                {regex: /[\}\]\)]/, dedent: true},

                {regex: /([a-z$])(,)([a-z$])/, token: ['variable-2', null, 'variable-2']},

                {regex: /[a-z$][\w$]*/, token: 'variable-1'},



                // You can embed other modes with the mode property. This rule
                // causes all code between << and >> to be highlighted with the XML
                // mode.
                {regex: /<</, token: 'meta', mode: {spec: 'xml', end: />>/}}
              ]
            });
        """),

        script(src:=s"/assets/$client"),
        raw("""<script>var codeReg = /<code>([\s\S]*?)<\/code>/;</script>"""),
        script("com.scalakata.Main().main()"),

        script("""
          $(document).ready(function() {
            $('.drawer').drawer({
              iscroll: {
                mouseWheel: true,
                scrollbars: true
              }
            });
          });
        """),

        script("""
          $(document).on('click', '#close-drawer-btn', function() {
            $('.drawer').drawer('close');
          });
        """),


        script("com.scalakata.RoomList().init('react-room-list');")
      )
    )
  }
}
