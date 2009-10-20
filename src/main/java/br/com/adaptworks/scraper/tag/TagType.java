package br.com.adaptworks.scraper.tag;

<<<<<<< HEAD:src/main/java/br/com/adaptworks/scraper/tag/TagType.java
import java.util.Map;
=======
import br.com.adaptworks.scraper.element.BangTagElement;
import br.com.adaptworks.scraper.element.CloseTagElement;
import br.com.adaptworks.scraper.element.Element;
import br.com.adaptworks.scraper.element.OpenTagElement;
>>>>>>> changing element package:src/main/java/br/com/adaptworks/scraper/tag/TagType.java

public enum TagType {

    OPEN(' ') {
        @Override
        public Tag createTag(final String name, final String content, final Map<String, String> attributes) {
            return new OpenTag(name, content, attributes);
        }
    },

    CLOSE('/') {
        @Override
        public Tag createTag(final String name, final String content, final Map<String, String> attributes) {
            return new CloseTag(name, content, attributes);
        }
    },

    BANG('!') {
        @Override
        public Tag createTag(final String name, final String content, final Map<String, String> attributes) {
            return new BangTag(name, content, attributes);
        }
    };

    private final char character;

    private TagType(final char character) {
        this.character = character;
    }

    public abstract Tag createTag(String name, String content, Map<String, String> attributes);

    public static TagType fromValue(final char character) {
        for (TagType type : values()) {
            if (type.character == character) {
                return type;
            }
        }
        return TagType.OPEN;
    }

    @Override
    public String toString() {
        return "" + character;
    }
}
