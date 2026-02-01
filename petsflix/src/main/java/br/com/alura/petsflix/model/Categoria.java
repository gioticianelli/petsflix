package br.com.alura.petsflix.model;

public enum Categoria {
    ACAO("Action"),
    AVENTURA("Adventure"),
    COMEDIA("Comedy"),
    DRAMA("Drama"),
    CRIME("Crime"),
    ROMANCE("Romance"),
    FICCAO_CIENTIFICA("Sci-Fi"),
    TERROR("Horror"),
    FANTASIA("Fantasy"),
    ANIMACAO("Animation"),
    MUSICAL("Musical"),
    FAMILIA("Family"),
    SUSPENSE("Thriller"),
    DOCUMENTARIO("Documentary"),
    MISTERIO("Mystery"),
    OUTRO("Unknown");


    private String categoriaOmdb;


    Categoria(String categoriaOmdb){
        this.categoriaOmdb = categoriaOmdb;

    }

    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }
}
