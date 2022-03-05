package com.szirakiapps.refreshratereset.takt;

import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * Modified version of
 * https://github.com/wasabeef/Takt
 * <p>
 * due to:
 * - frame rate display bug
 * - memory leak
 * <p>
 * Copyright (C) 2020 Wasabeef
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class Takt {

    private final Program program = new Program();

    public Program prepare(TextView fpsText) {
        return program.prepare(fpsText);
    }

    public Program play() {
        program.play();
        return program;
    }

    public Program finish() {
        program.finish();
        return program;
    }

    public static class Program {
        private Metronome metronome;
        private boolean isPlaying = false;

        private final DecimalFormat decimal = new DecimalFormat("#.0' fps'");

        private TextView fpsText;

        public Program() {
        }

        private void finish() {
            stop();
        }

        private Program prepare(TextView fpsText) {
            metronome = new Metronome();
            this.fpsText = fpsText;

            listener(fps -> {
                if (this.fpsText != null) {
                    this.fpsText.setText(decimal.format(fps));
                }
            });

            return this;
        }

        public void play() {
            metronome.start();
            isPlaying = true;
        }

        public void stop() {
            metronome.stop();
            isPlaying = false;
        }

        public Program color(int color) {
            fpsText.setTextColor(color);
            return this;
        }

        public Program size(float size) {
            fpsText.setTextSize(size);
            return this;
        }

        /*
         * alpha from = 0.0, to = 1.0
         */
        public Program alpha(float alpha) {
            fpsText.setAlpha(alpha);
            return this;
        }

        public Program interval(int ms) {
            metronome.setInterval(ms);
            return this;
        }

        public Program listener(Audience audience) {
            metronome.addListener(audience);
            return this;
        }
    }
}
