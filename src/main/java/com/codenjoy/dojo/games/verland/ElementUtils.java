package com.codenjoy.dojo.games.verland;

import com.codenjoy.dojo.services.printer.TeamElement;

import static com.codenjoy.dojo.games.verland.Element.*;

public class ElementUtils {

    public static final Element[] heroes = new Element[]{
            HERO_DEAD,
            HERO,
    };

    public static final Element[] otherHeroes = new Element[]{
            OTHER_HERO_DEAD,
            OTHER_HERO,
    };

    public static final Element[] enemyHeroes = new Element[]{
            ENEMY_HERO_DEAD,
            ENEMY_HERO,
    };

    public static final Element[] pathless = new Element[]{
            PATHLESS,
    };

    public static final Element[] infections = new Element[]{
            INFECTION,
    };

    public static final Element[] hidden = new Element[]{
            HIDDEN,
    };

    public static final Element[] clear = new Element[]{
            CLEAR,
    };

    public static final Element[] contagions = new Element[]{
            CONTAGION_ONE,
            CONTAGION_TWO,
            CONTAGION_THREE,
            CONTAGION_FOUR,
            CONTAGION_FIVE,
            CONTAGION_SIX,
            CONTAGION_SEVEN,
            CONTAGION_EIGHT,
    };

    public static final Element[] healing = new Element[]{
            HERO_HEALING,
            OTHER_HERO_HEALING,
            ENEMY_HERO_HEALING,
    };

    public static final Element[] cure = new Element[]{
            HERO_CURE,
            OTHER_HERO_CURE,
    };

    public static final TeamElement<Element> TEAM_ELEMENT = new TeamElement<>() {
        @Override
        public Element otherHero(Element element) {
            switch (element) {
                case HERO:      return OTHER_HERO;
                case HERO_DEAD: return OTHER_HERO_DEAD;
            }
            throw new IllegalArgumentException("Bad hero state: " + element);
        }

        @Override
        public Element enemyHero(Element element) {
            switch (element) {
                case HERO:      return ENEMY_HERO;
                case HERO_DEAD: return ENEMY_HERO_DEAD;
            }
            throw new IllegalArgumentException("Bad hero state: " + element);
        }
    };
}