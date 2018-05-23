const DOM_MIN_TIMEOUT_VALUE = 4; // 4ms

var MidiPlayer = MidiPlayer;
var loadFile, loadDataAndPlay, Player;
var AudioContext = window.AudioContext || window.webkitAudioContext || false;
var audioContext = new AudioContext || new webkitAudioContext;
var eventsDiv = document.getElementById('events');

var instrument1, instrument2;

var repeat = function() {
    console.log("repeat");
    Player.stop().play();
}


Soundfont.instrument(audioContext, 'https://raw.githubusercontent.com/gleitz/midi-js-soundfonts/gh-pages/MusyngKite/acoustic_grand_piano-mp3.js').then(function (instrument_ch1) {

    instrument1 = instrument_ch1;

});

Soundfont.instrument(audioContext, 'https://raw.githubusercontent.com/gleitz/midi-js-soundfonts/gh-pages/MusyngKite/marimba-mp3.js').then(function (instrument_ch2) {

    instrument2 = instrument_ch2;

});

loadDataAndPlay = function(dataUri) {
    Player = new MidiPlayer.Player(function(event) {
        if (event.name == 'Note on' && event.velocity > 0 && event.channel == 1) {
            console.debug(event);
            instrument1.play(event.noteName, audioContext.currentTime, {gain:event.velocity/100});
        } else if (event.name == 'Note on' && event.velocity > 0 && event.channel == 2) {
            console.debug(event);
            instrument2.play(event.noteName, audioContext.currentTime, {gain:event.velocity/100});
        }

    });

    // Loop Music
    Player.on('endOfFile', function() {
        console.log("endOfFile");

        // temporary workaround to loop file (call play() from another context)
        setTimeout(repeat, DOM_MIN_TIMEOUT_VALUE);
    });

    Player.loadDataUri(dataUri);
    Player.play();
};
