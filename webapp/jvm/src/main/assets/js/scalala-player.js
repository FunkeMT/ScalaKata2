const DOM_MIN_TIMEOUT_VALUE = 4; // 4ms
const SOUNDFONT_REPO_URI_PREFIX = 'https://raw.githubusercontent.com/gleitz/midi-js-soundfonts/gh-pages/MusyngKite/';
const SOUNDFONT_REPO_URI_SUFFIX = '-mp3.js';


var loadFile, loadDataAndPlay;
var AudioContext = window.AudioContext || window.webkitAudioContext || false;
var audioContext = new AudioContext || new webkitAudioContext;
var eventsDiv = document.getElementById('events');


var instrumentsMap = new Map();
var channelsMap = new Map();
var soundfontMap = new Map();
soundfontMap.set(0, "acoustic_grand_piano");
soundfontMap.set(13, "marimba");
//soundfontMap.set(14, "xylophone");
//soundfontMap.set(20, "church_organ");
soundfontMap.set(25, "acoustic_guitar_nylon");
//soundfontMap.set(33, "acoustic_bass");
//soundfontMap.set(41, "violin");
//soundfontMap.set(43, "cello");
soundfontMap.set(57, "trumpet");
//soundfontMap.set(59, "tuba");
//soundfontMap.set(61, "french_horn");
//soundfontMap.set(68, "soprano_sax");
//soundfontMap.set(69, "oboe");
//soundfontMap.set(72, "clarinet");
//soundfontMap.set(74, "flute");

var promiseArr = new Array();


var MidiPlayer = MidiPlayer;
var Player = new MidiPlayer.Player();


var repeat = function() {
    console.log("repeat");
    Player.stop().play();
};

var loadSoundfonts = function() {
    soundfontMap.forEach(function(value, key) {
        promiseArr.push(Soundfont.instrument(audioContext, SOUNDFONT_REPO_URI_PREFIX + value + SOUNDFONT_REPO_URI_SUFFIX, { number: key }));
    });
    Promise.all(promiseArr).then(function(value) {
        console.log("Promise all finished");
        console.log(value);
        value.forEach(function(obj) {
            instrumentsMap.set(obj.opts.number, obj);
        });
        console.log(instrumentsMap);
    });
};
loadSoundfonts();



loadDataAndPlay = function(dataUri) {
    Player.stop();


    // Load MIDI File from base64
    Player.loadDataUri(dataUri);

    // Set Tempo (ToDo: TMP)
    Player.tempo = 40;

    // Add Main Events
    Player.on('midiEvent', function(event) {
        if (event.name == 'Note on' && event.velocity > 0) {
            console.debug(event);
            var instrumentID = channelsMap.get(event.channel);
            var instrument = instrumentsMap.get(instrumentID);
            instrument.play(event.noteName, audioContext.currentTime, {gain:event.velocity/100});
        } else if (event.name == 'Program Change') {
            // ToDO: Workaround
            console.debug(event);
            channelsMap.set(event.channel, event.value);
        }
    });

    // Loop Music Event
    Player.on('endOfFile', function() {
        console.log("endOfFile");

        // ToDo: temporary workaround to loop file (call play() from another context)
        setTimeout(repeat, DOM_MIN_TIMEOUT_VALUE);
    });


    // PLAY
    Player.play();
};
