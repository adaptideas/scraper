package br.com.adaptworks.scraper.tag;

import br.com.adaptworks.scraper.element.CloseTagElement;
import br.com.adaptworks.scraper.element.Element;
import br.com.adaptworks.scraper.element.OpenTagElement;

public enum TagType {

    OPEN(' ') {
        @Override
        public Element createElement(final Tag tag, final String elementContent) {
            return new OpenTagElement(tag, elementContent);
        }
    },

    CLOSE('/') {
        @Override
        public Element createElement(final Tag tag, final String elementContent) {
            return new CloseTagElement(tag, elementContent);
        }
    },

    BANG('!') {
        @Override
        public Element createElement(final Tag tag, final String elementContent) {
            return new BangTagElement();
        }
    };

    private final char character;

    private TagType(final char character) {
        this.character = character;

    }

    public static TagType fromValue(final char character) {
        for (TagType type : values()) {
            if (type.character == character) {
                return type;
            }
        }
        return TagType.OPEN;
    }

    public abstract Element createElement(Tag tag, String elementContent);

    @Override
    public String toString() {
        return "" + character;
    }
}
