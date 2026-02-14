package br.com.alura.petsflix.model;

public enum Categoria {
    ACAO("Action", "Ação"),
    AVENTURA("Adventure", "Aventura"),
    COMEDIA("Comedy", "Comédia"),
    DRAMA("Drama", "Drama"),
    CRIME("Crime", "Crime"),
    ROMANCE("Romance", "Romance"),
    FICCAO_CIENTIFICA("Sci-Fi", "Ficção Científica"),
    TERROR("Horror", "Terror"),
    FANTASIA("Fantasy", "Fantasia"),
    ANIMACAO("Animation", "Animação"),
    MUSICAL("Musical", "Musical"),
    FAMILIA("Family", "Família"),
    SUSPENSE("Thriller", "Suspense"),
    DOCUMENTARIO("Documentary", "Documentário"),
    MISTERIO("Mystery", "Mistério"),
    OUTRO("Unknown", "Outro");


    private String categoriaOmdb;

    private String categoriaPortugues;


    Categoria(String categoriaOmdb, String categoriaPortugues) {
        this.categoriaOmdb = categoriaOmdb;
        this.categoriaPortugues = categoriaPortugues;

    }

    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }

    public static Categoria fromPortugues(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaPortugues.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }
}
