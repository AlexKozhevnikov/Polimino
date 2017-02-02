package com.alexeus.polyominoes.control;

import com.alexeus.polyominoes.control.enums.PlayRegimeType;

/**
 * Created by alexeus on 02.02.2017.
 * Настройки
 */
public class Settings {
    private static Settings instance;

    /**
     * Если да, то включается автоматический ход партии: наблюдателю не нужно нажимать на next, ход будет автоматически
     * совершаться по происшествии timeoutMillis миллисекунд.
     */
    private PlayRegimeType playRegime;

    private Settings() {
        playRegime = PlayRegimeType.none;
    }

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }

    public PlayRegimeType getPlayRegime() {
        return playRegime;
    }

    public void setPlayRegime(PlayRegimeType playRegime) {
        this.playRegime = playRegime;
    }
}
