var MidiPlayer = MidiPlayer;
var loadFile, loadDataUri, Player;
var AudioContext = window.AudioContext || window.webkitAudioContext || false;
var ac = new AudioContext || new webkitAudioContext;
var eventsDiv = document.getElementById('events');

var instrument1, instrument2;


Soundfont.instrument(ac, 'https://raw.githubusercontent.com/gleitz/midi-js-soundfonts/gh-pages/MusyngKite/acoustic_grand_piano-mp3.js').then(function (instrument_ch1) {

    instrument1 = instrument_ch1;

});

Soundfont.instrument(ac, 'https://raw.githubusercontent.com/gleitz/midi-js-soundfonts/gh-pages/MusyngKite/marimba-mp3.js').then(function (instrument_ch2) {

    instrument2 = instrument_ch2;

});

loadDataUri = function(dataUri) {
    Player = new MidiPlayer.Player(function(event) {
        if (event.name == 'Note on' && event.velocity > 0 && event.channel == 1) {
            console.debug(event);
            instrument1.play(event.noteName, ac.currentTime, {gain:event.velocity/100});
        } else if (event.name == 'Note on' && event.velocity > 0 && event.channel == 2) {
            console.debug(event);
            instrument2.play(event.noteName, ac.currentTime, {gain:event.velocity/100});
        }

    });

    Player.loadDataUri(dataUri);
    Player.play();
}
