package model;

import lombok.Getter;

@Getter
public enum MusicalNote {
    DO_OCTAVE_1( "DO octave 1", 65.41), DO_DIESE_OCTAVE_1( "DO# octave 1", 69.30),
    DO_OCTAVE_2( "DO octave 2", 130.8), DO_DIESE_OCTAVE_2( "DO# octave 2", 138.59),
    DO_OCTAVE_3( "DO octave 3", 261.63), DO_DIESE_OCTAVE_3( "DO# octave 3", 277.18),
    DO_OCTAVE_4( "DO octave 4", 523.25), DO_DIESE_OCTAVE_4( "DO# octave 4", 554.37),
    DO_OCTAVE_5( "DO octave 5", 1050.0), DO_DIESE_OCTAVE_5( "DO# octave 5", 1110.0),
    DO_OCTAVE_6( "DO octave 6", 2093.0), DO_DIESE_OCTAVE_6( "DO# octave 6", 2217.0),
    RE_OCTAVE_1( "RE octave 1", 73.42), RE_DIESE_OCTAVE_1( "RE# octave 1", 77.78),
    RE_OCTAVE_2( "RE octave 2", 146.83), RE_DIESE_OCTAVE_2( "RE# octave 2", 155.56),
    RE_OCTAVE_3( "RE octave 3", 293.66), RE_DIESE_OCTAVE_3( "RE# octave 3", 311.13),
    RE_OCTAVE_4( "RE octave 4", 587.33), RE_DIESE_OCTAVE_4( "RE# octave 4", 622.25),
    RE_OCTAVE_5( "RE octave 5", 1170.0), RE_DIESE_OCTAVE_5( "RE# octave 5", 1240.0),
    RE_OCTAVE_6( "RE octave 6", 2349.0), RE_DIESE_OCTAVE_6( "RE# octave 6", 2489.0),
    MI_OCTAVE_1( "MI octave 1", 82.41),
    MI_OCTAVE_2( "MI octave 2", 164.81),
    MI_OCTAVE_3( "MI octave 3", 329.63),
    MI_OCTAVE_4( "MI octave 4", 659.26),
    MI_OCTAVE_5( "MI octave 5", 1320.0),
    MI_OCTAVE_6( "MI octave 6", 2637.0),
    FA_OCTAVE_1( "FA octave 1", 87.31), FA_DIESE_OCTAVE_1( "FA# octave 1", 92.50),
    FA_OCTAVE_2( "FA octave 2", 174.61), FA_DIESE_OCTAVE_2( "FA# octave 2", 185.0),
    FA_OCTAVE_3( "FA octave 3", 349.23), FA_DIESE_OCTAVE_3( "FA# octave 3", 369.99),
    FA_OCTAVE_4( "FA octave 4", 698.46), FA_DIESE_OCTAVE_4( "FA# octave 4", 739.99),
    FA_OCTAVE_5( "FA octave 5", 1400.0), FA_DIESE_OCTAVE_5( "FA# octave 5", 1480.0),
    FA_OCTAVE_6( "FA octave 6", 2794.0), FA_DIESE_OCTAVE_6( "FA# octave 6", 2960.0),
    SOL_OCTAVE_1( "SOL octave 1", 98.00), SOL_DIESE_OCTAVE_1( "SOL# octave 1", 103.83),
    SOL_OCTAVE_2( "SOL octave 2", 196.0), SOL_DIESE_OCTAVE_2( "SOL# octave 2", 207.65),
    SOL_OCTAVE_3( "SOL octave 3", 392.0), SOL_DIESE_OCTAVE_3( "SOL# octave 3", 415.30),
    SOL_OCTAVE_4( "SOL octave 4", 783.99), SOL_DIESE_OCTAVE_4( "SOL# octave 4", 830.61),
    SOL_OCTAVE_5( "SOL octave 5", 1570.0), SOL_DIESE_OCTAVE_5( "SOL# octave 5", 1660.0),
    SOL_OCTAVE_6( "SOL octave 6", 3136.0), SOL_DIESE_OCTAVE_6( "SOL# octave 6", 3322.0),
    LA_OCTAVE_1( "LA octave 1", 110.0), LA_DIESE_OCTAVE_1( "LA# octave 1", 116.54),
    LA_OCTAVE_2( "LA octave 2", 220.0), LA_DIESE_OCTAVE_2( "LA# octave 2", 233.08),
    LA_OCTAVE_3( "LA octave 3", 440.0), LA_DIESE_OCTAVE_3( "LA# octave 3", 466.16),
    LA_OCTAVE_4( "LA octave 4", 880.0), LA_DIESE_OCTAVE_4( "LA# octave 4", 932.33),
    LA_OCTAVE_5( "LA octave 5", 1760.0), LA_DIESE_OCTAVE_5( "LA# octave 5", 1860.0),
    LA_OCTAVE_6( "LA octave 6", 3520.0), LA_DIESE_OCTAVE_6( "LA# octave 6", 3729.0),
    SI_OCTAVE_1( "SI octave 1", 123.47),
    SI_OCTAVE_2( "SI octave 2", 246.94),
    SI_OCTAVE_3( "SI octave 3", 493.94),
    SI_OCTAVE_4( "SI octave 4", 987.77),
    SI_OCTAVE_5( "SI octave 5", 1980.0),
    SI_OCTAVE_6( "SI octave 6", 3951.0);


    private String musicalNote;
    private double frequency;

    MusicalNote(String name ,double frequency) {
        this.musicalNote = name;
        this.frequency = frequency;
    }


}
